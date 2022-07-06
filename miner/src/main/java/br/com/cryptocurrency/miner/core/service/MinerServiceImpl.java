package br.com.cryptocurrency.miner.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Miner;
import br.com.cryptocurrency.repository.miner.MinerRepository;
import br.com.cryptocurrency.servicebus.core.service.miner.MinerServiceBus;

@Component
public class MinerServiceImpl implements MinerService {

	@Autowired
	private MinerServiceBus minerServiceBus;

	@Autowired
	private MinerRepository minerRepository;

	/**
	 * 
	 */
	public void initMiner(String userId) {
		minerServiceBus.initMiner(userId);
	}

	/**
	 * 	
	 */
	@Override
	public Miner getMiner(String userId) {
		return minerRepository.getMiner(userId);
	}

	/**
	 * 	
	 */
	@Override
	public void save(Miner miner) {
		// TODO Auto-generated method stub
	}
}