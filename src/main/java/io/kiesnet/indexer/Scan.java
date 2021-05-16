package io.kiesnet.indexer;

import io.kiesnet.indexer.domain.entity.BlockDataEntity;
import io.kiesnet.indexer.domain.entity.BlockEntity;
import io.kiesnet.indexer.domain.entity.TransactionEntity;
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
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
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
		throws InvalidArgumentException, ProposalException, ParseException, IOException, SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, URISyntaxException, InterruptedException {
		// Подключение к блокчейну
		Gateway gateway = blockchainConnectPort.connect();
		Network network = gateway.getNetwork(tagNetwork);
		Channel channel = network.getChannel();

		ArrayList<BlockEntity> blockEntities = new ArrayList<BlockEntity>();

		BlockchainInfo blockchaininfo = channel.queryBlockchainInfo();

		// Подключаемся к MySQL получаем данные последнего блока
		BlockDataEntity blockData = this.storagePort.getBlockData();
		long endBlockNumber = blockData.get_number();
		long startBlockNumber = 0;

		// Бесконечный цикл проверки блокчейна
		while (true) {
			long heightBlocks = blockchaininfo.getHeight();

			// Если высота блока равна послденему блоку, то спим 2 секунды
			if (heightBlocks == endBlockNumber) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("GET_HEIGHT: " + heightBlocks);
				continue;
			}

			// Обновляем переменные
			startBlockNumber = endBlockNumber;
			endBlockNumber = blockchaininfo.getHeight();

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
					ArrayList<JSONObject> txWritesetJson = new ArrayList<JSONObject>();
					ArrayList<String> argsList = new ArrayList<String>();

					// Проверка валидности транзакции
					if (
						envelopeinfo.getType() != BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE && envelopeinfo.isValid()
					) {
						System.out.println("Transaction is not valid");
						continue;
					}

					BlockInfo.TransactionEnvelopeInfo transactionEnvelopeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeinfo;

					for (BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo ta : transactionEnvelopeInfo
						.getTransactionActionInfos()) {
						for (int i = 0; i < ta.getChaincodeInputArgsCount(); i++) {
							if (i == 0) {
								txMethod = new String(ta.getChaincodeInputArgs(i));
							}
							argsList.add(new String(ta.getChaincodeInputArgs(i)));
						}
						chainCodeName = ta.getChaincodeIDName();
						txPayload = new String(ta.getProposalResponsePayload());
						proposalResponsePayload = ta.getProposalResponsePayload(); // тут есть отличие с сайтом, надо проверить у кого не правильно

						TransactionEntity transactionEntity = new TransactionEntity(
							txTime,
							txTime,
							j,
							blockData.get_blockchainId(),
							Hex.toHexString(blockHash),
							txId,
							chainCodeName,
							txMethod,
							"VALID",
							Hex.toHexString(proposalResponsePayload),
							txPayload
						);
						transactionEntities.add(transactionEntity);
//						if (txMethod.equals("pay") ||
//							txMethod.equals("pay/refund") ||
//							txMethod.equals("transfer")
//						) {
//						txPayload = new String(ta.getProposalResponsePayload());
//
//						JSONObject txPayloadJson = strToJson(txPayload);
//
//						for (NsRwsetInfo nsrwset : ta.getTxReadWriteSet().getNsRwsetInfos()) {
//							long writesetcount = nsrwset.getRwset().getWritesCount();
//							for (int i = 0; i < writesetcount; i++) {
//								txWritesetJson.add(strToJson(
//									nsrwset.getRwset().getWrites(i).getValue().toStringUtf8()));
//							}
//						}
//					}
					}
				}

				BlockEntity blockEntity = new BlockEntity(
					new Date(), // Исправить на данные блока
					new Date(), // Исправить на данные блока
					j,
					blockData.get_blockchainId(),
					blockTxCount,
					Hex.toHexString(blockHash),
					Hex.toHexString(dataHash),
					Hex.toHexString(previousHash),
					transactionEntities
				);
				blockEntities.add(blockEntity);
				// Сохранение блоков в БД
				Boolean saveIf = this.blockPort.saveDb(blockEntities, j, 10);
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
