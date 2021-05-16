package io.kiesnet.indexer.adapters;

import io.kiesnet.indexer.domain.entity.BlockEntity;
import io.kiesnet.indexer.domain.entity.TransactionEntity;
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
	private static final String testnetDataPath = "data/testnet/";
	private static final String mainnetDataPath = "data/mainnet/";

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

					String query = "INSERT INTO transactions VALUES ('"+
						txDate + "', '" +
						txTime + "', " +
						transactionEntity.get_blockchainId() + ", " +
						transactionEntity.get_block() + ", '" +
						transactionEntity.get_blockHash() + "', '" +
						transactionEntity.get_txHash() + "', '" +
						transactionEntity.get_chaincodeName() + "', '" +
						transactionEntity.get_txType() + "', '" +
						transactionEntity.get_validationCode() + "', '" +
						transactionEntity.get_payloadProposalHash() + "', '" +
						transactionEntity.get_payload() +
						"')";
					httpResponse(query);

					switch (transactionEntity.get_txType()) {
						case "transfer":
							this.saveMethodTransfer(transactionEntity);
							break;
						case "pay":
							this.saveMethodPay();
							break;
						case "pay/refund":
							this.saveMethodPayRefund();
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
	}

	private void saveMethodPay() {
		System.out.println("Save Transaction: pay");
	}

	private void saveMethodPayRefund() {
		System.out.println("Save Transaction: pay/refund");
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

		String query = "INSERT INTO blocks VALUES ('"+
			txDate + "', '" +
			txTime + "', " +
			blockEntity.get_blockchainId() + ", " +
			blockEntity.get_block() + ", " +
			blockEntity.get_blockTxCount() + ", '" +
			blockEntity.get_blockHash() + "', '" +
			blockEntity.get_dataHash() + "', '" +
			blockEntity.get_previousHash() +
			"')";
		httpResponse(query);
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

// Set as your own computer path
//	FileWriter writer = new FileWriter(testnetDataPath + "test-" + transactionEntity.get_block() + "_" + transactionEntity.get_txHash() + ".txt");
//			writer.write(transactionEntity.toString());
//				writer.flush();
//				writer.close();
