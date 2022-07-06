package br.com.cryptocurrency.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Wallet {

	private String id;
	private long timestamp;
	private String privateKey;
	private String publicKey;

	private Transaction transaction;

}
