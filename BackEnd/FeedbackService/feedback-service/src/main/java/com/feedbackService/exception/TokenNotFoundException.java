package com.feedbackService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TokenNotFoundException extends RuntimeException {

	public TokenNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
