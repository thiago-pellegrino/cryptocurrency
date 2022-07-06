package br.com.cryptocurrency.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.repository.configuration.Factory;

@Component
public class Repository {

	@Autowired
	private Factory connectionFactory;

	/**
	 * 
	 * 
	 * @return Connection {@link Connection}
	 */
	public Connection openConnectionSQLite() {
		return connectionFactory.getConnectionSQLite();
	}

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
			PreparedStatement preparedStatement, Statement statement, ResultSet resultSet) {
		connectionFactory.closeConnection(connection, callableStatement, preparedStatement, statement, resultSet);
	}

}