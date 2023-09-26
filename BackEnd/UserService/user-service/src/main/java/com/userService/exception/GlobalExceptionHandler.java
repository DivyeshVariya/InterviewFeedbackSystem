package com.userService.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetailsForException> handleBadCredentialException(BadCredentialsException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("INVALID_CREDENTIALS");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<ErrorDetailsForException> handleInvalidHeaderException(TokenNotFoundException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("INVALID_HEADER");
		System.out.println(errorDetails);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDetailsForException> handleIllegalArgumentException(IllegalArgumentException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("ILLEGAL_ARGUMENT");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorDetailsForException> handleExpiredJwtException(ExpiredJwtException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("EXPIRED_JWT_TOKEN");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ErrorDetailsForException> handleMalformedJwtException(MalformedJwtException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("MALFORMED_JWT_TOKEN");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorDetailsForException> handleValidationException(ValidationException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("VALIDATION_FAIL");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserDetailsNotFoundException.class)
	public ResponseEntity<ErrorDetailsForException> handleUserDetailsNotFoundException(UserDetailsNotFoundException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("USER_DETAILS_NOT_FOUND");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
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
	
	@ExceptionHandler(TokenNotFoundDBException.class)
	public ResponseEntity<ErrorDetailsForException> handleTokenNotFoundDBException(TokenNotFoundDBException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("TOKEN_NOTFOUND_DB");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorDetailsForException> handleInvalidTokenException(InvalidTokenException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("INVALID_TOKEN");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PasswordMisMatchException.class)
	public ResponseEntity<ErrorDetailsForException> handlePasswordMisMatchException(PasswordMisMatchException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("PASSWORD_MISMATCH");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FailToRegisterException.class)
	public ResponseEntity<ErrorDetailsForException> handleInvalidUserRoleException(FailToRegisterException exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<ErrorDetailsForException> handleGlobalException(Exception exception,
			WebRequest webRequest)
	{
		ErrorDetailsForException errorDetails= new ErrorDetailsForException();
		errorDetails.setTimestamp(LocalDateTime.now());
		errorDetails.setMessage(exception.getMessage());
		errorDetails.setPath(webRequest.getDescription(false));
		errorDetails.setErrorCode("INTERNAL_SERVER_ERROR");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
