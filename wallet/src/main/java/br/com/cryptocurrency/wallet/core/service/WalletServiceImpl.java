package br.com.cryptocurrency.wallet.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.domain.Wallet;
import br.com.cryptocurrency.repository.wallet.WalletRepository;
import br.com.cryptocurrency.servicebus.core.service.blockchain.BlockchainServiceBus;
import br.com.cryptocurrency.servicebus.core.service.transaction.TransactionServiceBus;
import br.com.cryptocurrency.wallet.util.WalletUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WalletServiceImpl implements WalletService {

	@Autowired
	private BlockchainServiceBus blockchainServiceBus;

	@Autowired
	private TransactionServiceBus transactionServiceBus;

	@Autowired
	private WalletRepository walletRepository;

//	TODO: 01 REMOVER QUANDO A CONSULTA DA WALLET RETORNAR A CHAVE PRIVADA PARA ASSINATURA DA TRANSAÇÃO
	private static String tempPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXjz78T4dTeKW0ALpaktpitPuhp8dtiXPuMpKpcnl9ZQrXGat8vvgNLS/VpaNmi3OESyP2ZJjAvfCn2ENy9tvnB0NioWG9It2lw967SyuQtnwr5JeJ7Nntz5inIYUkUS5l3HmchhOxSpUXLkQtlk/+MjR9q+zy1Omn5GQhHAZyJMK3LI/rziK4xJcnVjR0acCgmw8F6co6jPPgNULXfmRmxkLBGtzY7haxuWAl8E+g4Dth+3sC8k3WNoqHJLkTS9cIlYI+7fBfxtOJZKVNv1Z962vvRByjubKOPB7FZcENJT0cUes0xIAmfEA8iYDlqj3x5u5NTNljDt+89YsPYBGtAgMBAAECggEAB5KSMJQerFaCXQCOTxk4TSdCzVxvM/NitfuT5WCBXnkOdhnjvhtilHH2CmQ4ylZcYrdIaDa1hSfYXuKI2DUqXpw3yzVKB2O3FGG5fr0uQ5BCmwTUZ4FMkDvHok4j82EVAuTltKLkV/7kfSCEKozwmUBT7BWLhMSLmiZotFjMn6ZP0mVTA0OFdmlgpVfCQVPCOHj6ms6ZKSe3svjSI9Sp8eD97Ni6sj3Gsp3FeWh8KS2OaxjEw5Mjo3XQpZBnV7n8DPnI3C4F8HGddVG2Ave8yvy9Nv/uI0qw0epEQib8K55Xupm21acea5ItUQe8+DeHEYSZvpfGOdxHkXpmPxfJYQKBgQDTkXiZ/hepUFymqw0Ey9Xp1mBFUyN1z/5D0Mb/XnmJ6B6TYKJfVarjc2ggGQK57PFQL0P85ilAIwgDp3uFGypAIkPDeqAGP/Rk/lRYO16zhTXLpUAv1V+4E6K1lxEXglyaVoxoslVZx29bVyIvhlln/eooVnzlyK5R2OVM4uJ3kQKBgQC3Y4gl4pHKSke5jAHq5IBUVCmVD5jy5/en1imS5EIL+rE9Ayah8mW4qnFL/3v76QoeKM/5TA17uvbauyWiMVVSptmid+6GmploQT+m9vS6XmtC4nRqRHShCGkpCm1XhfmCmyD8Pvf1fniYf4frO9yFWJd4gwbFE1Isu3OXiLOCXQKBgQC9+K6hH85hQv6ezZLYDqva15sERL3fiM8YV7yIC234zcu+rBPApOzYZYVG5Q5UopSgWoCi8oxxwcLtfRLll1nd0qakPVS5p+2sTc6wl0bWzbNbU9wJ13+mi0Mb6TT4th6umy5iyFbpUbIFA313zRw0y4PZiqeFsSgN6EMlQcj2cQKBgDwTWf13LGVFUhvBdrtZIYT+kSSomGUyglmz+aiKJ+y+siMXjidNPe9xvm20Awyx+cOJgR/cvyoCRG3BEwIAAUBzMiZb/YKQ0KJrhYuPqPoIhkDB6HsLc91w4Oo15T3fwK39XZ/kjNwaEy150a/Iy2PPcsdivOwAeVH4ElTYzl1tAoGAciNuvVrXlhFGiCHW7N9FvVZTwQioZEIeNLh0fQFf0L+Ilsvky33y6IcerTZbkiwrbW+Sttg2uVc2yYQu2TFKZIFk5TljrCMhuPhxo2RdrwbflbuSJ4HOwB7SQvx7rbIoCE4yh6MJVn5po7t26WQGaTQDIo0yE5W6vZQiUGXTRT0=";
	private static String tempPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl48+/E+HU3iltAC6WpLaYrT7oafHbYlz7jKSqXJ5fWUK1xmrfL74DS0v1aWjZotzhEsj9mSYwL3wp9hDcvbb5wdDYqFhvSLdpcPeu0srkLZ8K+SXiezZ7c+YpyGFJFEuZdx5nIYTsUqVFy5ELZZP/jI0favs8tTpp+RkIRwGciTCtyyP684iuMSXJ1Y0dGnAoJsPBenKOozz4DVC135kZsZCwRrc2O4WsblgJfBPoOA7Yft7AvJN1jaKhyS5E0vXCJWCPu3wX8bTiWSlTb9Wfetr70Qco7myjjwexWXBDSU9HFHrNMSAJnxAPImA5ao98ebuTUzZYw7fvPWLD2ARrQIDAQAB";

	/**
	 * 
	 * @param userId {String}
	 * @return Wallet
	 */
	@Override
	public Wallet generateWallet(String userId) {
		Wallet wallet = Wallet.builder().id(WalletUtil.generateRamdomId(userId)).timestamp(System.currentTimeMillis())
				.build();
		WalletUtil.generateKeys(wallet);
//		TODO: 02 IMPLEMENTAR A PERSISTENCIA DOS DADOS DA WALLET
//		walletRepository.save(user, wallet);
		return wallet;
	}

	/**
	 * 
	 * @param userId {String}
	 * @return List<Wallet>
	 */
	@Override
	public List<Wallet> getWallets(String userId) {
		log.info("### Etapa Consulta Wallet: recuperando a lista de wallets do usuário");
		return walletRepository.getWallets(userId);
	}

	/**
	 * 
	 * @param walletId {String}
	 * @return Wallet
	 */
	@Override
	public Wallet getWallet(String walletId) {
		log.info("### Etapa Consulta Wallet: recuperando os dados da wallet");
		return walletRepository.getWallet(walletId);
	}

	/**
	 * 
	 * @param wallet {Wallet}
	 * @return Transaction
	 */
	@Override
	public Transaction createTransaction(Wallet wallet) {

		log.info("### Etapa Transação: criando a transação");
//		TODO: 03 IMPLEMENTAR A CONSULTA DA BLOCKCHAIN PARA VALIDAR O SALDO ANTES DE CRIAR UMA TRANSAÇÃO
		Blockchain blockchain = blockchainServiceBus.getBlockchain();

		log.info("### Etapa Transação: validando o balance do sender");
//		if (blockchain == null || !WalletUtil.validateBalance(blockchain, wallet)) {
//		if (!WalletUtil.validateBalance(blockchain, wallet)) {
//			TODO: 04 IMPLEMENTAR AS EXCEPTIONS
		// throw new Exception();
//			return null;
//		}

		log.info("### Etapa Transação: recuperando os dados da wallet pelo userId");
//		TODO: 05 IMPLEMENTAR A CONSULTA DOS DADOS DA WALLET
//		wallet = walletRepository.getWallet(wallet.getId());

//		TODO: 06 REMOVER QUANDO A CONSULTA DA WALLET RETORNAR A CHAVE PRIVADA PARA ASSINATURA DA TRANSAÇÃO
		wallet.setPrivateKey(tempPrivateKey);
		wallet.setPublicKey(tempPublicKey);

		log.info("### Etapa Transação: assinando a transação, com a privateKey da wallet");
		Transaction transaction = WalletUtil.signigTransaction(wallet);

		log.info("### Etapa Transação: propagando a transação assinada na rede");
//		TODO: 07 IMPLEMENTAR A PROPAGAÇÃO DA TRANSAÇÃO NOS NÓS DA REDE
//		transactionserviceBus.publishToPropagateTransaction(transaction);

		log.info("### Etapa Transação: adicionando a transação no pool local para mineração local");
//		TODO: 08 REMOVER QUANDO A PROPAGAÇÃO PARA OS NÓS ESTIVER IMPLEMENTADA
		transactionServiceBus.addTransactionPool(transaction);
		return transaction;
	}

	/**
	 * 
	 */
	public String getBalance(String walletId) {
//		TODO: 05 IMPLEMENTAR A CONSULTA DOS DADOS DA WALLET
		Wallet wallet = walletRepository.getWallet(walletId);

		BigDecimal totalAmount = new BigDecimal(0);
		log.info("### Etapa Consulta Wallet: calculando o balance da wallet varrendo toda a blockchain");
		for (Block block : blockchainServiceBus.getBlockchain().getChain()) {
			for (Transaction transaction : block.getTransactions()) {
				if (wallet.getPublicKey().equals(transaction.getSenderAdress())) {
					totalAmount.add(transaction.getAmount());
				}
			}
		}
		return totalAmount.toString();
	}

}
