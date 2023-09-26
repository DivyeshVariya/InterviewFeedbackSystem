package com.emailService.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	
	@ExceptionHandler(ConfigMailNotFoundException.class)
	public ResponseEntity<ErrorDetailsForException> handleConfigMailNotFoundException(ConfigMailNotFoundException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("CONFIG_MAIL_NOT_FOUND");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MailTemplateNotFoundexception.class)
	public ResponseEntity<ErrorDetailsForException> handleMailTemplateNotFoundexception(MailTemplateNotFoundexception exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("MAIL_TEMPLATE_NOT_FOUND");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MailAdminNotFoundException.class)
	public ResponseEntity<ErrorDetailsForException> handleMailAdminNotFoundException(MailAdminNotFoundException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("MAIL_ADMIN_NOT_FOUND");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.NOT_FOUND);
	}
		
	@ExceptionHandler(RequestedHeaderNotFoundException.class)
	public ResponseEntity<ErrorDetailsForException> handleRequestedHeaderNotFoundException(RequestedHeaderNotFoundException exception,WebRequest webRequest)
	{
		ErrorDetailsForException errorDetailsForException =new ErrorDetailsForException();
		errorDetailsForException.setErrorCode("REQUESTED_HEADER_NOT_FOUND");
		errorDetailsForException.setMessage(exception.getMessage());
		errorDetailsForException.setPath(webRequest.getDescription(false));
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.BAD_REQUEST);
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
