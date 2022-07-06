package br.com.cryptocurrency.user.core.service;

import org.springframework.stereotype.Component;

import br.com.cryptocurrency.domain.User;
import br.com.cryptocurrency.domain.Wallet;

@Component
public interface UserService {

	/**
	 * 
	 * @param user {User}
	 * @return User
	 */
	public User generateUser(User user);

	/**
	 * 
	 * @param user {User}
	 * @return User
	 */
	public Wallet getLogin(User user);

}
