package br.com.cryptocurrency.user.util;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cryptocurrency.blockchain.util.BlockchainUtil;
import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.domain.Wallet;
import br.com.cryptocurrency.enums.EnumTransactionType;
import br.com.cryptocurrency.servicebus.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalletUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @param wallet {Wallet}
	 */
	public static void generateKeys(Wallet wallet) {

		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair keyPair = keyGen.genKeyPair();
			wallet.setPrivateKey(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
			wallet.setPublicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @param wallet {Wallet}
	 * @return Transaction
	 */
	public static Transaction signigTransaction(Wallet wallet) {
		Transaction transaction = null;
		try {
			transaction = wallet.getTransaction();
			transaction.setId(WalletUtil.generateRamdomId(wallet.getId()));
			transaction.setTimestamp(System.currentTimeMillis());
			transaction.setSenderAdress(wallet.getPublicKey().toString());
			String dataHash = BlockchainUtil.generateHash(mapper.writeValueAsString(transaction));
			transaction.setSignaturedTransaction(TransactionUtil.sign(dataHash, wallet.getPrivateKey()));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return transaction;
	}

	/**
	 * 
	 * @param blockchain
	 * @param wallet
	 * @return
	 */
	public static boolean validateBalance(Blockchain blockchain, Wallet wallet) {
		if (wallet.getTransaction().getTransactionType() == EnumTransactionType.EXCHANGE) {
			return true;
		}
		BigDecimal totalAmount = new BigDecimal(0);
		for (Block block : blockchain.getChain()) {
			for (Transaction transaction : block.getTransactions()) {
				if (wallet.getPublicKey().equals(transaction.getSenderAdress())) {
					totalAmount.add(transaction.getAmount());
				}
			}
		}
		if (totalAmount.compareTo(wallet.getTransaction().getAmount()) > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param id {String}
	 * @return String Ex.:
	 *         26cb07a30f27bd0f1ace9c372a57afe28ed535554acba962004f38236b14b7af
	 */
	public static String generateRamdomId(String id) {
		return sha256Hex(UUID.randomUUID() + id + System.currentTimeMillis());
	}

	/**
	 * 
	 * @param key {byte[]}
	 * @return String
	 */
	public static String getHexKey(byte[] key) {
		StringBuffer keyString = new StringBuffer();
		for (int i = 0; i < key.length; ++i) {
			keyString.append(Integer.toHexString(0x0100 + (key[i] & 0x00FF)).substring(1));
		}
		return keyString.toString();
	}
}
