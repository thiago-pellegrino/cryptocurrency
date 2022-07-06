package br.com.cryptocurrency.servicebus.core.service.miner;

import java.util.Map;

import br.com.cryptocurrency.domain.Transaction;

public interface MinerServiceBus {

	/**
	 * 
	 * @param userId {String}
	 * @return String
	 */
	public String initMiner(String userId);

	/**
	 * 
	 * @param transactions {Map<String, Transaction>}
	 * @return
	 */
	public boolean mineBlock(Map<String, Transaction> transactions);

}
