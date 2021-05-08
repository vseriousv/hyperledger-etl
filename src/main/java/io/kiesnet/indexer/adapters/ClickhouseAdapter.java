package io.kiesnet.indexer.adapters;

import io.kiesnet.indexer.domain.entity.BlockEntity;
import io.kiesnet.indexer.domain.ports.BlockPort;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ClickhouseAdapter implements BlockPort {
	@Override
	public BlockEntity saveDb(long block_number, long block_tx_count, String tx_id, Date tx_time, JSONObject payload, ArrayList<String> args, ArrayList<JSONObject> tx_writeset) {
		return null;
	}
}
