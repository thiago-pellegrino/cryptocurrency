package br.com.cryptocurrency.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = { "br.com.cryptocurrency" })
@EnableFeignClients
public class Userpplication {

	public static void main(String[] args) {
		SpringApplication.run(Userpplication.class, args);
	}
}
