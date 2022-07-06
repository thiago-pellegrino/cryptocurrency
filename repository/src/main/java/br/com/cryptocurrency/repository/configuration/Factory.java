package br.com.cryptocurrency.repository.configuration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Component;

@Component
public interface Factory {

	/**
	 * 
	 * @return Connection {@link Connection}
	 */
	public Connection getConnectionSQLite();

	/**
	 * 
	 * @param connection        {@link Connection}
	 * 
	 * @param callableStatement {@link CallableStatement}
	 * 
	 * @param preparedStatement {@link PreparedStatement}
	 * 
	 * @param statement         {@link Statement}
	 * 
	 * @param resultSet         {@link ResultSet}
	 */
	public void closeConnection(Connection connection, CallableStatement callableStatement,
			PreparedStatement preparedStatement, Statement statement, ResultSet resultSet);

}