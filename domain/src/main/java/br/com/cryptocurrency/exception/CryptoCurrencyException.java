package br.com.cryptocurrency.exception;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.cryptocurrency.domain.ErrorResponse;
import br.com.cryptocurrency.enums.EnumCryptoCurrencyError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CryptoCurrencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Integer httpStatus;
	private final List<ErrorResponse> erros;

	/**
	 * 
	 * @param error
	 * @param httpStatus
	 * @param errorResponse
	 */
	private CryptoCurrencyException(@NotNull Throwable error, @NotNull Integer httpStatus,
			@NotNull ErrorResponse errorResponse) {
		super(error);
		this.erros = List.of(errorResponse);
		this.httpStatus = httpStatus;
	}

	/**
	 * 
	 * @param httpStatus
	 * @param errorResponse
	 */
	private CryptoCurrencyException(@NotNull Integer httpStatus, @NotNull ErrorResponse errorResponse) {
		this.erros = List.of(errorResponse);
		this.httpStatus = httpStatus;
	}

	/**
	 * 
	 * @param error
	 * @param httpStatus
	 * @param code
	 * @param title
	 * @param detail
	 */
	public CryptoCurrencyException(@NotNull Throwable error, @NotNull Integer httpStatus, @NotNull String code,
			@NotNull String title, @NotNull String detail) {
		this(error, httpStatus, new ErrorResponse(code, title, detail));
	}

	/**
	 * 
	 * @param httpStatus
	 * @param code
	 * @param title
	 * @param detail
	 */
	public CryptoCurrencyException(@NotNull Integer httpStatus, @NotNull String code, @NotNull String title,
			@NotNull String detail) {
		this(httpStatus, new ErrorResponse(code, title, detail));
	}

	/**
	 * 
	 * @param enumFornecimentError
	 */
	public CryptoCurrencyException(@NotNull EnumCryptoCurrencyError enumFornecimentError) {
		this(requireNonNull(enumFornecimentError).getHttpStatus(),
				ErrorResponse.ofEnumCryptoCurrencyError(enumFornecimentError));
	}
}
