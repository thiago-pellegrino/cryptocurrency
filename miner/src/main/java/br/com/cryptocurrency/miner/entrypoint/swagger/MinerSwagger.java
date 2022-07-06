package br.com.cryptocurrency.miner.entrypoint.swagger;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cryptocurrency.domain.ResponseError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Crypto Currency")
public interface MinerSwagger {

	String error400 = "The request was malformed, omitting mandatory attributes, either in the payload or through attributes in the URL";
	String error500 = "An error occurred in the API gateway or microservice";
	String successful = "Successful.";

	@ResponseStatus(OK)
	@ApiOperation(value = "Get Miner.", notes = "Method to propagate transaction to the node network.")
	@ApiResponses({ @ApiResponse(code = 200, message = successful, response = String.class),
			@ApiResponse(code = 400, message = error400, response = ResponseError.class),
			@ApiResponse(code = 500, message = error500, response = ResponseError.class) })
	public ResponseEntity<?> getMiner(
			@ApiParam(value = "User ID.", required = true) @Valid @NotNull(message = "Required") @PathVariable final String userId);

}
