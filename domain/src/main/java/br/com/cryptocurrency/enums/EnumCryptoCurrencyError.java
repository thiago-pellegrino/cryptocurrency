package br.com.cryptocurrency.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCryptoCurrencyError {

	ERRO_400(400, "00001", "Erro de negocio", "Erro de comunicacao com os nós da rede..."),
	ERRO_500(500, "00002", "Indisponibilidade de servico", "O servico retornou com  erro 500");

	private Integer httpStatus;
	private String code;
	private String title;
	private String detail;

	public static String getMessageByCode(String code) {
		return Stream.of(values()).filter(item -> item.getCode().equalsIgnoreCase(code)).findFirst()
				.map(EnumCryptoCurrencyError::getDetail).orElse("mensagem nao cadastrada");
	}

}
