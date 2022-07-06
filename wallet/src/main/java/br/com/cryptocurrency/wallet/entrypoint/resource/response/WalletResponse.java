package br.com.cryptocurrency.wallet.entrypoint.resource.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletResponse {

	private String id;
	private long timestamp;
	private String publicKey;
}
