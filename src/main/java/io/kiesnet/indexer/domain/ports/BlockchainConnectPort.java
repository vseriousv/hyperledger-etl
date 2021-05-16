package io.kiesnet.indexer.domain.ports;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.io.IOException;

public interface BlockchainConnectPort {
	Gateway connect() throws IOException, InvalidArgumentException, ProposalException;
}
