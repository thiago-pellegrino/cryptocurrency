package br.com.cryptocurrency.repository.miner;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Miner;

@Component
public interface MinerRepository {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Miner getMiner(String userId);
}
