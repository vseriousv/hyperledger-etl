package io.kiesnet.indexer.adapters;

import io.kiesnet.indexer.domain.entity.BlockEntity;
import io.kiesnet.indexer.domain.entity.TransactionEntity;
import io.kiesnet.indexer.domain.entity.WriteSetTxsEntity;
import io.kiesnet.indexer.domain.ports.BlockPort;
import org.springframework.cglib.core.Block;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class ClickhouseAdapter implements BlockPort {
	private static final HttpClient httpClient = HttpClient.newBuilder()
		.version(HttpClient.Version.HTTP_2)
		.connectTimeout(Duration.ofSeconds(10))
		.build();

	@Override
	public Boolean saveDb(ArrayList<BlockEntity> blockEntities, long blockNumber, Integer limitSaveBlock) throws IOException, InterruptedException {
		if (blockNumber != 0 && blockNumber % limitSaveBlock == 0) {
			for (BlockEntity blockEntity : blockEntities) {
				this.saveBlock(blockEntity);
				ArrayList<TransactionEntity> transactionEntities = blockEntity.get_transactions();
				for (TransactionEntity transactionEntity : transactionEntities) {
					String txDate = dateFormat(transactionEntity.get_txDate(), "yyyy-MM-dd");
					String txTime = dateFormat(transactionEntity.get_txTime(), "yyyy-MM-dd HH:mm:ss");

					String query = "INSERT INTO transactions VALUES (";
					query += stringSave(txDate, false);
					query += stringSave(txTime, false);
					query += numberSave(transactionEntity.get_blockchainId(), false);
					query += numberSave(transactionEntity.get_block(), false);
					query += stringSave(transactionEntity.get_blockHash(), false);
					query += stringSave(transactionEntity.get_txHash(), false);
					query += stringSave(transactionEntity.get_chaincodeName(), false);
					query += stringSave(transactionEntity.get_txType(), false);
					query += stringSave(transactionEntity.get_validationCode(), false);
					query += stringSave(transactionEntity.get_payloadProposalHash(), false);
					query += stringSave(transactionEntity.get_payload(), true);
					query += ")";
					httpResponse(query);
					saveWriteSetTxs(transactionEntity);

					switch (transactionEntity.get_txType()) {
						case "transfer":
							this.saveMethodTransfer(transactionEntity);
							break;
						case "pay":
							this.saveMethodPay(transactionEntity);
							break;
						case "pay/refund":
							this.saveMethodPayRefund(transactionEntity);
							break;
						case "deploy":
							this.saveMethodDeploy();
							break;
						case "register":
							this.saveMethodRegister();
							break;
						case "account/create":
							this.saveMethodAccountCreate();
							break;
						case "upgrade":
							this.saveMethodUpgrade();
							break;
						case "token/create":
							this.saveMethodTokenCreate();
							break;
						case "pay/prune":
							this.saveMethodPayPrune();
							break;
						case "account/holder/add":
							this.saveMethodAccountHolderAdd();
							break;
						case "approve":
							this.saveMethodApprove();
							break;
						case "account/get":
							this.saveAccountGet();
							break;
						case "fee/prune":
							this.saveFeePrune();
							break;
						case "pay/list":
							this.savePayList();
							break;
						default:
							System.out.println("NO METHODS");
							break;
					}
				}
			}
			return true;
		}
		return false;
	}

	private void saveMethodDeploy() throws IOException, InterruptedException {
		System.out.println("Save Transaction: deploy");
	}

	private void saveMethodRegister() {
		System.out.println("Save Transaction: register");
	}

	private void saveMethodAccountCreate() {
		System.out.println("Save Transaction: account/create");
	}

	private void saveMethodUpgrade() {
		System.out.println("Save Transaction: upgrade");
	}

	private void saveMethodTokenCreate() {
		System.out.println("Save Transaction: token/create");
	}

	private void saveMethodTransfer(TransactionEntity transactionEntity) throws IOException, InterruptedException {
		System.out.println("Save Transaction: transfer");
		String txDate = dateFormat(transactionEntity.get_txDate(), "yyyy-MM-dd");
		String txTime = dateFormat(transactionEntity.get_txTime(), "yyyy-MM-dd HH:mm:ss");
		String query = "INSERT INTO tx_transfers VALUES (";
		query += stringSave(txDate, false);
		query += stringSave(txTime, false);
		query += numberSave(transactionEntity.get_blockchainId(), false);
		query += numberSave(transactionEntity.get_block(), false);
		query += stringSave(transactionEntity.get_blockHash(), false);
		query += stringSave(transactionEntity.get_txHash(), false);
		query += stringSave(transactionEntity.get_chaincodeName(), false);
		query += stringSave(transactionEntity.get_txType(), false);
		query += stringSave(transactionEntity.get_validationCode(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogNumber(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogType(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogRid(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogDiff(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogFee(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogAmount(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogMemo(), false);
		query += stringSave(txTime, true);
		query += ");";
		httpResponse(query);
	}

	private void saveMethodPay(TransactionEntity transactionEntity) throws IOException, InterruptedException {
		System.out.println("Save Transaction: pay");
		String txDate = dateFormat(transactionEntity.get_txDate(), "yyyy-MM-dd");
		String txTime = dateFormat(transactionEntity.get_txTime(), "yyyy-MM-dd HH:mm:ss");
		String query = "INSERT INTO tx_payments VALUES (";
		query += stringSave(txDate, false);
		query += stringSave(txTime, false);
		query += numberSave(transactionEntity.get_blockchainId(), false);
		query += numberSave(transactionEntity.get_block(), false);
		query += stringSave(transactionEntity.get_blockHash(), false);
		query += stringSave(transactionEntity.get_txHash(), false);
		query += stringSave(transactionEntity.get_chaincodeName(), false);
		query += stringSave(transactionEntity.get_txType(), false);
		query += stringSave(transactionEntity.get_validationCode(), false);
		query += stringSave(transactionEntity.get_payloadPayNumber(), false);
		query += stringSave(transactionEntity.get_payloadPayId(), false);
		query += numberSave(transactionEntity.get_payloadPayAmount(), false);
		query += numberSave(transactionEntity.get_payloadPayFee(), false);
		query += numberSave(transactionEntity.get_payloadPayTotalRefund(), false);
		query += stringSave(transactionEntity.get_payloadPayRid(), false);
		query += numberSave(transactionEntity.get_payloadPayOrderId(), false);
		query += stringSave(transactionEntity.get_payloadPayMemo(), false);
		query += stringSave(txTime, false);
		query += stringSave(transactionEntity.get_payloadBalanceLogNumber(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogType(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogRid(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogDiff(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogFee(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogAmount(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogMemo(), false);
		query += stringSave(txTime, false);
		query += stringSave(transactionEntity.get_payloadBalanceLogPayId(), true);
		query += ");";
		System.out.println("query:" + query);
		httpResponse(query);
	}

	private void saveMethodPayRefund(TransactionEntity transactionEntity) throws IOException, InterruptedException {
		System.out.println("Save Transaction: pay/refund");
		String txDate = dateFormat(transactionEntity.get_txDate(), "yyyy-MM-dd");
		String txTime = dateFormat(transactionEntity.get_txTime(), "yyyy-MM-dd HH:mm:ss");
		String query = "INSERT INTO tx_refunds VALUES (";
		query += stringSave(txDate, false);
		query += stringSave(txTime, false);
		query += numberSave(transactionEntity.get_blockchainId(), false);
		query += numberSave(transactionEntity.get_block(), false);
		query += stringSave(transactionEntity.get_blockHash(), false);
		query += stringSave(transactionEntity.get_txHash(), false);
		query += stringSave(transactionEntity.get_chaincodeName(), false);
		query += stringSave(transactionEntity.get_txType(), false);
		query += stringSave(transactionEntity.get_validationCode(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogNumber(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogType(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogRid(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogDiff(), false);
		query += numberSave(transactionEntity.get_payloadBalanceLogAmount(), false);
		query += stringSave(transactionEntity.get_payloadBalanceLogMemo(), false);
		query += stringSave(txTime, true);
		query += ");";
		System.out.println("query:" + query);
		httpResponse(query);
	}

	private void saveMethodPayPrune() {
		System.out.println("Save Transaction: pay/prune");
	}

	private void saveMethodAccountHolderAdd() {
		System.out.println("Save Transaction: account/holder/add");
	}

	private void saveMethodApprove() {
		System.out.println("Save Transaction: approve");
	}

	private void saveAccountGet() {
		System.out.println("Save Transaction: account/get");
	}

	private void saveFeePrune() {
		System.out.println("Save Transaction: fee/prune");
	}

	private void savePayList() {
		System.out.println("Save Transaction: pay/list");
	}

	private void saveBlock(BlockEntity blockEntity) throws IOException, InterruptedException {
		System.out.println("Save Block: " + blockEntity.get_block());

		String txDate = dateFormat(blockEntity.get_txDate(), "yyyy-MM-dd");
		String txTime = dateFormat(blockEntity.get_txTime(), "yyyy-MM-dd HH:mm:ss");

		String query = "INSERT INTO blocks VALUES (";
		query += stringSave(txDate, false);
		query += stringSave(txTime, false);
		query += numberSave(blockEntity.get_blockchainId(), false);
		query += numberSave(blockEntity.get_block(), false);
		query += numberSave(blockEntity.get_blockTxCount(), false);
		query += stringSave(blockEntity.get_blockHash(), false);
		query += stringSave(blockEntity.get_dataHash(), false);
		query += stringSave(blockEntity.get_previousHash(), true);
		query += ")";
		httpResponse(query);
	}

	private void saveWriteSetTxs(TransactionEntity transactionEntity) throws IOException, InterruptedException {
		System.out.println("Save Write set txs: " + transactionEntity.get_txHash());

		String insertInto = "INSERT INTO write_set_txs VALUES";
		String query = "";

		for (WriteSetTxsEntity writeSetTxsEntity : transactionEntity.get_writeSetTxsEntities()) {
			String txDate = dateFormat(writeSetTxsEntity.get_txDate(), "yyyy-MM-dd");
			String txTime = dateFormat(writeSetTxsEntity.get_txTime(), "yyyy-MM-dd HH:mm:ss");
			query += "(";
			query += stringSave(txDate, false);
			query += stringSave(txTime, false);
			query += numberSave(writeSetTxsEntity.get_blockchainId(), false);
			query += numberSave(writeSetTxsEntity.get_block(), false);
			query += stringSave(writeSetTxsEntity.get_blockHash(), false);
			query += stringSave(writeSetTxsEntity.get_txHash(), false);
			query += stringSave(writeSetTxsEntity.get_chaincodeName(), false);
			query += numberSave(writeSetTxsEntity.get_setIndex(), false);
			query += stringSave(writeSetTxsEntity.get_setKey(), false);
			query += stringSave(writeSetTxsEntity.get_setValue(), false);
			query += booleanSave(writeSetTxsEntity.get_setIsDelete(), true);
			query += "),";
		}
		if (query.length() > 0) {
			String queryArray = query.substring(0, query.length() - 1);
			String allQuery = insertInto + queryArray + ";";
			httpResponse(allQuery);
		}
	}

	private String stringSave(String str, Boolean close) {
		if (close) {
			return "'" + str + "' ";
		} else {
			return "'" + str + "', ";
		}
	}

	private String numberSave(long number, Boolean close) {
		if (close) {
			return "" + number + " ";
		} else {
			return "" + number + ", ";
		}
	}

	private String dateSave(Date date, Boolean close) {
		if (close) {
			return "" + dateFormat(date, "yyyy-MM-dd HH:mm:ss") + " ";
		} else {
			return "" + dateFormat(date, "yyyy-MM-dd HH:mm:ss") + ", ";
		}
	}

	private String booleanSave(boolean flag, Boolean close) {
		int flagInt = flag ? 1 : 0;
		if (close) {
			return "" + flagInt + " ";
		} else {
			return "" + flagInt + ", ";
		}
	}

	private String dateFormat(Date date, String pattern) {
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		return formatDate.format(date);
	}

	private HttpResponse<String> httpResponse(String query) throws IOException, InterruptedException {
		String url = "http://localhost:8123/?database=hyperledger&user=default&password=Soc123";

		HttpRequest.BodyPublisher bodyQuery = HttpRequest.BodyPublishers.ofString(query);
		HttpRequest request = HttpRequest.newBuilder()
			.POST(bodyQuery)
			.uri(URI.create(url))
			.header("Content-Type", "text/plain")
			.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
}
