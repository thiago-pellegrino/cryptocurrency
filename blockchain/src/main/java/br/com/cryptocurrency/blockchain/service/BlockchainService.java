package br.com.cryptocurrency.blockchain.service;

import br.com.cryptocurrency.domain.Blockchain;

public interface BlockchainService {

	/**
	 * 
	 */
	public Blockchain getBlockchain();

	/**
	 * 
	 */
	public void saveBlockchain(Blockchain blockchain);

}
