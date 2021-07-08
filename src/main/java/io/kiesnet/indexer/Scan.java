package io.kiesnet.indexer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.kiesnet.indexer.domain.entity.*;
import io.kiesnet.indexer.domain.ports.BlockPort;
import io.kiesnet.indexer.domain.ports.BlockchainConnectPort;
import io.kiesnet.indexer.domain.ports.StoragePort;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.protos.common.Common.Block;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockInfo.EnvelopeInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.TxReadWriteSetInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Scan {
	private BlockPort blockPort;
	private BlockchainConnectPort blockchainConnectPort;
	private StoragePort storagePort;

	public Scan(
		BlockPort blockPort,
		BlockchainConnectPort blockchainConnectPort,
		StoragePort storagePort
	) {
		this.blockPort = blockPort;
		this.blockchainConnectPort = blockchainConnectPort;
		this.storagePort = storagePort;
	}

	public void worker(
		String tagNetwork,
		long readBlocks,
		long forkResistance,
		long maxLimitReadBlock
	)
		throws InvalidArgumentException, ProposalException, ParseException, IOException, SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException {
		// Подключение к блокчейну
		Gateway gateway = blockchainConnectPort.connect();
		Network network = gateway.getNetwork(tagNetwork);
		Channel channel = network.getChannel();

		ArrayList<BlockEntity> blockEntities = new ArrayList<BlockEntity>();

		BlockchainInfo blockchaininfo = channel.queryBlockchainInfo();
		long endBlockNumber = 1;
		long startBlockNumber ;
		// Подключаемся к MySQL получаем данные последнего блока
		BlockDataEntity blockData = this.storagePort.getBlockData();
		if(blockData != null)
		{
			if (blockData.get_status() != 2)
			{
				System.out.println("Warning Larst block  number :"+ blockData.get_number() + " status :" + blockData.get_status());
				return;
			}
	   	endBlockNumber = blockData.get_number()+ blockData.get_count();
		}


		// Бесконечный цикл проверки блокчейна
		while (true) {
			long heightBlocks = blockchaininfo.getHeight();

			// Если высота блока равна послденему блоку, то спим 2 секунды
			// Aleksey say no need for this

		/*	if (heightBlocks == endBlockNumber) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("GET_HEIGHT: " + heightBlocks);
				continue;
			} */

			// Обновляем переменные
			startBlockNumber = endBlockNumber;
			endBlockNumber = heightBlocks;

			// Получение инфаормации по каждому блоку: максимальное значение блока с учетом запаса на форк
			for (long j = startBlockNumber; j < endBlockNumber - forkResistance; j++) {

					BlockInfo blockInfo = channel.queryBlockByNumber(j);
					BlockInfo blockNextInfo = channel.queryBlockByNumber(j + 1);
					Block block = blockInfo.getBlock();
					long blockTxCount = block.getData().getDataCount();
					byte[] previousHash = blockInfo.getPreviousHash();
					byte[] dataHash = blockInfo.getDataHash();
					byte[] blockHash = blockNextInfo.getPreviousHash();

				ArrayList<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();

				// Получение инфаормации по каждой транзакции в этом блоке
				for (EnvelopeInfo envelopeinfo : blockInfo.getEnvelopeInfos()) {
					// Данные транзакции
					String txId = envelopeinfo.getTransactionID();
					Date txTime = envelopeinfo.getTimestamp();
					String txPayload = null;
					String txMethod = null;
					String chainCodeName = null;
					byte[] proposalResponsePayload = null;
					ArrayList<WriteSetTxsEntity> txWriteSet = new ArrayList<WriteSetTxsEntity>();
					ArrayList<String> argsList = new ArrayList<String>();

					// Проверка валидности транзакции
					if (
						envelopeinfo.getType() != BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE && envelopeinfo.isValid()
					) {
						System.out.println("Transaction is not valid");
						continue;
					}

					BlockInfo.TransactionEnvelopeInfo transactionEnvelopeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeinfo;

					for (BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo ta : transactionEnvelopeInfo.getTransactionActionInfos()) {
						for (int i = 0; i < ta.getChaincodeInputArgsCount(); i++) {
							if (i == 0) {
								txMethod = new String(ta.getChaincodeInputArgs(i));
							}
							argsList.add(new String(ta.getChaincodeInputArgs(i)));
						}
						chainCodeName = ta.getChaincodeIDName();
						txPayload = new String(ta.getProposalResponsePayload());
						proposalResponsePayload = ta.getProposalResponsePayload(); // тут есть отличие с сайтом, надо проверить у кого не правильно

						// Парсинг writeSetTxs
						for (TxReadWriteSetInfo.NsRwsetInfo nsRwSet : ta.getTxReadWriteSet().getNsRwsetInfos()) {
							long writeSetCount = nsRwSet.getRwset().getWritesCount();
							for (int i = 0; i < writeSetCount; i++) {
								WriteSetTxsEntity writeSetTxsEntity = new WriteSetTxsEntity(
									txTime,
									txTime,
									j,
									/// todo crach here block data == nill
								//	blockData.get_blockchainId(),
									222,
									Hex.toHexString(blockHash),
									txId,
									chainCodeName,
									i,
									nsRwSet.getRwset().getWrites(i).getKey(),
									nsRwSet.getRwset().getWrites(i).getValue().toStringUtf8(),
									nsRwSet.getRwset().getWrites(i).getIsDelete()
								);
								txWriteSet.add(writeSetTxsEntity);
							}
						}

						// Составление сущности транзакции
						TransactionEntity transactionEntity = new TransactionEntity(
							txTime,
							txTime,
							j,
							// todo
							//blockData.get_blockchainId(),
							222,
							Hex.toHexString(blockHash),
							txId,
							chainCodeName,
							txMethod,
							"VALID",
							Hex.toHexString(proposalResponsePayload),
							txPayload,
							txWriteSet
						);

						// Парсинг только transfer транзакций
						if (txMethod.equals("transfer") || txMethod.equals("pay/refund") ) {
							ObjectNode txPayloadObject = new ObjectMapper().readValue(txPayload, ObjectNode.class);

							if (txPayloadObject.has("@balance_log")) {
								transactionEntity.set_payloadBalanceLogNumber(txPayloadObject.get("@balance_log").textValue());
							}
							if (txPayloadObject.has("type")) {
								transactionEntity.set_payloadBalanceLogType(txPayloadObject.get("type").asLong());
							}
							if (txPayloadObject.has("rid")) {
								transactionEntity.set_payloadBalanceLogRid(txPayloadObject.get("rid").textValue());
							}
							if (txPayloadObject.has("diff")) {
								transactionEntity.set_payloadBalanceLogDiff(txPayloadObject.get("diff").asLong());
							}
							if (txPayloadObject.has("fee")) {
								transactionEntity.set_payloadBalanceLogFee(txPayloadObject.get("fee").asLong());
							}
							if (txPayloadObject.has("amount")) {
								transactionEntity.set_payloadBalanceLogAmount(txPayloadObject.get("amount").asLong());
							}
							if (txPayloadObject.has("memo")) {
								transactionEntity.set_payloadBalanceLogMemo(txPayloadObject.get("memo").textValue());
							}
							if (txPayloadObject.has("pay_id")) {
								transactionEntity.set_payloadBalanceLogPayId(txPayloadObject.get("pay_id").textValue());
							}
						}

						// Парсинг только payment транзакций
						if (txMethod.equals("pay")) {
							ObjectNode txPayloadObject = new ObjectMapper().readValue(txPayload, ObjectNode.class);
							System.out.println("txPayloadObject : " + txPayloadObject);
							JsonNode pay = txPayloadObject.get("pay");
							JsonNode balanceLog = txPayloadObject.get("balance_log");

							if (pay.has("@pay")) {
								transactionEntity.set_payloadPayNumber(pay.get("@pay").textValue());
							}
							if (pay.has("pay_id")) {
								transactionEntity.set_payloadPayId(pay.get("pay_id").textValue());
							}
							if (pay.has("amount")) {
								transactionEntity.set_payloadPayAmount(pay.get("amount").asLong());
							}
							if (pay.has("fee")) {
								transactionEntity.set_payloadPayFee(pay.get("fee").asLong());
							}
							if (pay.has("total_refund")) {
								transactionEntity.set_payloadPayTotalRefund(pay.get("total_refund").asLong());
							}
							if (pay.has("rid")) {
								transactionEntity.set_payloadPayRid(pay.get("rid").textValue());
							}
							if (pay.has("order_id")) {
								transactionEntity.set_payloadPayOrderId(pay.get("order_id").asLong());
							}
							if (pay.has("memo")) {
								transactionEntity.set_payloadPayMemo(pay.get("memo").textValue());
							}

							if (balanceLog.has("@balance_log")) {
								transactionEntity.set_payloadBalanceLogNumber(balanceLog.get("@balance_log").textValue());
							}
							if (balanceLog.has("type")) {
								transactionEntity.set_payloadBalanceLogType(balanceLog.get("type").asLong());
							}
							if (balanceLog.has("rid")) {
								transactionEntity.set_payloadBalanceLogRid(balanceLog.get("rid").textValue());
							}
							if (balanceLog.has("diff")) {
								transactionEntity.set_payloadBalanceLogDiff(balanceLog.get("diff").asLong());
							}
							if (balanceLog.has("fee")) {
								transactionEntity.set_payloadBalanceLogFee(balanceLog.get("fee").asLong());
							}
							if (balanceLog.has("amount")) {
								transactionEntity.set_payloadBalanceLogAmount(balanceLog.get("amount").asLong());
							}
							if (balanceLog.has("memo")) {
								transactionEntity.set_payloadBalanceLogMemo(balanceLog.get("memo").textValue());
							}
							if (balanceLog.has("pay_id")) {
								transactionEntity.set_payloadBalanceLogPayId(balanceLog.get("pay_id").textValue());
							}
						}

						System.out.println(j + " : " + transactionEntity.get_txType() + " : " + transactionEntity);
						transactionEntities.add(transactionEntity);
					}
				}


				// Получение первого элемента массива транзакций блока
				if (transactionEntities.size() <= 0) continue;
				TransactionEntity transactionEntity = transactionEntities.get(0);

				BlockEntity blockEntity = new BlockEntity(
					transactionEntity.get_txDate(),
					transactionEntity.get_txTime(),
					j,
				/// todo
				//	blockData.get_blockchainId(),
					222,
					blockTxCount,
					Hex.toHexString(blockHash),
					Hex.toHexString(dataHash),
					Hex.toHexString(previousHash),
					transactionEntities
				);
				blockEntities.add(blockEntity);
				// Сохранение блоков в БД
				Boolean saveIf = this.blockPort.saveDb(blockEntities, j, 10);
//				Boolean saveIf = true;
				// Отчистка массива блоков, когда произойдет запись в БД
				if (saveIf) blockEntities.clear();
			}
		}
	}

	protected static JSONObject strToJson(String str) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(str);
		return (JSONObject) obj;
	}
}
