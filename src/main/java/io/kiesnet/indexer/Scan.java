package io.kiesnet.indexer;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.protos.common.Common.Block;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockInfo.EnvelopeInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.TxReadWriteSetInfo.NsRwsetInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.UppsalaDTO;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;

public class Scan {

	private static final String configFilePath = "src/main/resources/";
	private static final String cryptoPath = "src/main/resources/crypto/";
	private static final String testnetDataPath = "data/testnet/";
	private static final String mainnetDataPath = "data/mainnet/";

	public static void worker()
		throws InvalidArgumentException, ProposalException, ParseException, IOException, TransactionException {
		Wallet wallet = Wallet.createInMemoryWallet();

//		############TestNet############
		String certificate = readFile(cryptoPath + "test-upp.crt.pem");
		PrivateKey privateKey = readPrivateKeyFromFile(cryptoPath + "test-upp.key.pem");
		Identity identity = Identity.createIdentity("payprotocol", certificate, privateKey);
		wallet.put("test-upp", identity);
		Path networkConfigFile = Paths.get(configFilePath + "network-config-main.yaml");
		Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "test-upp").networkConfig(networkConfigFile);
//		###############################

//		############MainNet############
//		String certificate = readFile(cryptoPath+"main-upp.crt.pem");
//		PrivateKey privateKey = readPrivateKeyFromFile(cryptoPath+ "main-upp.key.pem");
//		Identity identity = Identity.createIdentity("payprotocol", certificate, privateKey);
//		wallet.put("main-upp", identity);
//		Path networkConfigFile = Paths.get(configFilePath+"network-config-main.yaml");
//		Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "test-upp").networkConfig(networkConfigFile);
//		###############################

		Gateway gateway = builder.connect();
		Network network = gateway.getNetwork("payprotocol");
		Channel channel = network.getChannel();

		ArrayList<UppsalaDTO> uppdata_list = new ArrayList<UppsalaDTO>();

		BlockchainInfo blockchaininfo = channel.queryBlockchainInfo();
		System.out.println(blockchaininfo.getBlockchainInfo());
//		long end_block_number = 0;
//		long start_block_number = 0;
		//an infinite loop start
//		while (true) {
//
//			System.out.println("GET_HEIGHT: " + blockchaininfo.getHeight());
//
//			if (end_block_number == blockchaininfo.getHeight()) {
//				// To change time bach  1s -> 1000
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			} else {
//				start_block_number = end_block_number;
//				end_block_number = blockchaininfo.getHeight();
//				System.out.println("start_block_number: " + start_block_number);
//				System.out.println("end_block_number: " + end_block_number);
//
//				// Получение инфаормации по каждому блоку
//				for (long j = 248811; j < 248841; j++) { //start_block_number -- end_block_number
//					System.out.println("interation: " + j);
//
//					BlockInfo blockInfo = channel.queryBlockByNumber(j);
//					Block block = blockInfo.getBlock();
//					long block_tx_count = block.getData().getDataCount();
//
//					// Получение инфаормации по каждой транзакции в этом блоке
////					for (EnvelopeInfo envelopeinfo : blockInfo.getEnvelopeInfos()) {
////						String tx_id = envelopeinfo.getTransactionID();
////						Date tx_time = envelopeinfo.getTimestamp();
////						String tx_payload = null;
////						String tx_method = null;
////						ArrayList<JSONObject> tx_writeset_json = new ArrayList<JSONObject>();
////						ArrayList<String> args_list = new ArrayList<String>();
////
////						if (
////							envelopeinfo.getType() == BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE && envelopeinfo.isValid()
////						) {
////
////							BlockInfo.TransactionEnvelopeInfo transactionEnvelopeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeinfo;
////
////							for (BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo ta : transactionEnvelopeInfo
////								.getTransactionActionInfos()) {
////								for (int i = 0; i < ta.getChaincodeInputArgsCount(); i++) {
////									if (i == 0) {
////										tx_method = new String(ta.getChaincodeInputArgs(i));
////									}
////									args_list.add(new String(ta.getChaincodeInputArgs(i)));
////								}
////								if (tx_method.equals("pay") || tx_method.equals("pay/refund")
////									|| tx_method.equals("transfer")) {
////									tx_payload = new String(ta.getProposalResponsePayload());
////									JSONObject tx_payload_json = strToJson(tx_payload);
////									for (NsRwsetInfo nsrwset : ta.getTxReadWriteSet().getNsRwsetInfos()) {
////										long writesetcount = nsrwset.getRwset().getWritesCount();
////										for (int i = 0; i < writesetcount; i++) {
////											tx_writeset_json.add(strToJson(
////												nsrwset.getRwset().getWrites(i).getValue().toStringUtf8()));
////										}
////									}
////									UppsalaDTO uppdata = new UppsalaDTO(j, block_tx_count, tx_id, tx_time,
////										tx_payload_json, args_list, tx_writeset_json);
////									uppdata_list.add(uppdata);
////								}
////
////							}
////						}
////					}
//
////					################### Modify to suit your needs ###################
////					if (j != 0 && j % 10 == 0) {
////						System.out.println(j + " scan success");
////						// Set as your own computer path
////						FileWriter writer = new FileWriter(testnetDataPath + "test" + j + "block.txt");
//////						FileWriter writer = new FileWriter(mainnetDataPath + "main" + j + "block.txt");
////						writer.write(uppdata_list.toString());
////						writer.flush();
////						writer.close();
////						System.out.println(j + " writer success");
////						uppdata_list.clear();
////					}
////					#################################################################
//				}
//			}
//		}
	}

	protected static String readFile(String filename) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		byte[] bytes = new byte[5012];
		int n = fis.read(bytes);
		fis.close();
		return new String(bytes, 0, n);
	}

	protected static PrivateKey readPrivateKeyFromFile(String filename) throws IOException {
		String pem = readFile(filename);
		Reader pemReader = new StringReader(pem);
		PEMParser pemParser = new PEMParser(pemReader);
		PrivateKeyInfo pemPair = (PrivateKeyInfo) pemParser.readObject();
		pemParser.close();
		PrivateKey privateKey = new JcaPEMKeyConverter().getPrivateKey(pemPair);
		return privateKey;
	}

	protected static JSONObject strToJson(String str) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(str);
		return (JSONObject) obj;
	}
}
