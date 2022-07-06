package br.com.cryptocurrency.servicebus.entrypoint.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;
import br.com.cryptocurrency.servicebus.core.service.blockchain.BlockchainServiceBus;
import br.com.cryptocurrency.servicebus.core.service.nodenetwork.NodeNetworkServiceBus;
import br.com.cryptocurrency.servicebus.core.service.transaction.TransactionServiceBus;
import br.com.cryptocurrency.servicebus.entrypoint.swagger.ServiceBusSwagger;
import br.com.cryptocurrency.servicebus.util.TransactionUtil;

@RestController
@RequestMapping(path = "/cryptocurrency", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceBusResource implements ServiceBusSwagger {

	@Autowired
	private BlockchainServiceBus blockchainServiceBus;

	@Autowired
	private TransactionServiceBus transactionServiceBus;

	@Autowired
	private NodeNetworkServiceBus nodeNetworkServiceBus;

	/**
	 * 
	 */
	@PostMapping(value = "/{node}/connect-to-nodenetwork", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> connetToNodeNetwork(@PathVariable final String node, @RequestBody final Node objNode) {
		nodeNetworkServiceBus.conectNodeToNework(objNode);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * 
	 */
	@GetMapping(value = "/{node}/get-blockchain", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBlockchain(@PathVariable final String node) {
		return new ResponseEntity<>(blockchainServiceBus.getBlockchain(), HttpStatus.OK);
	}

	/**
	 * 
	 */
	@GetMapping(value = "/{node}/get-nodes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getNodes(@PathVariable final String node) {
		return new ResponseEntity<>(nodeNetworkServiceBus.getNodes(), HttpStatus.OK);
	}

	/**
	 * 
	 */
	@PostMapping(value = "/{node}/propagate-blockchain", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> propagateBlockchain(@PathVariable final String node,
			@RequestBody final Blockchain blockchain) {
		blockchainServiceBus.publishToBlockchainQueue(blockchain);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * 
	 */
	@PostMapping(value = "/{node}/propagate-transaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> propagateTransaction(@PathVariable final String node,
			@RequestBody final Transaction transaction) {
		transactionServiceBus.addTransactionPool(transaction);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * 
	 */
	@PostMapping(value = "/verify-signatured-transaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifySginature(@RequestBody final Transaction transaction) {
		return new ResponseEntity<>(TransactionUtil.verifySginature(transaction), HttpStatus.CREATED);
	}
}
