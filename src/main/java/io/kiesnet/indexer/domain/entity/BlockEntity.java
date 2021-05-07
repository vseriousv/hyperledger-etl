package io.kiesnet.indexer.domain.entity;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class BlockEntity {
	public long block_number;
	public long block_tx_count;
	public String tx_id;
	public Date tx_time;
	public JSONObject payload;
	public ArrayList<String> args = new ArrayList<String>();
	public ArrayList<JSONObject> tx_writeset = new ArrayList<JSONObject>();

	public BlockEntity(
		long block_number,
		long block_tx_count,
		String tx_id,
		Date tx_time,
		JSONObject payload,
		ArrayList<String> args,
		ArrayList<JSONObject> tx_writeset
	) {
	}

	public long getBlock_number() {
		return block_number;
	}

	public long getBlock_tx_count() {
		return block_tx_count;
	}

	public String getTx_id() {
		return tx_id;
	}

	public Date getTx_time() {
		return tx_time;
	}

	public JSONObject getPayload() {
		return payload;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public ArrayList<JSONObject> getTx_writeset() {
		return tx_writeset;
	}

}
