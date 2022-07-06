package br.com.cryptocurrency.servicebus.core.config;

import java.util.concurrent.ForkJoinPool;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ForkJoinConfiguration {

	@Value("${server.parallelism}")
	private String parallelism;

	@Bean
	public ForkJoinPool getForkJoinPool() {
		return new ForkJoinPool(Integer.valueOf(parallelism));
	}

	@PostConstruct
	public void setProperties() {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", parallelism);
	}

}