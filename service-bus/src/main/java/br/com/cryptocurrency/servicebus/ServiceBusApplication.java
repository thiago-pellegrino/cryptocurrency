package br.com.cryptocurrency.servicebus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = { "br.com.cryptocurrency" })
@EnableFeignClients
@EnableAsync
public class ServiceBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBusApplication.class, args);
	}
}
