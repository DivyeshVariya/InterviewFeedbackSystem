package com.userService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordMisMatchException extends RuntimeException {

	public PasswordMisMatchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
