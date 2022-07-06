package br.com.cryptocurrency.blockchain.util;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Blockchain;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockchainUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @param blockchain {Blockchain}
	 * @return boolean
	 */
	public static boolean validateBlockchain(Blockchain blockchain) {
		try {
			Block previousBlock = blockchain.getChain().get(0);
			int blockIndex = 0;

			log.info("### Etapa Mineração: varrendo todos os blocos da blockchain");
			while (blockIndex < blockchain.getChain().size()) {
				Block block = blockchain.getChain().get(blockIndex);
				log.info(
						"### Etapa Mineração: verificando se o previousHash do bloco anterior esta certo para o bloco atual");
				if (!block.getPreviousHash().equals(generateHash(mapper.writeValueAsString(previousBlock)))) {
					return false;
				}

				log.info("### Etapa Mineração: validando o nounce");
				if (!generateHashOperation(previousBlock.getNounce(), block.getNounce())) {
					return false;
				}
				previousBlock = block;
				blockIndex++;
			}
		} catch (Exception e) {
			log.error("### Etapa Mineração: ERROR: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param data {String}
	 * @return String
	 */
	public static String generateHash(String data) {
		return sha256Hex(data);

	}

	/**
	 * 
	 * @param previousNounce {long}
	 * @param nounce         {long}
	 * @return boolean
	 */
	public static boolean generateHashOperation(long previousNounce, long nounce) {

		String hashOperation = sha256Hex(previousNounce * 2 - nounce * 2 + "");
		if (hashOperation.startsWith("0000")) {
			return true;
		}
		return false;
	}

}
