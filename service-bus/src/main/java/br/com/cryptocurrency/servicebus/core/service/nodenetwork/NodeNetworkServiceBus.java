package br.com.cryptocurrency.servicebus.core.service.nodenetwork;

import java.util.List;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;

public interface NodeNetworkServiceBus {

	/**
	 * @param node {Node}
	 */
	public void conectNodeToNework(Node node);

	/**
	 * 
	 * @return List<Node>
	 */
	public List<Node> getNodes();

	/**
	 * 
	 * @param node {Node}
	 */
	public void addNode(Node node);

	/**
	 * 
	 * @param transaction {Transaction}
	 */
	public void propagateTransaction(Transaction transaction);

	/**
	 * 
	 * @param blockchain {Blockchain}
	 */
	public void propagateBlockchain(Blockchain blockchain);

}
