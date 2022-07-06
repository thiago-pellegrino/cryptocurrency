package br.com.cryptocurrency.repository.blockchain;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Blockchain;

@Component
public interface BlockchainRepository {

	public Blockchain getBlockchain();

	public void save(Blockchain blockchain);
}
