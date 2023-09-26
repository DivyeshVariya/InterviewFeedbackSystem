package com.userService.service;

import com.userService.dto.ChangePasswordRequestDto;
import com.userService.dto.ResponseDto;
import com.userService.dto.UserDetailsResponseDto;
import com.userService.dto.UserUpdateProfileRequestDto;

public interface UserManagerService {

	public UserDetailsResponseDto getUserdetails(String userEmailId);

	public ResponseDto changePassword(ChangePasswordRequestDto changePasswordDto);

	public ResponseDto updateProfile(UserUpdateProfileRequestDto userUpdateProfileRequestDto);

	public ResponseDto deleteUser(String userEmailId);
}
