package br.com.cryptocurrency.servicebus.core.service.miner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cryptocurrency.blockchain.service.BlockchainService;
import br.com.cryptocurrency.blockchain.util.BlockchainUtil;
import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.nodenetwork.core.service.node.NodeService;
import br.com.cryptocurrency.servicebus.core.service.blockchain.BlockchainServiceBus;
import br.com.cryptocurrency.servicebus.core.service.nodenetwork.NodeNetworkServiceBus;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MinerServiceBusImpl implements MinerServiceBus {

	@Autowired
	private NodeService nodeService;

	@Autowired
	private NodeNetworkServiceBus nodeNetworkServiceBus;

	@Autowired
	private BlockchainService blockchainService;

	@Autowired
	private BlockchainServiceBus blockchainServiceBus;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * @param userId {String}
	 * @return String
	 */
	@Override
	public String initMiner(String userId) {
		Blockchain blockchain = blockchainServiceBus.getBlockchain();
		Node node = nodeService.getNode(userId);
//		Miner miner = getMiner(user);
		nodeService.conectNodeToNework(node);
//		if (blockchain == null) {
//			asyncExecution.consumerBlockchainQueue(blockchain);
//		}
		if (blockchainServiceBus.replaceChain(blockchain)) {
			return "new_chain";
		} else {
			return "same_chain";
		}
	}

	/**
	 * @param transactions {Map<String, Transaction>}
	 * @return boolean
	 */
	@Override
	public boolean mineBlock(Map<String, Transaction> transactions) {
		log.info("### Etapa Mineração: Iniciando a mineração");
		try {
			log.info("### Etapa Mineração: recuperando a blockchain mais atualizada");
			Blockchain blockchain = blockchainServiceBus.getBlockchain();

			log.info("### Etapa Mineração: recuperando a lista de blocos da blockchain");
			List<Block> chain = blockchain.getChain();

			log.info("### Etapa Mineração: recuperando o último bloco da blockchain");
			Block lastBlock = chain.get(chain.size() - 1);

			log.info("### Etapa Mineração: recuperando o previousHash do bloco anterior");
			String previousHash = BlockchainUtil.generateHash(mapper.writeValueAsString(lastBlock));

			log.info("### Etapa Mineração: definindo o id do novo bloco");
			BigDecimal id = new BigDecimal(1);
			id.add(lastBlock.getId());

			log.info("### Etapa Mineração: criando o novo bloco com as transações");
			Block block = blockchainServiceBus.createBlock(id, lastBlock.getNounce(), previousHash, transactions);

			if (block != null) {
				log.info("### Etapa Mineração: adicionando o bloco na blockchain");
				chain.add(block);
				blockchain.setChain(chain);
				log.info("### Etapa Mineração: blockchain atualizada: " + mapper.writeValueAsString(blockchain));

				log.info("### Etapa Mineração: persistindo a blockchain atualizada com o novo bloco");
				blockchainService.saveBlockchain(blockchain);

//				TODO: IMPLEMENTAR A PROPAGAÇÃO DA BLOCKCHAIN 
				log.info("### Etapa Mineração: propagando a blockchain atualizada com o novo bloco");
				nodeNetworkServiceBus.propagateBlockchain(blockchain);
			} else {
				log.error("### Etapa Mineração: bloco nulo");
				return false;
			}
		} catch (Exception e) {
			log.error("### Etapa Mineração: ERROR: " + e.getMessage());
			return false;
		}
		return true;
	}

}