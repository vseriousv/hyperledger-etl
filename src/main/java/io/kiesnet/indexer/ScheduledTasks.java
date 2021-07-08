package io.kiesnet.indexer;

import io.kiesnet.indexer.adapters.ClickhouseAdapter;
import io.kiesnet.indexer.adapters.HyperledgerAdapter;
import io.kiesnet.indexer.adapters.MySqlAdapter;
import io.kiesnet.indexer.domain.ports.BlockPort;
import io.kiesnet.indexer.domain.ports.BlockchainConnectPort;
import io.kiesnet.indexer.domain.ports.StoragePort;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Component
public class ScheduledTasks {
	@Autowired
	public void reportCurrentTime() throws InvalidArgumentException, ProposalException, ParseException, IOException, SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, URISyntaxException, InterruptedException, NoSuchFieldException {
		BlockPort blockPort = new ClickhouseAdapter();
		BlockchainConnectPort blockchainConnectPort = new HyperledgerAdapter();
		StoragePort storagePort = new MySqlAdapter("jdbc:mysql://localhost:3306/spy", "root", "hello");
		Scan scan = new Scan(blockPort, blockchainConnectPort, storagePort);
		scan.worker("payprotocol", 10, 7, 0);
	}
}
