package com.emailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoAuditing(auditorAwareRef = "DatabaseAuditing")
@OpenAPIDefinition(
		info=@Info(
				title = "Spring Boot Rest API Documentation for Email Microservice",
				description = "Users Microservice used in Internview Feedback System"
						+ " for storing information about Users.",
				version = "v0.0.1",
				contact = @Contact(
						name = "Divyesh Variya",
						email = "Divyesh.Variya@einfochips.com",
						url = "https://github.com/DivyeshVariya/config-server-repo"
						),
				license = @License(
						name = "Spring Boot 3.1.2",
						url = "https://spring.io/blog/2023/07/20/spring-boot-3-1-2-available-now"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "MongoDB Atlas used for storing data.",
				url = "https://cloud.mongodb.com/v2/64a116666ba75905bf720ceb#/clusters"
				)
		)
public class EmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}
}
