package br.com.cryptocurrency.domain;

import static java.util.Objects.requireNonNull;

import javax.validation.constraints.NotNull;

import org.springframework.validation.FieldError;

import br.com.cryptocurrency.enums.EnumCryptoCurrencyError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String code;
	private String title;
	private String detail;

	/**
	 * 
	 * @param error
	 * @return
	 */
	public static ErrorResponse ofEnumCryptoCurrencyError(@NotNull EnumCryptoCurrencyError error) {
		requireNonNull(error);
		return new ErrorResponse(error.getCode(), error.getTitle(), error.getDetail());
	}

	/**
	 * 
	 * @param fieldError
	 * @return
	 */
	public static ErrorResponse ofFieldError(@NotNull FieldError fieldError) {
		requireNonNull(fieldError);
		final var code = requireNonNull(fieldError.getDefaultMessage());
		final var title = requireNonNull(fieldError.getField());
		return new ErrorResponse(code, title, EnumCryptoCurrencyError.getMessageByCode(code));
	}
}
