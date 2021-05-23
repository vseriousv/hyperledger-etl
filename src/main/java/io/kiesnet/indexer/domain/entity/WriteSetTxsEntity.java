package io.kiesnet.indexer.domain.entity;

import java.util.Date;

public class WriteSetTxsEntity {
	private Date _txDate;
	private Date _txTime;
	private long _block;
	private long _blockchainId;
	private String _blockHash;
	private String _txHash;
	private String _chaincodeName;
	private long _setIndex;
	private String _setKey;
	private String _setValue;
	private Boolean _setIsDelete;

	public WriteSetTxsEntity(
		Date _txDate,
		Date _txTime,
		long _block,
		long _blockchainId,
		String _blockHash,
		String _txHash,
		String _chaincodeName,
		long _setIndex,
		String _setKey,
		String _setValue,
		Boolean _setIsDelete
	){
		this._txDate = _txDate;
		this._txTime = _txTime;
		this._block = _block;
		this._blockchainId = _blockchainId;
		this._blockHash = _blockHash;
		this._txHash = _txHash;
		this._chaincodeName = _chaincodeName;
		this._setIndex = _setIndex;
		this._setKey = _setKey;
		this._setValue = _setValue;
		this._setIsDelete = _setIsDelete;
	}

	public Date get_txDate() {
		return _txDate;
	}

	public Date get_txTime() {
		return _txTime;
	}

	public long get_block() {
		return _block;
	}

	public long get_blockchainId() {
		return _blockchainId;
	}

	public String get_blockHash() {
		return _blockHash;
	}

	public String get_txHash() {
		return _txHash;
	}

	public String get_chaincodeName() {
		return _chaincodeName;
	}

	public long get_setIndex() {
		return _setIndex;
	}

	public String get_setKey() {
		return _setKey;
	}

	public String get_setValue() {
		return _setValue;
	}

	public Boolean get_setIsDelete() {
		return _setIsDelete;
	}
}
