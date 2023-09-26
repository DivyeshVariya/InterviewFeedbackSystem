package com.userService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.userService.jwt.JwtAuthenticationEntryPoint;
import com.userService.jwt.JwtFilter;
import com.userService.jwt.JwtLogoutHandler;
import com.userService.swagger.SwaggerConfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppConfig {

	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	SwaggerConfig swaggerConfig;
	
	@Autowired
	JwtLogoutHandler jwtLogoutHandler;

	
	@Bean
	public String getString()
	{
		return new String();
	}

	@Bean
	public UserDetailsService userDetailService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
							auth->auth
						.requestMatchers("/userAuth/**").permitAll()
						.requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
						)
				.authorizeHttpRequests(
						auth -> 
						auth.requestMatchers("/userManager/**").authenticated()
						.anyRequest().authenticated()
						)
				.exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(logout->logout.logoutUrl("/userAuth/logout")
						.addLogoutHandler(jwtLogoutHandler)
						.logoutSuccessHandler((request,response,authentication)->SecurityContextHolder.clearContext()))
				.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception
	{
		return builder.getAuthenticationManager();
	}
	
	@Bean
	public OpenAPI openAPI() {
	    return new OpenAPI().addSecurityItem(new SecurityRequirement().
	            addList("Bearer Authentication"))
	        .components(new Components().addSecuritySchemes
	            ("Bearer Authentication",swaggerConfig.createAPIKeyScheme()
	        ));
	}
}
