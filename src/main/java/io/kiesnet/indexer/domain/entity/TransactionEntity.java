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

	public void set_txDate(Date _txDate) {
		this._txDate = _txDate;
	}

	public Date get_txTime() {
		return _txTime;
	}

	public void set_txTime(Date _txTime) {
		this._txTime = _txTime;
	}

	public long get_block() {
		return _block;
	}

	public void set_block(long _block) {
		this._block = _block;
	}

	public long get_blockchainId() {
		return _blockchainId;
	}

	public void set_blockchainId(long _blockchainId) {
		this._blockchainId = _blockchainId;
	}

	public String get_blockHash() {
		return _blockHash;
	}

	public void set_blockHash(String _blockHash) {
		this._blockHash = _blockHash;
	}

	public String get_txHash() {
		return _txHash;
	}

	public void set_txHash(String _txHash) {
		this._txHash = _txHash;
	}

	public String get_chaincodeName() {
		return _chaincodeName;
	}

	public void set_chaincodeName(String _chaincodeName) {
		this._chaincodeName = _chaincodeName;
	}

	public String get_txType() {
		return _txType;
	}

	public void set_txType(String _txType) {
		this._txType = _txType;
	}

	public String get_validationCode() {
		return _validationCode;
	}

	public void set_validationCode(String _validationCode) {
		this._validationCode = _validationCode;
	}

	public String get_payloadProposalHash() {
		return _payloadProposalHash;
	}

	public void set_payloadProposalHash(String _payloadProposalHash) {
		this._payloadProposalHash = _payloadProposalHash;
	}

	public String get_payload() {
		return _payload;
	}

	public void set_payload(String _payload) {
		this._payload = _payload;
	}

	public ArrayList<WriteSetTxsEntity> get_writeSetTxsEntities() {
		return _writeSetTxsEntities;
	}

	public void set_writeSetTxsEntities(ArrayList<WriteSetTxsEntity> _writeSetTxsEntities) {
		this._writeSetTxsEntities = _writeSetTxsEntities;
	}

	public String get_payloadBalanceLogNumber() {
		return _payloadBalanceLogNumber;
	}

	public void set_payloadBalanceLogNumber(String _payloadBalanceLogNumber) {
		this._payloadBalanceLogNumber = _payloadBalanceLogNumber;
	}

	public long get_payloadBalanceLogType() {
		return _payloadBalanceLogType;
	}

	public void set_payloadBalanceLogType(long _payloadBalanceLogType) {
		this._payloadBalanceLogType = _payloadBalanceLogType;
	}

	public String get_payloadBalanceLogRid() {
		return _payloadBalanceLogRid;
	}

	public void set_payloadBalanceLogRid(String _payloadBalanceLogRid) {
		this._payloadBalanceLogRid = _payloadBalanceLogRid;
	}

	public long get_payloadBalanceLogDiff() {
		return _payloadBalanceLogDiff;
	}

	public void set_payloadBalanceLogDiff(long _payloadBalanceLogDiff) {
		this._payloadBalanceLogDiff = _payloadBalanceLogDiff;
	}

	public long get_payloadBalanceLogFee() {
		return _payloadBalanceLogFee;
	}

	public void set_payloadBalanceLogFee(long _payloadBalanceLogFee) {
		this._payloadBalanceLogFee = _payloadBalanceLogFee;
	}

	public long get_payloadBalanceLogAmount() {
		return _payloadBalanceLogAmount;
	}

	public void set_payloadBalanceLogAmount(long _payloadBalanceLogAmount) {
		this._payloadBalanceLogAmount = _payloadBalanceLogAmount;
	}

	public String get_payloadBalanceLogMemo() {
		return _payloadBalanceLogMemo;
	}

	public void set_payloadBalanceLogMemo(String _payloadBalanceLogMemo) {
		this._payloadBalanceLogMemo = _payloadBalanceLogMemo;
	}

	public Date get_payloadBalanceLogCreatedTime() {
		return _payloadBalanceLogCreatedTime;
	}

	public void set_payloadBalanceLogCreatedTime(Date _payloadBalanceLogCreatedTime) {
		this._payloadBalanceLogCreatedTime = _payloadBalanceLogCreatedTime;
	}

	public String get_payloadBalanceLogPayId() {
		return _payloadBalanceLogPayId;
	}

	public void set_payloadBalanceLogPayId(String _payloadBalanceLogPayId) {
		this._payloadBalanceLogPayId = _payloadBalanceLogPayId;
	}

	public String get_payloadPayNumber() {
		return _payloadPayNumber;
	}

	public void set_payloadPayNumber(String _payloadPayNumber) {
		this._payloadPayNumber = _payloadPayNumber;
	}

	public String get_payloadPayId() {
		return _payloadPayId;
	}

	public void set_payloadPayId(String _payloadPayId) {
		this._payloadPayId = _payloadPayId;
	}

	public long get_payloadPayAmount() {
		return _payloadPayAmount;
	}

	public void set_payloadPayAmount(long _payloadPayAmount) {
		this._payloadPayAmount = _payloadPayAmount;
	}

	public long get_payloadPayFee() {
		return _payloadPayFee;
	}

	public void set_payloadPayFee(long _payloadPayFee) {
		this._payloadPayFee = _payloadPayFee;
	}

	public long get_payloadPayTotalRefund() {
		return _payloadPayTotalRefund;
	}

	public void set_payloadPayTotalRefund(long _payloadPayTotalRefund) {
		this._payloadPayTotalRefund = _payloadPayTotalRefund;
	}

	public String get_payloadPayRid() {
		return _payloadPayRid;
	}

	public void set_payloadPayRid(String _payloadPayRid) {
		this._payloadPayRid = _payloadPayRid;
	}

	public long get_payloadPayOrderId() {
		return _payloadPayOrderId;
	}

	public void set_payloadPayOrderId(long _payloadPayOrderId) {
		this._payloadPayOrderId = _payloadPayOrderId;
	}

	public String get_payloadPayMemo() {
		return _payloadPayMemo;
	}

	public void set_payloadPayMemo(String _payloadPayMemo) {
		this._payloadPayMemo = _payloadPayMemo;
	}

	public long get_payloadPayCreatedTime() {
		return _payloadPayCreatedTime;
	}

	public void set_payloadPayCreatedTime(long _payloadPayCreatedTime) {
		this._payloadPayCreatedTime = _payloadPayCreatedTime;
	}
}





////	for tx_payments
//	private String _payloadPayNumber;
//	private String _payloadPayId;
//	private long _payloadPayAmount;
//	private long _payloadPayFee;
//	private long _payloadPayTotalRefund;
//	private String _payloadPayRid;
//	private long _payloadPayOrderId;
//	private String _payloadPayMemo;
//	private long _payloadPayCreatedTime;
//
////	for tx_payments and tx_transfers
//	private String _payloadBalanceLogNumber;
//	private long _payloadBalanceLogType;
//	private String _payloadBalanceLogRid;
//	private long _payloadBalanceLogDiff;
//	private long _payloadBalanceLogFee;
//	private long _payloadBalanceLogAmount;
//	private String _payloadBalanceLogMemo;
//	private Date _payloadBalanceLogCreatedTime;
//	private String _payloadBalanceLogPayId;
//
////	for tx_refunds
//	private String _payloadBalanceLog;
//	private long _payloadType;
//	private String _payloadRid;
//	private long _payloadDiff;
//	private long _payloadAmount;
//	private String _payloadMemo;
//	private Date _payloadCreatedTime;

