package br.com.cryptocurrency.repository.blockchain;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.repository.Repository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BlockchainRepositoryImpl extends Repository implements BlockchainRepository {

	/**
	 * Metodo responsavel por gerar os inserts dos indicadores de cobranças
	 * 
	 * @param competencia {@link Competencia}
	 */
	public void consolidar() {
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
	 * @param statement {@link statement}
	 * 
	 */
	public void addBatchUpdate(Statement statement) {

		try {
			StringBuilder saida = new StringBuilder(1024);

			saida.append("INSERT INTO INDICADORES_DIA_MIN(");
			saida.append("       ANO ");
			saida.append("      ,MES ");
			saida.append("      ,DIA ");
			saida.append("      ,HORA ");
			saida.append("      ,MINUTO ");
			saida.append("      ,TIPO_RECEBIMENTO ");
			saida.append("      ,TIPO_COBRANCA    ");
			saida.append("      ,CANCELADOS       ");
			saida.append("      ,VALOR_CANCELADO  ");
			saida.append("      ,RECEBIDOS        ");
			saida.append("      ,VALOR_RECEBIDO   ");
			saida.append("      ,EMITIDOS         ");
			saida.append("      ,VALOR_EMITIDO)    ");
			// saida.append("VALUES(strftime('%Y','now', 'localtime') ");
			// saida.append(" ,strftime('%m','now', 'localtime') ");
			// saida.append(" ,strftime('%d','now', 'localtime') ");
			// saida.append(" ,strftime('%H','now', 'localtime') ");
			// saida.append(" ,strftime('%M','now', 'localtime')");
//			saida.append("VALUES(" + indicadoresDia.getCompetencia().getAno());
//			saida.append("      ," + indicadoresDia.getCompetencia().getMes());
//			saida.append("      ," + indicadoresDia.getCompetencia().getDia());
//			saida.append("      ," + indicadoresDia.getCompetencia().getHora());
//			saida.append("      ," + indicadoresDia.getCompetencia().getMin());
//			saida.append("      ," + indicadoresDia.getTipoRecebimento());
//			saida.append("      ," + indicadoresDia.getTipoCobranca());
//			saida.append("      ," + indicadoresDia.getCanceladas());
//			saida.append("      ," + indicadoresDia.getValorCanceladas());
//			saida.append("      ," + indicadoresDia.getRecebidas());
//			saida.append("      ," + indicadoresDia.getValorRecebidas());
//			saida.append("      ," + indicadoresDia.getEmitidas());
//			saida.append("      ," + indicadoresDia.getValorEmitidas() + " ); ");

			log.info("Etapa: ADICIONANDO INSERT AO BACTHUPDATE");

			statement.addBatch(saida.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Erro na montagem do bacthupdate " + e.toString());
		}
	}

	@Override
	public Blockchain getBlockchain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Blockchain blockchain) {
		// TODO Auto-generated method stub

	}
}
