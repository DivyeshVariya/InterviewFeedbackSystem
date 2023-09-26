package com.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class UserUpdateProfileRequestDto {

	@NotEmpty(message = "User's Email id should not be null or empty...")
	@Email(message = "Email address should be valid...")
	private String userEmailId;
	
	@NotEmpty(message = "User's full name should not be null or empty...")
	private String userFullName;
	
	@NotEmpty(message = "User's contact number should not be null or empty...")
	@Size(min = 10, message = "Contact number must have 10 digits..")
	@Size(max = 10, message = "Contact number must have 10 digits..")
	private String userContactNo;
	
	@NotEmpty(message = "User's designation should not be null or empty...")
	private String userDesignation;
	
	@NotEmpty(message = "User's department name should not be null or empty...")
	private String userDepartment;
}
