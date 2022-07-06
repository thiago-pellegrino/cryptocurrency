package br.com.cryptocurrency.servicebus.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
public class SwaggerProperties {
	private String version;
	private String title;
	private String description;
	private String license;
	private String licenseUrl;
	private String termsOfServiceUrl;
	private String basePackage;
	private String contactName;
	private String contactUrl;
	private String contactEmail;

}