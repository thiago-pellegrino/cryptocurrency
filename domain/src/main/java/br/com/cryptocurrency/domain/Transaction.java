package br.com.cryptocurrency.domain;

import java.math.BigDecimal;

import br.com.cryptocurrency.enums.EnumTransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {

	private String id;
	private String senderAdress;
	private String recieverAdress;
	private long timestamp;
	private EnumTransactionType transactionType;
	private BigDecimal amount;
	private BigDecimal tax;
	private BigDecimal total;
	private String signaturedTransaction;
}
