package io.kiesnet.indexer.domain.entity;

import java.util.ArrayList;
import java.util.Date;

public class TransactionEntity {
	private Date _txDate;
	private Date _txTime;
	private long _block;
	private long _blockchainId;
	private String _blockHash;
	private String _txHash;
	private String _chaincodeName;
	private String _txType;
	private String _validationCode;
	private String _payloadProposalHash;
	private String _payload;
	private ArrayList<WriteSetTxsEntity> _writeSetTxsEntities;

//	for tx_payments
	private String _payloadPayNumber;
	private String _payloadPayId;
	private long _payloadPayAmount;
	private long _payloadPayFee;
	private long _payloadPayTotalRefund;
	private String _payloadPayRid;
	private long _payloadPayOrderId;
	private String _payloadPayMemo;
	private long _payloadPayCreatedTime;

//	for tx_payments and tx_transfers
	private String _payloadBalanceLogNumber;
	private long _payloadBalanceLogType;
	private String _payloadBalanceLogRid;
	private long _payloadBalanceLogDiff;
	private long _payloadBalanceLogFee;
	private long _payloadBalanceLogAmount;
	private String _payloadBalanceLogMemo;
	private Date _payloadBalanceLogCreatedTime;
	private String _payloadBalanceLogPayId;

//	for tx_refunds
	private String _payloadBalanceLog;
	private long _payloadType;
	private String _payloadRid;
	private long _payloadDiff;
	private long _payloadAmount;
	private String _payloadMemo;
	private Date _payloadCreatedTime;


	public TransactionEntity(
		Date _txDate,
		Date _txTime,
		long _block,
		long _blockchainId,
		String _blockHash,
		String _txHash,
		String _chaincodeName,
		String _txType,
		String _validationCode,
		String _payloadProposalHash,
		String _payload,
		ArrayList<WriteSetTxsEntity> _writeSetTxsEntities
	) {
		this._txDate = _txDate;
		this._txTime = _txTime;
		this._block = _block;
		this._blockchainId = _blockchainId;
		this._blockHash = _blockHash;
		this._txHash = _txHash;
		this._chaincodeName = _chaincodeName;
		this._txType = _txType;
		this._validationCode = _validationCode;
		this._payloadProposalHash = _payloadProposalHash;
		this._payload = _payload;
		this._writeSetTxsEntities = _writeSetTxsEntities;
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

	public String get_txType() {
		return _txType;
	}

	public String get_validationCode() {
		return _validationCode;
	}

	public String get_payloadProposalHash() {
		return _payloadProposalHash;
	}

	public String get_payload() {
		return _payload;
	}

	public ArrayList<WriteSetTxsEntity> get_writeSetTxsEntities() {
		return _writeSetTxsEntities;
	}
}
