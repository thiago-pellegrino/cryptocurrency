package br.com.cryptocurrency.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.com.cryptocurrency" })
public class RepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryApplication.class, args);
	}
}
