package br.com.cryptocurrency.servicebus.dataprovider.usercase;

import java.util.List;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;

public interface NodeNetworkBoundary {
	/**
	 * 
	 */
	public void connetToNodeNetwork(String node, Node objNode);

	/**
	 * 
	 */
	public Blockchain getBlockchain(String node);

	/**
	 * 
	 */
	public List<Node> getNodes(String node);

	/**
	 * 
	 */
	public void propagateTransacion(String node, Transaction transaction);

	/**
	 * 
	 */
	public void propagateProcessedTransacions(String node, List<Transaction> transaction);

	/**
	 * 
	 */
	public void propagateBlockchain(String node, Blockchain blockchain);

}
