package br.com.cryptocurrency.wallet.core.service;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.domain.Wallet;

@Component
public interface WalletService {

	/**
	 * 
	 * @param userId {String}
	 * @return Wallet
	 */
	public Wallet generateWallet(String userId);

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

	/**
	 * 
	 * @param walletId {String}
	 * @return String
	 */
	public String getBalance(String walletId);

	/**
	 * 
	 * @param wallet {String}
	 * @return Transaction
	 */
	public Transaction createTransaction(Wallet wallet);

}
