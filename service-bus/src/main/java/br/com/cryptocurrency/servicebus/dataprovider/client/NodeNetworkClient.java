package br.com.cryptocurrency.servicebus.dataprovider.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.Transaction;

@FeignClient(name = "node", url = "${clients.nodenetwork.url}")
public interface NodeNetworkClient {

	@RequestMapping(value = "/{node}/connect-to-nodenetwork", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> connetToNodeNetwork(@PathVariable final String node, @RequestBody final Node objNode);

	@RequestMapping(value = "/{node}/get-blockchain", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getBlockchain(@PathVariable final String node);

	@RequestMapping(value = "/{node}/get-nodes", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getNodes(@PathVariable final String node);

	@RequestMapping(value = "/{node}/propagate-transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> propagateTransacion(@PathVariable final String node, @RequestBody final Transaction transaction);

	@RequestMapping(value = "/{node}/propagate-processed-transactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> propagateProcessedTransacions(@PathVariable final String node,
			@RequestBody final List<Transaction> transaction);

	@RequestMapping(value = "/{node}/propagate-blockchain", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> propagateBlockchain(@PathVariable final String node, @RequestBody final Blockchain blockchain);
}
