package br.com.cryptocurrency.repository.wallet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Wallet;
import br.com.cryptocurrency.repository.Repository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WalletRepositoryImpl extends Repository implements WalletRepository {

	/**
	 * Metodo responsavel por gerar os inserts dos indicadores de cobranças
	 * 
	 * @param competencia {@link Competencia}
	 */
	@Override
	public void save(String userId, Wallet wallet) {

		Connection connection = null;
		Statement statement = null;
		try {

			log.info("Etapa: ABRINDO CONEXÃO");
			connection = openConnectionSQLite();
			connection.setAutoCommit(false);
			log.info("Etapa: CONEXÃO ABERTA");

			statement = connection.createStatement();
			log.info("Etapa: EXECUTANDO BACTH");
			statement.executeBatch();
			log.info("Etapa: BACTH EXECUTADO");
			connection.commit();
			log.info("Etapa: COMMIT");

		} catch (Exception e) {
			log.error("Erro na execução do bacthupdate " + e.toString());
		} finally {
			closeConnection(connection, null, null, statement, null);
		}
	}

	/**
	 * 
	 */
	@Override
	public Wallet getWallet(String walletId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<Wallet> getWallets(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param statement {@link statement}
	 * 
	 */
	public void addBatchUpdate(Statement statement) {

		try {
			StringBuilder saida = new StringBuilder(1024);

			saida.append("INSERT INTO WALLETS(");
			saida.append("       ID ");
			saida.append("      ,TIMESTAMP ");
			saida.append("      ,WALLET ");
			// saida.append("VALUES(strftime('%Y','now', 'localtime') ");
			// saida.append(", (strftime('%Y','now', 'localtime') ");
			// saida.append(" ," + indicadoresDia.getValorEmitidas() + " ); ");

			statement.addBatch(saida.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Erro na montagem do bacthupdate " + e.toString());
		}
	}

}
