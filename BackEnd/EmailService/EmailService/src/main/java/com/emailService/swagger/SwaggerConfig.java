package com.emailService.swagger;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.security.SecurityScheme;

@Component
public class SwaggerConfig {

	public SecurityScheme createAPIKeyScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}
}
