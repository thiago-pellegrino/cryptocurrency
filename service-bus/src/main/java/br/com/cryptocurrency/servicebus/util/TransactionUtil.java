package br.com.cryptocurrency.servicebus.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cryptocurrency.blockchain.util.BlockchainUtil;
import br.com.cryptocurrency.domain.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @param dataHash         {String}
	 * @param stringPrivateKey {String}
	 * @return String
	 * @throws Exception
	 */
	public static String sign(String dataHash, String stringPrivateKey) throws Exception {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory
					.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(stringPrivateKey.getBytes())));
			Signature privateSignature = Signature.getInstance("SHA256withRSA");
			privateSignature.initSign(privateKey);
			privateSignature.update(dataHash.getBytes(StandardCharsets.UTF_8));
			byte[] signature = privateSignature.sign();
			return Base64.getEncoder().encodeToString(signature);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param dataHash        {String}
	 * @param signature       {String}
	 * @param stringPublicKey
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean verify(String dataHash, String signature, String stringPublicKey) throws Exception {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(stringPublicKey));
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);
			Signature publicSignature = Signature.getInstance("SHA256withRSA");
			publicSignature.initVerify(publicKey);
			publicSignature.update(dataHash.getBytes(StandardCharsets.UTF_8));
			byte[] signatureBytes = Base64.getDecoder().decode(signature);
			return publicSignature.verify(signatureBytes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param transaction {Transaction}
	 * @return boolean
	 */
	public static boolean verifySginature(Transaction transaction) {
		try {
			String signaturedTransaction = transaction.getSignaturedTransaction();
			transaction.setSignaturedTransaction(null);
			String dataHash = BlockchainUtil.generateHash(mapper.writeValueAsString(transaction));
			transaction.setSignaturedTransaction(signaturedTransaction);
			return TransactionUtil.verify(dataHash, signaturedTransaction, transaction.getSenderAdress());
		} catch (Exception e) {
			log.error(
					"### Etapa Transaction Pool: verificalção da assinatura da transação antes de adicionar ao transaction pool "
							+ e.getMessage());
			return false;
		}
	}

}
