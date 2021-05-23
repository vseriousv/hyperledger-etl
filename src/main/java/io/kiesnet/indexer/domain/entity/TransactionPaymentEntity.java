package io.kiesnet.indexer.domain.entity;

import java.util.ArrayList;
import java.util.Date;

public class TransactionPaymentEntity extends TransactionEntity {
	private String _payloadPayNumber;
	private String _payloadPayId;
	private long _payloadPayAmount;
	private long _payloadPayFee;
	private long _payloadPayTotalRefund;
	private String _payloadPayRid;
	private long _payloadPayOrderId;
	private String _payloadPayMemo;
	private long _payloadPayCreatedTime;
	private String _payloadBalanceLogNumber;
	private long _payloadBalanceLogType;
	private String _payloadBalanceLogRid;
	private long _payloadBalanceLogDiff;
	private long _payloadBalanceLogFee;
	private long _payloadBalanceLogAmount;
	private String _payloadBalanceLogMemo;
	private Date _payloadBalanceLogCreatedTime;
	private String _payloadBalanceLogPayId;

	public TransactionPaymentEntity(
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
		ArrayList<WriteSetTxsEntity> _writeSetTxsEntities,
		String _payloadPayNumber,
		String _payloadPayId,
		long _payloadPayAmount,
		long _payloadPayFee,
		long _payloadPayTotalRefund,
		String _payloadPayRid,
		long _payloadPayOrderId,
		String _payloadPayMemo,
		long _payloadPayCreatedTime,
		String _payloadBalanceLogNumber,
		long _payloadBalanceLogType,
		String _payloadBalanceLogRid,
		long _payloadBalanceLogDiff,
		long _payloadBalanceLogFee,
		long _payloadBalanceLogAmount,
		String _payloadBalanceLogMemo,
		Date _payloadBalanceLogCreatedTime,
		String _payloadBalanceLogPayId
	) {
		super(_txDate, _txTime, _block, _blockchainId, _blockHash, _txHash, _chaincodeName, _txType, _validationCode, _payloadProposalHash, _payload, _writeSetTxsEntities);
		this._payloadPayNumber = _payloadPayNumber;
		this._payloadPayId = _payloadPayId;
		this._payloadPayAmount = _payloadPayAmount;
		this._payloadPayFee = _payloadPayFee;
		this._payloadPayTotalRefund = _payloadPayTotalRefund;
		this._payloadPayRid = _payloadPayRid;
		this._payloadPayOrderId = _payloadPayOrderId;
		this._payloadPayMemo = _payloadPayMemo;
		this._payloadPayCreatedTime = _payloadPayCreatedTime;
		this._payloadBalanceLogNumber = _payloadBalanceLogNumber;
		this._payloadBalanceLogType = _payloadBalanceLogType;
		this._payloadBalanceLogRid = _payloadBalanceLogRid;
		this._payloadBalanceLogDiff = _payloadBalanceLogDiff;
		this._payloadBalanceLogFee = _payloadBalanceLogFee;
		this._payloadBalanceLogAmount = _payloadBalanceLogAmount;
		this._payloadBalanceLogMemo = _payloadBalanceLogMemo;
		this._payloadBalanceLogCreatedTime = _payloadBalanceLogCreatedTime;
		this._payloadBalanceLogPayId = _payloadBalanceLogPayId;
	}

	public String get_payloadPayNumber() {
		return _payloadPayNumber;
	}

	public String get_payloadPayId() {
		return _payloadPayId;
	}

	public long get_payloadPayAmount() {
		return _payloadPayAmount;
	}

	public long get_payloadPayFee() {
		return _payloadPayFee;
	}

	public long get_payloadPayTotalRefund() {
		return _payloadPayTotalRefund;
	}

	public String get_payloadPayRid() {
		return _payloadPayRid;
	}

	public long get_payloadPayOrderId() {
		return _payloadPayOrderId;
	}

	public String get_payloadPayMemo() {
		return _payloadPayMemo;
	}

	public long get_payloadPayCreatedTime() {
		return _payloadPayCreatedTime;
	}

	public String get_payloadBalanceLogNumber() {
		return _payloadBalanceLogNumber;
	}

	public long get_payloadBalanceLogType() {
		return _payloadBalanceLogType;
	}

	public String get_payloadBalanceLogRid() {
		return _payloadBalanceLogRid;
	}

	public long get_payloadBalanceLogDiff() {
		return _payloadBalanceLogDiff;
	}

	public long get_payloadBalanceLogFee() {
		return _payloadBalanceLogFee;
	}

	public long get_payloadBalanceLogAmount() {
		return _payloadBalanceLogAmount;
	}

	public String get_payloadBalanceLogMemo() {
		return _payloadBalanceLogMemo;
	}

	public Date get_payloadBalanceLogCreatedTime() {
		return _payloadBalanceLogCreatedTime;
	}

	public String get_payloadBalanceLogPayId() {
		return _payloadBalanceLogPayId;
	}
}
