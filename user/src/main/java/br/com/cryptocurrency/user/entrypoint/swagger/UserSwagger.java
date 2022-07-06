package br.com.cryptocurrency.user.entrypoint.swagger;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cryptocurrency.domain.ResponseError;
import br.com.cryptocurrency.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Crypto Currency")
public interface UserSwagger {

	@ResponseStatus(CREATED)
	@ApiOperation(value = "Generate User.", notes = "Method to create user.")
	@ApiResponses({ @ApiResponse(code = 201, message = "Sucessful.", response = String.class),
			@ApiResponse(code = 400, message = "A requisicao foi malformada, omitindo atributos obrigatorios, seja no payload ou atraves de atributos na URL.", response = ResponseError.class),
			@ApiResponse(code = 500, message = "Ocorreu um erro no gateway da API ou no micro servico.", response = ResponseError.class) })
	public ResponseEntity<?> generateUser(
			@ApiParam(value = "User.", required = true) @Valid @NotNull(message = "Required") @RequestBody User user);

	@ResponseStatus(OK)
	@ApiOperation(value = "Get Login.", notes = "Method to return user id by login.")
	@ApiResponses({ @ApiResponse(code = 201, message = "Sucessful.", response = String.class),
			@ApiResponse(code = 400, message = "A requisicao foi malformada, omitindo atributos obrigatorios, seja no payload ou atraves de atributos na URL.", response = ResponseError.class),
			@ApiResponse(code = 500, message = "Ocorreu um erro no gateway da API ou no micro servico.", response = ResponseError.class) })
	public ResponseEntity<?> getLogin(
			@ApiParam(value = "User.", required = true) @Valid @NotNull(message = "Required") @RequestBody User user);

}
