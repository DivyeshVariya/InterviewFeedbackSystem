package com.emailService.dto;

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
public class NewMailTemplateRequestDto {

	@NotEmpty(message = "templateFor attribute should not be null or empty...")
	private String templateFor;
	
	@NotEmpty(message = "mailSubject attribute should not be null or empty...")
	private String mailSubject;
	
	@NotEmpty(message = "mailBody attribute should not be null or empty...")
	private String mailBody;
}
