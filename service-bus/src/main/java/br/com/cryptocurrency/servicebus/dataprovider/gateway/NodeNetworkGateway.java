package br.com.cryptocurrency.servicebus.dataprovider.gateway;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.enums.EnumCryptoCurrencyError;
import br.com.cryptocurrency.exception.CryptoCurrencyException;
import br.com.cryptocurrency.servicebus.dataprovider.client.NodeNetworkClient;
import br.com.cryptocurrency.servicebus.dataprovider.usercase.NodeNetworkBoundary;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NodeNetworkGateway implements NodeNetworkBoundary {

	private NodeNetworkClient nodeNetworkClient;

	/**
	 * 
	 */
	public void connetToNodeNetwork(String node, Node objNode) {
		try {
			log.trace("connetToNodeNetwork");
			nodeNetworkClient.connetToNodeNetwork(node, objNode);
		} catch (FeignException e) {
			feignException(e);
		}
	}

	/**
	 * 
	 */
	public Blockchain getBlockchain(String node) {
		ResponseEntity<?> response = null;
		try {
			log.trace("getBlockchain");
			response = nodeNetworkClient.getBlockchain(node);
		} catch (FeignException e) {
			feignException(e);
		}
		return (Blockchain) response.getBody();
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Node> getNodes(String node) {
		ResponseEntity<?> response = null;
		try {
			log.trace("getNodes");
			response = nodeNetworkClient.getNodes(node);
		} catch (FeignException e) {
			feignException(e);
		}
		return (List<Node>) response.getBody();
	}

	/**
	 * 
	 */
	@Override
	public void propagateTransacion(String node, Transaction transaction) {
		try {
			log.trace("propagateTransacion");
			nodeNetworkClient.propagateTransacion(node, transaction);
		} catch (FeignException e) {
			feignException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void propagateProcessedTransacions(String node, List<Transaction> transaction) {
		try {
			log.trace("propagateProcessedTransacions");
			nodeNetworkClient.propagateProcessedTransacions(node, transaction);
		} catch (FeignException e) {
			feignException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public void propagateBlockchain(String node, Blockchain blockchain) {
		try {
			log.trace("propagateBlockchain");
			nodeNetworkClient.propagateBlockchain(node, blockchain);
		} catch (FeignException e) {
			feignException(e);
		}
	}

	/**
	 * 
	 * @param e
	 */
	private void feignException(FeignException e) {
		log.error("FeignException: {}", e);
		switch (e.status()) {
		case 400:
			log.error("Response status 400: {}", e.responseBody());
			throw new CryptoCurrencyException(EnumCryptoCurrencyError.ERRO_400);
		default:
			log.error("Response error: {}", e.responseBody());
			throw new CryptoCurrencyException(EnumCryptoCurrencyError.ERRO_500);
		}
	}

}
