package br.com.cryptocurrency.repository.wallet;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Wallet;

@Component
public interface WalletRepository {

	/**
	 * 
	 * @param userId {String}
	 * @param wallet {Wallet}
	 */
	public void save(String userId, Wallet wallet);

	/**
	 * 
	 * @param walletId {String}
	 * @return Wallet
	 */
	public Wallet getWallet(String walletId);

	/**
	 * 
	 * @param userId {String}
	 * @return List<Wallet>
	 */
	public List<Wallet> getWallets(String userId);
}
