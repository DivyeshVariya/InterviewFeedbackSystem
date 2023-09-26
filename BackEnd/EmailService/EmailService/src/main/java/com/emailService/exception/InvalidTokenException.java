package com.emailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
