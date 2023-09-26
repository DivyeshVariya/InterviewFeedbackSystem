package com.feedbackService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestedHeaderNotFoundException extends RuntimeException {

	public RequestedHeaderNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
