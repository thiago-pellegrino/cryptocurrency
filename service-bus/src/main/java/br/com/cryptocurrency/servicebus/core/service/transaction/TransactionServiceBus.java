package br.com.cryptocurrency.servicebus.core.service.transaction;

import br.com.cryptocurrency.domain.Transaction;

public interface TransactionServiceBus {

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	public void addTransactionPool(Transaction transaction);

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	public void publishToPropagateTransaction(Transaction transaction);

}
