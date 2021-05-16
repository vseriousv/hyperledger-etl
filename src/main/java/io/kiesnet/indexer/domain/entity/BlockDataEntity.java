package io.kiesnet.indexer.domain.entity;

import java.util.Date;

public class BlockDataEntity {
	private long _id;
	private long _number;
	private long _count;
	private String _shard;
	private long _blockchainId;
	private long _status;
	private Date _startAt;
	private Date _finishAt;
	private Date _createdAt;
	private Date _updatedAt;

	public BlockDataEntity(
		long _id,
		long _number,
		long _count,
		String _shard,
		long _blockchainId,
		long _status
//		Date _startAt,
//		Date _finishAt,
//		Date _createdAt,
//		Date _updatedAt
	){
		this._id = _id;
		this._number = _number;
		this._count = _count;
		this._shard = _shard;
		this._blockchainId = _blockchainId;
		this._status = _status;
//		this._startAt = _startAt;
//		this._finishAt = _finishAt;
//		this._createdAt = _createdAt;
//		this._updatedAt = _updatedAt;
	}

	public long get_id() {
		return _id;
	}

	public long get_number() {
		return _number;
	}

	public long get_count() {
		return _count;
	}

	public String get_shard() {
		return _shard;
	}

	public long get_blockchainId() {
		return _blockchainId;
	}

	public long get_status() {
		return _status;
	}

	public Date get_startAt() {
		return _startAt;
	}

	public Date get_finishAt() {
		return _finishAt;
	}

	public Date get_createdAt() {
		return _createdAt;
	}

	public Date get_updatedAt() {
		return _updatedAt;
	}
}
