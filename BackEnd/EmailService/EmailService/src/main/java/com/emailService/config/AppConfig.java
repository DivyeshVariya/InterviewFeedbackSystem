package com.emailService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emailService.swagger.SwaggerConfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
@Configuration
public class AppConfig {

	@Autowired
	SwaggerConfig swaggerConfig;
	
	private Logger logger= LoggerFactory.getLogger(AppConfig.class); 
	
 @Bean
	public OpenAPI openAPI() {
	    return new OpenAPI().addSecurityItem(new SecurityRequirement().
	            addList("Bearer Authentication"))
	        .components(new Components().addSecuritySchemes
	            ("Bearer Authentication",swaggerConfig.createAPIKeyScheme()
	        ));
	}
}
