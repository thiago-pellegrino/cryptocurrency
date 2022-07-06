package br.com.cryptocurrency.miner.entrypoint.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cryptocurrency.miner.core.service.MinerService;
import br.com.cryptocurrency.miner.entrypoint.swagger.MinerSwagger;

@RestController
@RequestMapping(path = "/miner", produces = MediaType.APPLICATION_JSON_VALUE)
public class MinerResource implements MinerSwagger {

	@Autowired
	private MinerService minerService;

	/**
	 * 
	 */
	@GetMapping(value = "/{userId}/get-miner")
	public ResponseEntity<?> getMiner(@PathVariable final String userId) {
		return new ResponseEntity<>(minerService.getMiner(userId), HttpStatus.OK);
	}

}
