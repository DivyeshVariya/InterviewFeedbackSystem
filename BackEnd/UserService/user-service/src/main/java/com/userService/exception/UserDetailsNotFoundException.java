package com.userService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserDetailsNotFoundException extends RuntimeException {

	public UserDetailsNotFoundException(String message) {
		super(message);
	}	
}
