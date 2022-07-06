package br.com.cryptocurrency.exception;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import br.com.cryptocurrency.domain.ErrorResponse;
import br.com.cryptocurrency.domain.ResponseError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ControllerAdvice
public class CryptoCurrencyExceptionHandler {

	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<ResponseError> exceptionHandle(CryptoCurrencyException e) {
		log.error("traduzindo CryptoCurrencyException para ResponseEntity", e);
		return ResponseEntity.status(e.getHttpStatus()).body(defaultResponseFromErros(e.getErros()));
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseError> validation(MethodArgumentNotValidException e) {
		log.error("traduzindo MethodArgumentNotValidException para ResponseEntity", e);
		final var erros = e.getBindingResult().getFieldErrors().stream().map(ErrorResponse::ofFieldError)
				.collect(toList());

		return ResponseEntity.status(BAD_REQUEST).body(defaultResponseFromErros(erros));
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseError> notReadableException(HttpMessageNotReadableException e) {
		log.error("traduzindo HttpMessageNotReadableException para ResponseEntity", e);
		final var errors = parserHttpMessageNotReadableExceptionMessage(requireNonNull(e.getMessage()));

		return ResponseEntity.status(BAD_REQUEST).body(defaultResponseFromErros(errors));
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	private List<ErrorResponse> parserHttpMessageNotReadableExceptionMessage(@NotNull String message) {
		requireNonNull(message);
		final var INVALID_ENUM = "INVALID ENUM";
		final var splited = message.split("String");
		final var specificMessages = splited[1].replace(":", " ").split(";");
		return List.of(new ErrorResponse(INVALID_ENUM, INVALID_ENUM, specificMessages[0]));
	}

	/**
	 * 
	 * @param errorList
	 * @return
	 */
	private ResponseError defaultResponseFromErros(List<ErrorResponse> errorList) {
		return ResponseError.builder().errors(errorList).build();
	}
}
