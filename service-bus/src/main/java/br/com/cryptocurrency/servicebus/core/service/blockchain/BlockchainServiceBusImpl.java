package br.com.cryptocurrency.servicebus.core.service.blockchain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cryptocurrency.blockchain.service.BlockchainService;
import br.com.cryptocurrency.blockchain.util.BlockchainUtil;
import br.com.cryptocurrency.domain.Block;
import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.nodenetwork.core.service.network.NetworkService;
import br.com.cryptocurrency.servicebus.dataprovider.usercase.NodeNetworkBoundary;
import br.com.cryptocurrency.servicebus.util.MinerUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BlockchainServiceBusImpl implements BlockchainServiceBus {

	@Autowired
	private NetworkService networkService;

	@Autowired
	private BlockchainService blockchainService;

	@Autowired
	private NodeNetworkBoundary nodeNetworkBoundary;

	@Autowired
	private BlockchainServiceBus blockchainServiceBus;

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 
	 * @return Blockchain
	 */
	@Override
	public Blockchain getBlockchain() {
		log.info("### Etapa Blockchain: recuperando a blockchain mais atual");
//		TODO: IMPLEMENTAR A CONSULTA DA BLOCKCHAIN
//		return blockchainService.getBlockchain();

//		TODO: REMOVER APÓS A RECUPERAÇÃO DA BLOCKCHAIN
		return createGenesisBlock();
	}

	/**
	 * 
	 * @return Blockchain
	 */
	@Override
	public Blockchain createGenesisBlock() {
		log.info("### Etapa Blockchain: criando o bloco geneisis");
		long nounce = 1;
		String previousHash = "0";
		Blockchain blockchain = Blockchain.builder().build();
		List<Block> chain = new ArrayList<Block>();
		Block block = createBlock(new BigDecimal(0), nounce, previousHash, null);
		if (block != null) {
			chain.add(block);
			blockchain.setChain(chain);

			log.info("### Etapa Blockchain: persistindo a blockchain");
//			TODO: IMPLEMENTAR A PERSISTENCIA DA BLOCKCHAIN
//			blockchainService.saveBlockchain(blockchain);
		} else {
			return null;
		}
		return blockchain;
	}

	/**
	 * @param blockchain   {BigDecimal}
	 * @param nounce       {long}
	 * @param previousHash {String}
	 * @param transactions {Map<String, Transaction>}
	 * @return Block
	 */
	@Override
	public Block createBlock(BigDecimal lastBlockId, long nounce, String previousHash,
			Map<String, Transaction> transactions) {
		log.info("### Etapa Mineração: criando a lista das transações a serem adicionadas do bloco");
		List<Transaction> enqueuedtransactions = new ArrayList<Transaction>();
		if (transactions != null) {
			for (Map.Entry<String, Transaction> transaction : transactions.entrySet()) {
				enqueuedtransactions.add(transaction.getValue());
			}
		}

		long startTime = System.currentTimeMillis();

		log.info("### Etapa Mineração: criando o bloco com as transações");
		Block block = Block.builder().previousHash(previousHash).nounce(MinerUtil.proofOfWork(nounce))
				.transactions(enqueuedtransactions).timestamp(System.currentTimeMillis()).build();

		long endTime = System.currentTimeMillis();

		log.info("### Etapa Mineração: setando o tempo de mineração do bloco");
		block.setMinerTime(MinerUtil.getMiningTime(endTime - startTime));

		log.info("### Etapa Mineração: setando o id ao novo bloco criado");
		block.setId(lastBlockId);
		try {
			log.info("### Etapa Mineração: gerando o hash do novo bloco");
			block.setHash(BlockchainUtil.generateHash(mapper.writeValueAsString(block)));
		} catch (JsonProcessingException e) {
			log.error("### Etapa Mineração: erro ao gerar o hash do novo bloco " + e.getMessage());
			return null;
		}
		log.info("### Etapa Mineração: retornando o novo bloco");
		return block;
	}

	/**
	 * 
	 * @param blockchain {Blockchain}
	 * @return boolean
	 */
	@Override
	public boolean replaceChain(Blockchain blockchain) {
		log.info("### Etapa Propagação da Blockchain: validando a maior blockchain");
		Blockchain longestChain = null;
		long maxLength = (long) blockchain.getChain().size();

		log.info("### Etapa Propagação da Blockchain: recuperando os nós da network");
		for (Node node : networkService.getNodes()) {

			log.info("### Etapa Propagação da Blockchain: recuperando a blockchain de cada nó");
			Blockchain nodeBlockchain = nodeNetworkBoundary.getBlockchain(node.getUrl());

			log.info("### Etapa Propagação da Blockchain: validando a blockchain do nó com a maior blockchain");
			if (nodeBlockchain.getChain().size() > maxLength && BlockchainUtil.validateBlockchain(nodeBlockchain)) {
				longestChain = nodeBlockchain;
			}
		}

		log.info("### Etapa Propagação da Blockchain: verificando se a maior blockchain é nula");
		if (longestChain != null) {

			log.info("### Etapa Propagação da Blockchain: persistindo a maior blockchain");
			blockchainService.saveBlockchain(longestChain);
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param blockchain {Blockchain}
	 */
	@Override
	public void publishToBlockchainQueue(Blockchain blockchain) {
		blockchainServiceBus.replaceChain(blockchain);
	}

}