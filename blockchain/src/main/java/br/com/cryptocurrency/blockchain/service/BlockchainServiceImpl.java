package br.com.cryptocurrency.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.repository.blockchain.BlockchainRepository;

@Component
public class BlockchainServiceImpl implements BlockchainService {

	@Autowired
	private BlockchainRepository blockchainRepository;

	/**
	 * 
	 */
	@Override
	public Blockchain getBlockchain() {
		return blockchainRepository.getBlockchain();
	}

	/**
	 * 
	 */
	@Override
	public void saveBlockchain(Blockchain blockchain) {
		blockchainRepository.save(blockchain);
	}

}
