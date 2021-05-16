package io.kiesnet.indexer.domain.ports;

import io.kiesnet.indexer.UppsalaDTO;
import io.kiesnet.indexer.domain.entity.BlockEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public interface BlockPort {
	Boolean saveDb(ArrayList<BlockEntity> blockEntities, long blockNumber, Integer limitSaveBlock) throws IOException, URISyntaxException, InterruptedException;
}
