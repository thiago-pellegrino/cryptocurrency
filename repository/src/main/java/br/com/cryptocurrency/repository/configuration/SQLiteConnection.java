package br.com.cryptocurrency.repository.configuration;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SQLiteConnection implements Factory {

	/**
	 * 
	 */
	public Connection getConnectionSQLite() {
		Connection connectionSqlite = null;
		try {
			connectionSqlite = DriverManager
					.getConnection("jdbc:sqlite:file:C:\\sqlite\\memorydb.db?mode=memory&cache=shared");
		} catch (SQLException e) {
			log.error("Nao foi possivel estabelecer conexao com a base de dados SQLITE", e);
		}
		return connectionSqlite;
	}
	

	/**
	 * @param connection
	 *            {@link Connection}
	 * 
	 * @param callableStatement
	 *            {@link CallableStatement}
	 * 
	 * @param preparedStatement
	 *            {@link PreparedStatement }
	 * 
	 * @param statement
	 *            {@link Statement }
	 * 
	 * @param resultSet
	 *            {@link ResultSet}
	 * 
	 */
	public void closeConnection(Connection connection, CallableStatement callableStatement,
			PreparedStatement preparedStatement, Statement statement, ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new RuntimeException("Impossible to close connection with resultset: " + e.getMessage(), e);
			}
		}
		if (callableStatement != null) {
			try {
				callableStatement.close();
			} catch (SQLException e) {
				throw new RuntimeException("Impossible to close connection with statement: " + e.getMessage(), e);
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				throw new RuntimeException("Impossible to close connection with statement: " + e.getMessage(), e);
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {				
				throw new RuntimeException("Impossible to close connection with statement: " + e.getMessage(), e);
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {				
				throw new RuntimeException("Impossible to close connection with database: " + e.getMessage(), e);
			}
		}
	}

}
