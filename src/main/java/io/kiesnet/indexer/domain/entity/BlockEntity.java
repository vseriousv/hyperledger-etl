package io.kiesnet.indexer.domain.entity;

import java.util.ArrayList;
import java.util.Date;

public class BlockEntity {
	private Date _txDate;
	private Date _txTime;
	private long _block;
	private long _blockchainId;
	private long _blockTxCount;
	private String _blockHash;
	private String _dataHash;
	private String _previousHash;
	private ArrayList<TransactionEntity> _transactions;

	public BlockEntity(
		Date _txDate,
		Date _txTime,
		long _block,
		long _blockchainId,
		long _blockTxCount,
		String _blockHash,
		String _dataHash,
		String _previousHash,
		ArrayList<TransactionEntity> _transactions
	) {
		this._block = _block;
		this._blockchainId = _blockchainId;
		this._txDate = _txDate;
		this._txTime = _txTime;
		this._blockTxCount = _blockTxCount;
		this._blockHash = _blockHash;
		this._dataHash = _dataHash;
		this._previousHash = _previousHash;
		this._transactions = _transactions;
	}

	public long get_block() {
		return _block;
	}

	public long get_blockchainId() {
		return _blockchainId;
	}

	public Date get_txDate() {
		return _txDate;
	}

	public Date get_txTime() {
		return _txTime;
	}

	public long get_blockTxCount() {
		return _blockTxCount;
	}

	public String get_blockHash() {
		return _blockHash;
	}

	public String get_dataHash() {
		return _dataHash;
	}

	public String get_previousHash() {
		return _previousHash;
	}

	public ArrayList<TransactionEntity> get_transactions() {
		return _transactions;
	}
}
