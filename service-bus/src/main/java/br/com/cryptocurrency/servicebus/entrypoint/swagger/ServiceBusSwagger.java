package br.com.cryptocurrency.servicebus.entrypoint.swagger;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cryptocurrency.domain.Blockchain;
import br.com.cryptocurrency.domain.Node;
import br.com.cryptocurrency.domain.ResponseError;
import br.com.cryptocurrency.domain.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Crypto Currency")
public interface ServiceBusSwagger {

	String error400 = "The request was malformed, omitting mandatory attributes, either in the payload or through attributes in the URL";
	String error500 = "An error occurred in the API gateway or microservice";
	String successful = "Successful.";

	@ResponseStatus(CREATED)
	@ApiOperation(value = "Connect node at network.", notes = "Method to connect the node at network.")
	@ApiResponses({ @ApiResponse(code = 201, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> connetToNodeNetwork(
			@ApiParam(value = "Node url.", required = true) @Valid @NotNull(message = "Required") @PathVariable final String node,
			@ApiParam(value = "Node.", required = true) @Valid @NotNull(message = "Required") @RequestBody final Node objNode);

	@ResponseStatus(OK)
	@ApiOperation(value = "Blockchain.", notes = "Method to connect the node at network.")
	@ApiResponses({ @ApiResponse(code = 200, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> getBlockchain(
			@ApiParam(value = "Node url.", required = true) @Valid @NotNull(message = "Required") @PathVariable final String node);

	@ResponseStatus(OK)
	@ApiOperation(value = "List of network nodes.", notes = "Method to connect the node at network.")
	@ApiResponses({ @ApiResponse(code = 200, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> getNodes(
			@ApiParam(value = "Node url.", required = true) @Valid @NotNull(message = "Required") @PathVariable final String node);

	@ResponseStatus(CREATED)
	@ApiOperation(value = "Propagate blockchain.", notes = "Method to connect the node at network.")
	@ApiResponses({ @ApiResponse(code = 201, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> propagateBlockchain(
			@ApiParam(value = "Node url.", required = true) @Valid @NotNull(message = "Required") @PathVariable final String node,
			@ApiParam(value = "Blockchain.", required = true) @Valid @NotNull(message = "Required") @RequestBody final Blockchain blockchain);

	@ResponseStatus(CREATED)
	@ApiOperation(value = "Propagate transaction.", notes = "Method to connect the node at network.")
	@ApiResponses({ @ApiResponse(code = 201, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> propagateTransaction(
			@ApiParam(value = "Node url.", required = true) @Valid @NotNull(message = "Required") @PathVariable final String node,
			@ApiParam(value = "Transaction.", required = true) @Valid @NotNull(message = "Required") @RequestBody final Transaction transaction);

}