package br.com.cryptocurrency.user.entrypoint.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cryptocurrency.domain.User;
import br.com.cryptocurrency.user.core.service.UserService;
import br.com.cryptocurrency.user.entrypoint.swagger.UserSwagger;

@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource implements UserSwagger {

	@Autowired
	private UserService userService;

	/**
	 * 
	 */
	@PostMapping(value = "/generate-user")
	public ResponseEntity<?> generateUser(@RequestBody final User user) {
		return new ResponseEntity<>(userService.generateUser(user), HttpStatus.CREATED);
	}

	/**
	 * 
	 */
	@GetMapping(value = "/get-login")
	public ResponseEntity<?> getLogin(@RequestBody final User user) {
		return new ResponseEntity<>(userService.getLogin(user), HttpStatus.OK);
	}

}
