package com.userService.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userService.dto.ForgetPasswordResponseDto;
import com.userService.dto.LoginResponseDto;
import com.userService.dto.ResponseDto;
import com.userService.dto.UserLoginRequestDto;
import com.userService.dto.UserRegisterRequestDto;
import com.userService.exception.ErrorDetailsForException;
import com.userService.exception.FailToRegisterException;
import com.userService.service.UserAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/userAuth")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserAuthController {
	
	private Logger logger=LoggerFactory.getLogger(UserAuthController.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	@PostMapping("/register-user")
	public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto userRegisterDto) throws FailToRegisterException
	{
		return new ResponseEntity<>(userAuthService.register(userRegisterDto),HttpStatus.OK);
	}
	
	@PostMapping("/user-login")
	public ResponseEntity<LoginResponseDto> userLogin(@Valid @RequestBody UserLoginRequestDto userLoginDto)
	{
		return new ResponseEntity<>(userAuthService.Login(userLoginDto),HttpStatus.OK);
	}
	
	@PostMapping("/forget-password")
	public ResponseEntity<ForgetPasswordResponseDto> forgetPassword(@RequestParam String userEmailId)
	{
		return new ResponseEntity<>(userAuthService.forgetPassword(userEmailId),HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ResponseDto> userLogout()
	{
		ResponseDto responseDto=new ResponseDto();
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setMessage("User logout successfully....");
		return new ResponseEntity<>(responseDto,HttpStatus.OK);
	}
	
	@GetMapping("/resolveException")
	public ResponseEntity<ErrorDetailsForException> userLogout1(@RequestParam("requestUrl") String requestUrl,@RequestParam("message") String message,@RequestParam("errorCode") String errorCode)
	{
		ErrorDetailsForException errorDetailsForException=new ErrorDetailsForException();
		errorDetailsForException.setErrorCode(errorCode);
		errorDetailsForException.setMessage(message);
		errorDetailsForException.setPath(requestUrl);
		errorDetailsForException.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetailsForException,HttpStatus.BAD_REQUEST);
	}

}
