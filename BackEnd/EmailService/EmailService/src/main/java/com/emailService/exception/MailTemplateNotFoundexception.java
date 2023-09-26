package com.emailService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MailTemplateNotFoundexception extends RuntimeException {

	public MailTemplateNotFoundexception(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
