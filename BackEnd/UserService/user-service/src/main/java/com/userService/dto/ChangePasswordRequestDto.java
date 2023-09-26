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
public class ChangePasswordRequestDto {

	@NotEmpty(message = "User Mail I'd should not be null or empty...")
	@Email(message = "Mail should be in proper format...")
	private String userEmailId;
	
	@NotEmpty(message = "Password should not be null or empty...")
	@Size(min = 8, message = "Password should have minimum length is 8... ")
	@Size(max = 16, message = "Password should have maximum length is 16... ")
	private String newPassword;
	
	@NotEmpty(message = "Password should not be null or empty...")
	@Size(min = 8, message = "Password should have minimum length is 8... ")
	@Size(max = 16, message = "Password should have maximum length is 16... ")
	private String confirmedPassword;
}
