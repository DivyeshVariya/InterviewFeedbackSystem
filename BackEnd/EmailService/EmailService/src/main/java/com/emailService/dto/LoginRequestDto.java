package com.emailService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDto {

	@NotEmpty(message = "userName mail attribute should not be null or empty...")
	private String userName;
	
	@NotEmpty(message = "Password should not be null or empty...")
	@Size(min = 8, message = "Password should have minimum length is 8... ")
	@Size(max = 16, message = "Password should have maximum length is 16... ")
	private String userPassword;
}
