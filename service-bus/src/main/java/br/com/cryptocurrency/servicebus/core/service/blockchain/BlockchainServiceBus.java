package br.com.cryptocurrency.servicebus.core.service.blockchain;

import java.math.BigDecimal;
import java.util.Map;

import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Transaction;

public interface BlockchainServiceBus {

	/**
	 * 
	 * @param blockchain {Blockchain}
	 */
	public void publishToBlockchainQueue(Blockchain blockchain);

	/**
	 * 
	 * @return Blockchain
	 */
	public Blockchain createGenesisBlock();

	/**
	 * 
	 * @return Blockchain
	 */
	public Blockchain getBlockchain();

	/**
	 * 
	 * @param blockchain {Blockchain}
	 * @return boolean
	 */
	public boolean replaceChain(Blockchain blockchain);

	/**
	 * @param lastBlockId  {BigDecimal}
	 * @param nounce       {long}
	 * @param previousHash {String}
	 * @param transactions {Map<String, Transaction>}
	 * @return Block
	 */
	public Block createBlock(BigDecimal lastBlockId, long nounce, String previousHash,
			Map<String, Transaction> transactions);

}
