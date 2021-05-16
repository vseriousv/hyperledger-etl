package io.kiesnet.indexer.domain.ports;

import io.kiesnet.indexer.domain.entity.BlockDataEntity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface StoragePort {
	BlockDataEntity getBlockData() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
