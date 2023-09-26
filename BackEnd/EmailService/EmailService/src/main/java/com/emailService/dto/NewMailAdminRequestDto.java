package com.emailService.dto;

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
public class NewMailAdminRequestDto {

	@NotEmpty(message = "Mail I'd should not be null or empty...")
	@Email(message = "Mail should be in proper format...")
	private String mailFrom;
	
	@NotEmpty(message = "Mail password should not be null or empty...")
	private String mailPassword;
	
	@NotEmpty(message = "Us should not be null or empty...")
	private String mailAdminFor;
}
