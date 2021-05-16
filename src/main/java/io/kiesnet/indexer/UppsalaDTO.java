package io.kiesnet.indexer;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class UppsalaDTO {
	public long block_number;
	public long block_tx_count;
	public String tx_id;
	public String tx_method;
	public Date tx_time;
	public JSONObject payload;
	public ArrayList<String> args = new ArrayList<String>();
	public ArrayList<JSONObject> tx_writeset = new ArrayList<JSONObject>();

	public UppsalaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UppsalaDTO(
		long block_number,
		long block_tx_count,
		String tx_id,
		String tx_method,
		Date tx_time,
		JSONObject payload,
		ArrayList<String> args,
		ArrayList<JSONObject> tx_writeset
	) {
		super();
		this.block_number = block_number;
		this.block_tx_count = block_tx_count;
		this.tx_id = tx_id;
		this.tx_method = tx_method;
		this.tx_time = tx_time;
		this.payload = payload;
		this.args = args;
		this.tx_writeset = tx_writeset;
	}

	public long getBlock_number() {
		return block_number;
	}

	public void setBlock_number(long block_number) {
		this.block_number = block_number;
	}

	public long getBlock_tx_count() {
		return block_tx_count;
	}

	public void setBlock_tx_count(long block_tx_count) {
		this.block_tx_count = block_tx_count;
	}

	public String getTx_id() {
		return tx_id;
	}

	public String getTx_method() {
		return tx_method;
	}

	public void setTx_id(String tx_id) {
		this.tx_id = tx_id;
	}

	public Date getTx_time() {
		return tx_time;
	}

	public void setTx_time(Date tx_time) {
		this.tx_time = tx_time;
	}

	public JSONObject getPayload() {
		return payload;
	}

	public void setPayload(JSONObject payload) {
		this.payload = payload;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}

	public ArrayList<JSONObject> getTx_writeset() {
		return tx_writeset;
	}

	public void setTx_writeset(ArrayList<JSONObject> tx_writeset) {
		this.tx_writeset = tx_writeset;
	}

	@Override
	public String toString() {
		return "UppsalaDTO \n [ \n block_number=" + block_number + ",\n block_tx_count=" + block_tx_count + ",\n tx_id=" + tx_id
			+ ",\n tx_time=" + tx_time + ",\n payload=" + payload + ",\n args=" + args + ",\n tx_writeset=" + tx_writeset
			+ "\n]";
	}

}
