package io.kiesnet.indexer.domain.entity;

import java.util.Date;

public class TransfersEntity {
	private Date _txDate;
	private Date _txTime;
	private long _block;
	private long _blockchainId;
	private String _blockHash;
	private String _txHash;
	private long _txIndex;
	private String _txSigner;
	private String _txType;
	private String _validationCode;
	private String _transferFrom;
	private String _transferTo;
	private long _currencyId;
	private long _value;

	public TransfersEntity(
		Date _txDate,
		Date _txTime,
		long _block,
		long _blockchainId,
		String _blockHash,
		String _txHash,
		long _txIndex,
		String _txSigner,
		String _txType,
		String _validationCode,
		String _transferFrom,
		String _transferTo,
		long _currencyId,
		long _value
	) {
		this._txDate = _txDate;
		this._txTime = _txTime;
		this._block = _block;
		this._blockchainId = _blockchainId;
		this._blockHash = _blockHash;
		this._txHash = _txHash;
		this._txIndex = _txIndex;
		this._txSigner = _txSigner;
		this._txType = _txType;
		this._validationCode = _validationCode;
		this._transferFrom = _transferFrom;
		this._transferTo = _transferTo;
		this._currencyId = _currencyId;
		this._value = _value;
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

	public long get_txIndex() {
		return _txIndex;
	}

	public String get_txSigner() {
		return _txSigner;
	}

	public String get_txType() {
		return _txType;
	}

	public String get_validationCode() {
		return _validationCode;
	}

	public String get_transferFrom() {
		return _transferFrom;
	}

	public String get_transferTo() {
		return _transferTo;
	}

	public long get_currencyId() {
		return _currencyId;
	}

	public long get_value() {
		return _value;
	}
}
