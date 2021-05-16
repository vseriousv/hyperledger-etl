package io.kiesnet.indexer.adapters;

import io.kiesnet.indexer.domain.entity.BlockDataEntity;
import io.kiesnet.indexer.domain.ports.StoragePort;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MySqlAdapter implements StoragePort {
	private String jdbc;
	private String username;
	private String password;

	public MySqlAdapter(String jdbc, String username, String password) {
		this.jdbc = jdbc;
		this.username = username;
		this.password = password;
	}

	public void close(Connection connection) throws SQLException {
		connection.close();
		System.out.println("Connection is close");
	}

	@Override
	public BlockDataEntity getBlockData() throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		try (Connection connection = DriverManager.getConnection(this.jdbc, this.username, this.password);
				 Statement statement = connection.createStatement()) {
			System.out.println("Connection to Store DB successfull!");
			String query = "SELECT * FROM blocks, blockchains WHERE blocks.blockchain_id=blockchains.id AND blockchains.name='Hyperledger' ORDER BY number DESC LIMIT 1;";
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();

			BlockDataEntity blockDataEntity = new BlockDataEntity(
				Long.valueOf(resultSet.getString("blocks.id")),
				Long.valueOf(resultSet.getString("blocks.number")),
				Long.valueOf(resultSet.getString("blocks.count")),
				resultSet.getString("blocks.shard"),
				Long.valueOf(resultSet.getString("blocks.blockchain_id")),
				Long.valueOf(resultSet.getString("blocks.status"))
				);
			return blockDataEntity;
		}
	}
}
