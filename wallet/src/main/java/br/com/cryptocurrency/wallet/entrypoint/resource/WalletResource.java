package br.com.cryptocurrency.wallet.entrypoint.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cryptocurrency.domain.Wallet;
import br.com.cryptocurrency.wallet.core.service.WalletService;
import br.com.cryptocurrency.wallet.entrypoint.resource.response.WalletResponse;
import br.com.cryptocurrency.wallet.entrypoint.swagger.WalletSwagger;

@RestController
@RequestMapping(path = "/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletResource implements WalletSwagger {

	@Autowired
	private WalletService walletService;

	/**
	 * 
	 */
	@PostMapping(value = "/generate-wallet/{userId}")
	public ResponseEntity<?> generateWallet(@PathVariable final String userId) {
		Wallet wallet = walletService.generateWallet(userId);
		WalletResponse walletResponse = WalletResponse.builder().id(wallet.getId()).timestamp(wallet.getTimestamp())
				.publicKey(wallet.getPublicKey()).build();
		return new ResponseEntity<>(walletResponse, HttpStatus.CREATED);
	}

	/**
	 * 
	 */
	@GetMapping(value = "/get-wallets/{userId}")
	public ResponseEntity<?> getWallets(@PathVariable final String userId) {
		return new ResponseEntity<>(walletService.getWallets(userId), HttpStatus.OK);
	}

	/**
	 * 
	 */
	@GetMapping(value = "/get-wallet/{walletId}")
	public ResponseEntity<?> getWallet(@PathVariable final String walletId) {
		return new ResponseEntity<>(walletService.getWallet(walletId), HttpStatus.OK);
	}

	/**
	 * 
	 */
	@GetMapping(value = "/get-balance/{walletId}")
	public ResponseEntity<?> getBalance(@PathVariable final String walletId) {
		return new ResponseEntity<>(walletService.getBalance(walletId), HttpStatus.OK);
	}

	/**
	 * 
	 */
	@PostMapping(value = "/create-transaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTransaction(@RequestBody final Wallet wallet) {
		return new ResponseEntity<>(walletService.createTransaction(wallet), HttpStatus.CREATED);
	}
}
