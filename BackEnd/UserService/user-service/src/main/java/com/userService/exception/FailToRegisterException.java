package com.userService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FailToRegisterException extends Exception {

	public FailToRegisterException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
