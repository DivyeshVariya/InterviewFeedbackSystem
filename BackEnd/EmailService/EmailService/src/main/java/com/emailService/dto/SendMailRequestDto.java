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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMailRequestDto {

	@NotEmpty(message = "Mail I'd should not be null or empty...")
	@Email(message = "Mail should be in proper format...")
	private String mailTo;
	
	@NotEmpty(message = "mailFor attribute should not be null or empty...")
	private String mailFor;
}
