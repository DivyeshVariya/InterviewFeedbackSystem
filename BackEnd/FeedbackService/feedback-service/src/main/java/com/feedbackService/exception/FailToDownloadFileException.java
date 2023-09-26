package com.feedbackService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FailToDownloadFileException extends RuntimeException {

	public FailToDownloadFileException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
