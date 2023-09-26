package com.apiGateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnKnownURLException extends RuntimeException {

	public UnKnownURLException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
