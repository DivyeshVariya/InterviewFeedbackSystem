package com.feedbackService.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorDetailsForException> handleAuthenticationException(AuthenticationException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage("Access denied...!!! "+exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("AUTHENTICATION_FAIL");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		Map<String,String> errors=new HashMap<>();
		List<ObjectError> errorList=ex.getBindingResult().getAllErrors();
		errorList.forEach((error)->{
				String fieldName=((FieldError)error).getField();
				String message=error.getDefaultMessage();
				errors.put(fieldName,message);
		});
		
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FailToSaveDocumentException.class)
	public ResponseEntity<ErrorDetailsForException> handleFailToSaveDocumentException(FailToSaveDocumentException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("FAIL_TO_SAVE_DATA");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FailToDownloadFileException.class)
	public ResponseEntity<ErrorDetailsForException> handleFailToDownloadFileException(FailToDownloadFileException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("FAIL_TO_DOWNLOAD_FILE");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FailToParseDataException.class)
	public ResponseEntity<ErrorDetailsForException> handleFailToParseDataException(FailToParseDataException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("FAIL_TO_PARSE_FILE");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FeedbackNotFoundException.class)
	public ResponseEntity<ErrorDetailsForException> handleFeedbackNotFoundException(FeedbackNotFoundException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode(HttpStatus.NOT_FOUND.name());
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsForException> handleGlobalException(Exception exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("INTERNAL_SERVER_ERROR");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
