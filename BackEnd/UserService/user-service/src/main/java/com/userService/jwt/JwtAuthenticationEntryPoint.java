package com.userService.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.userService.exception.TokenNotFoundException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		resolver.resolveException(request, response, null,authException);

//		if (authException instanceof Expire) {
		// Handle the custom exception with a specific status code and message
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Custom exception occurred: " + authException.getMessage()+"Invalid Header...");
//            resolver.resolveException(request, response, null,authException);
//        }else
//        {
//        	resolver.resolveException(request, response, null,authException);
//        }
	}

}
