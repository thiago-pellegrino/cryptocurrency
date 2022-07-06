package br.com.cryptocurrency.servicebus.core.service.transaction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.servicebus.core.service.blockchain.BlockchainServiceBus;
import br.com.cryptocurrency.servicebus.core.service.miner.MinerServiceBus;
import br.com.cryptocurrency.servicebus.core.service.nodenetwork.NodeNetworkServiceBus;
import br.com.cryptocurrency.servicebus.util.MinerUtil;
import br.com.cryptocurrency.servicebus.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TransactionServiceBusImpl implements TransactionServiceBus {

	@Autowired
	private MinerServiceBus minerServiceBus;

	@Autowired
	private BlockchainServiceBus blockchainServiceBus;

	@Autowired
	private NodeNetworkServiceBus nodeNetworkServiceBus;

	private static Map<String, Transaction> transactions = new HashMap<String, Transaction>();

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	@Override
	public void publishToPropagateTransaction(Transaction transaction) {
		log.info(
				"### Etapa Propagação da transaction: propagando a transaction de forma asyncrona para envio aos nós da network");
		nodeNetworkServiceBus.propagateTransaction(transaction);
	}

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	@Override
	public void addTransactionPool(Transaction transaction) {
		try {
			log.info("### Etapa Transaction Pool: adicionando a transação de forma asyncrona no transaction pool");
			addTransaction(transaction);
		} catch (Exception e) {
			log.error("### Etapa Transaction Pool: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param transaction {Transaction}
	 * @throws Exception
	 */
	@Async
	private void addTransaction(Transaction transaction) throws Exception {
		log.info(
				"### Etapa Transaction Pool: verificação da assinatura da transação antes de adicionar ao transaction pool");
		if (TransactionUtil.verifySginature(transaction)) {
			log.info("### Etapa Transaction Pool: transaction verified OK");

			log.info("### Etapa Transaction Pool: verificando se a transaction já se encontra no transaction pool");
			if (transactions.containsKey(transaction.getId())) {
				log.info(
						"### Etapa Transaction Pool: transaction já se encontra no transaction pool, não readiciona-la");
				return;
			}

			log.info("### Etapa Transaction Pool: verificando se a transaction já se encontra no na blockchain");
			if (verifyTransactionIntoBlockchain(transaction)) {
				log.info("### Etapa Transaction Pool: transaction já se encontra no na blockchain, não readiciona-la");
				return;
			}

			log.info("### Etapa Transaction Pool: adicionando a transaction no transaction pool");
			transactions.put(transaction.getId(), transaction);

			log.info(
					"### Etapa Transaction Pool: definindo o transaction pool a ser minerado, uma foto do momento em que a transaction é adicionada ao pool");
			log.info("### Etapa Transaction Pool: " + transactions.size());
			Map<String, Transaction> listTransactionPool = transactions;

			log.info("### Etapa Transaction Pool: adicionando a transaction no transaction pool");
			addTransactionsToBeMinered(listTransactionPool);
		} else {
			log.info("### Etapa Transaction Pool: transaction verified ERRO");
		}
	}

	/**
	 * @param transaction {Transaction}
	 * @return boolean
	 */
	private boolean verifyTransactionIntoBlockchain(Transaction transaction) {
		log.info("### Etapa Transaction Pool: varrendo os blocos da blockchain");
		for (Block block : blockchainServiceBus.getBlockchain().getChain()) {

			log.info("### Etapa Transaction Pool: varrendo as transacoes dos blocos da blockchain");
			for (Transaction committedTransaction : block.getTransactions()) {

				log.info("### Etapa Transaction Pool: verificando se a transaction já se encontra na blockchain");
				if (committedTransaction.getId().equals(transaction.getId())) {

					log.info(
							"### Etapa Transaction Pool: transaction já se encontra na blockchain, não adiciona-la novamente");
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param listTransactionPool {Map<String, Transaction>}
	 */
	private void addTransactionsToBeMinered(Map<String, Transaction> listTransactionPool) {
		log.info(
				"### Etapa Transaction Pool: validando o tamanho das transactions no transaction pool para serem adicionadas ao bloco");
		if (MinerUtil.validateTransactionsPoolSize(listTransactionPool)) {

			log.info(
					"### Etapa Transaction Pool: Iniciando a mineracao do bloco com as transacoes do transaction pool");
			if (minerServiceBus.mineBlock(listTransactionPool)) {

				log.info("### Etapa Mineração: removendo as transacoes adicionadas ao bloco do transation pool");
				removeFromTransactionPool(listTransactionPool);
			} else {
				log.info("### Etapa Mineração: as" + listTransactionPool.size()
						+ "  transacoes não foram removidas transation pool");
			}
		} else {
			log.info(
					"### Etapa Mineração: quantidade de transacoes no transation pool não satisfatorias para mineração do bloco");
		}
	}

	/**
	 * 
	 * @param listTransactionPool {Map<String, Transaction>}
	 */
	private void removeFromTransactionPool(Map<String, Transaction> listTransactionPool) {
		for (Map.Entry<String, Transaction> committedTransaction : listTransactionPool.entrySet()) {
			transactions.remove(committedTransaction.getKey());
			log.info("### Etapa Mineração: removendo transacao: " + committedTransaction.getKey());
		}
		log.info("### Etapa Mineração: transacoes removidas do transation pool");
	}

}