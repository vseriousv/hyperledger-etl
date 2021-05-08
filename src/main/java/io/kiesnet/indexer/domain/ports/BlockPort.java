package io.kiesnet.indexer.domain.ports;

import io.kiesnet.indexer.domain.entity.BlockEntity;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public interface BlockPort {
	BlockEntity saveDb(
		long block_number,
		long block_tx_count,
		String tx_id,
		Date tx_time,
		JSONObject payload,
		ArrayList<String> args,
		ArrayList<JSONObject> tx_writeset
	);
}
