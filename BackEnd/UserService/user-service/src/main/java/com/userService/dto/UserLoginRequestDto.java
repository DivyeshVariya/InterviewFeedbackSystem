package com.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRequestDto {

	@NotEmpty(message = "User Email id should not be null or empty...")
	@Email(message = "Email Address should be valid...")
	private String userEmailId;
	
	@NotEmpty(message = "User Password should not be null or empty...")
	private String userPassword;
}
