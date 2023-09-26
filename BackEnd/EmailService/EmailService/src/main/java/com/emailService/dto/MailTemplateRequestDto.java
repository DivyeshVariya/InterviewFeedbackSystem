package com.emailService.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailTemplateRequestDto {

	@NotEmpty(message = "Id attribute should not be null or empty...")
	private String id;
	
	@NotEmpty(message = "templateFor attribute should not be null or empty...")
	private String templateFor;
	
	@NotEmpty(message = "mailSubject attribute should not be null or empty...")
	private String mailSubject;
	
	@NotEmpty(message = "mailBody attribute should not be null or empty...")
	private String mailBody;
}
