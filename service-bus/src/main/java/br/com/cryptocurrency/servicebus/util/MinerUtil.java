package br.com.cryptocurrency.servicebus.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.cryptocurrency.blockchain.util.BlockchainUtil;
import br.com.cryptocurrency.domain.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinerUtil {

	/**
	 * Method responsable to validate de size of transactions data to mine block
	 * 
	 * @param transactions {Map<String, Transaction>}
	 * @return boolean
	 */
	public static boolean validateTransactionsPoolSize(Map<String, Transaction> transaction) {
		try {
			log.info("### Etapa Mineração: validacao tamanho das transacoes");
			log.info("### Etapa Mineração: quantidade transacoes: " + transaction.size());

//			TODO: ALTERAR A IMPLEMENTAÇÃO DE VERIFICAÇÃO DO TAMANHO DAS TRANSAÇÕES
//			long sizeOfTransactions = sizeOf(transaction);

			log.info("### Etapa Mineração: tamanho das transacoes: sizeOfTransactions");
//			TODO: DETERMINAR O TAMANHO DO BLOCO A SER CRIADO, PARA FINS DE VALIDAÇÃO DO PROCESSO E TESTES 
//			CADA TRANSACTION ESTARÁ EM UM BLOCO DISTINTO
//			if (sizeOfTransactions > 900 && sizeOfTransactions <= 1024) {
//			if (sizeOfTransactions <= 1024) {
			return true;
//			}
		} catch (Exception e) {
			log.error("### Etapa Mineração: validacao tamanho das transacoes: " + e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param previousNounce {long}
	 * @return long
	 */
	public static long proofOfWork(long previousNounce) {
		log.info("### Etapa Mineração: realizando o proofOfWork para criação do bloco");
		long newNounce = 1;
		boolean checkNounce = true;
		while (checkNounce) {
			if (BlockchainUtil.generateHashOperation(previousNounce, newNounce)) {
				checkNounce = false;
			} else {
				newNounce++;
			}
		}
		return newNounce;
	}

	/**
	 * 
	 * @param millis
	 * @return
	 */
	public static String getMiningTime(long millis) {
		return String.format("%02d:%02d:%02d.%03d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
				TimeUnit.MILLISECONDS.toMillis(millis)
						- TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis))

		);
	}

	/**
	 * Method responsable to select transactions based on reward of each on of then
	 * 
	 * @param transaction
	 * @return
	 */
	public static List<Transaction> selectTransactions(Transaction transaction) {
		return null;
	}

}
