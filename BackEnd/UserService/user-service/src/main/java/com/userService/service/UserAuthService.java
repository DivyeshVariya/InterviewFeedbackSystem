package com.userService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.userService.dto.ForgetPasswordResponseDto;
import com.userService.dto.LoginResponseDto;
import com.userService.dto.ResponseDto;
import com.userService.dto.UserLoginRequestDto;
import com.userService.dto.UserRegisterRequestDto;
import com.userService.exception.FailToRegisterException;

public interface UserAuthService {

	public ResponseDto register(UserRegisterRequestDto userRegisterDto) throws FailToRegisterException;
	
	public LoginResponseDto Login(UserLoginRequestDto userLoginDto);

	public ForgetPasswordResponseDto forgetPassword(String userEmailId);
}
