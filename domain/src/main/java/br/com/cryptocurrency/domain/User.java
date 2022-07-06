package br.com.cryptocurrency.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	private String id;
	private long timestamp;
	private String name;
	private String login;
	private String password;
	private String email;
	private String masterPrivateKey;
	private String masterPublicKey;
	private Node node;
	private List<Wallet> wallets;
	private Miner miner;

}
