package br.com.cryptocurrency.wallet.entrypoint.swagger;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cryptocurrency.domain.ResponseError;
import br.com.cryptocurrency.domain.Wallet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Crypto Currency")
public interface WalletSwagger {

	String error400 = "The request was malformed, omitting mandatory attributes, either in the payload or through attributes in the URL.";
	String error500 = "An error occurred in the API gateway or microservice.";
	String successful = "Successful.";

	@ResponseStatus(CREATED)
	@ApiOperation(value = "Generate Wallet.", notes = "Method to generate user wallet.")
	@ApiResponses({ @ApiResponse(code = 201, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> generateWallet(
			@ApiParam(value = "User ID.", required = true) @Valid @NotNull(message = "Required") @PathVariable String userId);

	@ResponseStatus(OK)
	@ApiOperation(value = "Get Wallets.", notes = "Method to show a list of user wallets.")
	@ApiResponses({ @ApiResponse(code = 202, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> getWallets(
			@ApiParam(value = "User ID.", required = true) @Valid @NotNull(message = "Required") @PathVariable String userId);

	@ResponseStatus(OK)
	@ApiOperation(value = "Get Wallet.", notes = "Method to show user wallet data.")
	@ApiResponses({ @ApiResponse(code = 202, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> getWallet(
			@ApiParam(value = "User ID.", required = true) @Valid @NotNull(message = "Required") @PathVariable String walletId);

	@ResponseStatus(OK)
	@ApiOperation(value = "Get Wallet Balance.", notes = "Method to show user wallet balance data.")
	@ApiResponses({ @ApiResponse(code = 201, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> getBalance(
			@ApiParam(value = "User ID.", required = true) @Valid @NotNull(message = "Required") @PathVariable String walletId);

	@ResponseStatus(CREATED)
	@ApiOperation(value = "Create transaction.", notes = "Method to propagate transaction to the node network.")
	@ApiResponses({ @ApiResponse(code = 201, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> createTransaction(
			@ApiParam(value = "Transaction.", required = true) @Valid @NotNull(message = "Required") @RequestBody final Wallet wallet);

}
