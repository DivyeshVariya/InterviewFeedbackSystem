package com.emailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RequestedHeaderNotFoundException extends RuntimeException {

	public RequestedHeaderNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
