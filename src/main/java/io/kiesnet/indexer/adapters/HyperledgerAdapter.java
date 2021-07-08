package io.kiesnet.indexer.adapters;

import io.kiesnet.indexer.domain.ports.BlockchainConnectPort;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
//import sample.UppsalaDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;

public class HyperledgerAdapter implements BlockchainConnectPort {
	private static final String configFilePath = "src/main/resources/";
	private static final String cryptoPath = "src/main/resources/crypto/";
	@Override
	public Gateway connect() throws IOException, InvalidArgumentException, ProposalException {
		Wallet wallet = Wallet.createInMemoryWallet();
//		############TestNet############
		String certificate = readFile(cryptoPath + "test-upp.crt.pem");
		PrivateKey privateKey = readPrivateKeyFromFile(cryptoPath + "test-upp.key.pem");
		Wallet.Identity identity = Wallet.Identity.createIdentity("payprotocol", certificate, privateKey);
		wallet.put("test-upp", identity);
		Path networkConfigFile = Paths.get(configFilePath + "network-config-main.yaml");
		Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "test-upp").networkConfig(networkConfigFile);

//		###############################


//		############MainNet############
	//	String certificate = readFile(cryptoPath+"main-upp.crt.pem");
	//	PrivateKey privateKey = readPrivateKeyFromFile(cryptoPath+ "main-upp.key.pem");
//		Wallet.Identity identity = Wallet.Identity.createIdentity("payprotocol", certificate, privateKey);
//		wallet.put("main-upp", identity);
//		Path networkConfigFile = Paths.get(configFilePath+"network-config-main.yaml");
//		Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "main-upp").networkConfig(networkConfigFile);
//		###############################

		Gateway gateway = builder.connect();
		return gateway;
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
}
