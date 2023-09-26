package com.userService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userService.dto.ChangePasswordRequestDto;
import com.userService.dto.ForgetPasswordResponseDto;
import com.userService.dto.ResponseDto;
import com.userService.dto.UserDetailsResponseDto;
import com.userService.dto.UserUpdateProfileRequestDto;
import com.userService.service.UserManagerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/userManager")
public class UserManagerController {
	
	private Logger logger=LoggerFactory.getLogger(UserManagerController.class);
	
	@Autowired
	UserManagerService userManagerService;
	
	@GetMapping("/get-user-details")
	@PreAuthorize("hasAnyRole('HR','INTERVIEWER')")
	public ResponseEntity<UserDetailsResponseDto> getUserDetails(@RequestParam String userEmailId)
	{
		return new ResponseEntity<>(userManagerService.getUserdetails(userEmailId),HttpStatus.OK);
	}
	
	@PutMapping("/change-password")
	@PreAuthorize("hasAnyRole('HR','INTERVIEWER')")
	public ResponseEntity<ResponseDto> changePassword(@Valid @RequestBody ChangePasswordRequestDto userEmailId)
	{
		return new ResponseEntity<>(userManagerService.changePassword(userEmailId),HttpStatus.OK);
	}
	
	@PutMapping("/update-profile")
	@PreAuthorize("hasAnyRole('HR','INTERVIEWER')")
	public ResponseEntity<ResponseDto> updateProfile(@Valid @RequestBody UserUpdateProfileRequestDto userUpdateProfileRequestDto)
	{
		return new ResponseEntity<>(userManagerService.updateProfile(userUpdateProfileRequestDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-user")
	@PreAuthorize("hasAnyRole('HR')")
	public ResponseEntity<ResponseDto> deleteUser(@RequestParam String userEmailId)
	{
		return new ResponseEntity<>(userManagerService.deleteUser(userEmailId),HttpStatus.OK);
	}
}
