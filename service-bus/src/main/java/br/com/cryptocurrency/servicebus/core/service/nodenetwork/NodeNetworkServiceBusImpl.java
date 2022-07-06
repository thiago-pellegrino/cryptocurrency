package br.com.cryptocurrency.servicebus.core.service.nodenetwork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.nodenetwork.core.service.network.NetworkService;
import br.com.cryptocurrency.nodenetwork.core.service.node.NodeService;
import br.com.cryptocurrency.servicebus.dataprovider.usercase.NodeNetworkBoundary;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NodeNetworkServiceBusImpl implements NodeNetworkServiceBus {

	@Autowired
	private NodeService nodeService;

	@Autowired
	private NetworkService networkService;

	@Autowired
	private NodeNetworkBoundary nodeNetworkBoundary;

	/**
	 * 
	 * @param node {Node}
	 */
	@Override
	public void conectNodeToNework(Node node) {
		nodeService.conectNodeToNework(node);
	}

	/**
	 * 
	 * @param node {Node}
	 */
	@Override
	public void addNode(Node node) {
		nodeService.conectNodeToNework(node);
	}

	/**
	 * 
	 * @return List<Node>
	 */
	@Override
	public List<Node> getNodes() {
		return networkService.getNodes();
	}

	/**
	 * 
	 * @param blockchain {Blockchain}
	 */
	@Override
	public void propagateBlockchain(Blockchain blockchain) {
		log.info("### Etapa Propagação da Blockchain: chamando método de propagação assincrona");
		asyncPropagateBlockchain(blockchain);
	}

	/**
	 * 
	 * @param blockchain {Blockchain}
	 */
	@Async
	private void asyncPropagateBlockchain(Blockchain blockchain) {
		try {
			log.info("### Etapa Propagação Assincrona da Blockchain: recuperando a lista de nós da network");
			for (Node node : networkService.getNodes()) {
				nodeNetworkBoundary.propagateBlockchain(node.getUrl(), blockchain);
			}
		} catch (Exception e) {
			log.error("### Etapa Propagação Assincrona da Blockchain: ERROR: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	public void propagateTransaction(Transaction transaction) {
		log.info("### Etapa Propagação da transaction: recuperando a lista de nós da network");
		asyncPropagateTransaction(transaction);
	}

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	@Async
	private void asyncPropagateTransaction(Transaction transaction) {
		log.info("### Etapa Propagação da transaction: recuperando a lista de nós da network");
		for (Node node : networkService.getNodes()) {
			log.info("### Etapa Propagação da transaction: enviando a transaction para o nó: " + node.getUrl());
			nodeNetworkBoundary.propagateTransacion(node.getUrl(), transaction);
		}
	}
}