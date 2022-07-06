package br.com.cryptocurrency.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Block {

	private BigDecimal id;
	private String previousHash;
	private long timestamp;
	private long nounce;
	private List<Transaction> transactions;
	private String minerTime;
	private String hash;
	
}
