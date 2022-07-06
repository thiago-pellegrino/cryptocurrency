package br.com.cryptocurrency.servicebus.core.config;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.cryptocurrency.servicebus.core.config.properties.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SpringFoxConfiguration {              
	
	 private static final SwaggerProperties swaggerProperties = new SwaggerProperties();

	 private static final String PATH = "br.com.cryptocurrency";
	 
	 @Bean
	 public Docket api() {
		 return new Docket(SWAGGER_2)
        		 .select()                                  
        		 .apis(RequestHandlerSelectors.basePackage(PATH))              
	             .paths(PathSelectors.any())                          
	             .build()
                 .useDefaultResponseMessages(false)
                 .apiInfo(apiInfo())
                 .forCodeGeneration(true)
                 .directModelSubstitute(LocalDate.class, String.class)
                 .directModelSubstitute(LocalTime.class, String.class)
                 .directModelSubstitute(LocalDateTime.class, String.class)
                 .directModelSubstitute(ZonedDateTime.class, String.class);
	 }

     private ApiInfo apiInfo() {

    	 final Contact contact = new Contact(swaggerProperties.getContactName(),
                swaggerProperties.getContactUrl(),
                swaggerProperties.getContactEmail());

    	 return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(contact)
                .build();
    }
}