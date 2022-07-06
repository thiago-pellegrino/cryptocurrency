package br.com.cryptocurrency.miner.core.service;

import br.com.cryptocurrency.domain.Miner;

public interface MinerService {

	/**
	 * 
	 */
	public Miner getMiner(String userId);

	/**
	 * 
	 */
	public void save(Miner miner);

}