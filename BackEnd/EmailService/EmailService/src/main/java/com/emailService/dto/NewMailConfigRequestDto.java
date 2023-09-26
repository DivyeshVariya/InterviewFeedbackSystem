package com.emailService.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class NewMailConfigRequestDto {
	
	@NotEmpty(message = "configFor attribute should not be null or empty...")
	private String configFor;
	
	@NotEmpty(message = "mailHost attribute should not be null or empty...")
	private String mailHost;
	
	@Min(value  = 465,message = "Default port must be 465 ...")
	@Max(value  = 465,message = "Default port must be 465 ...")
	private int mailPort;
	
	@Min(value  = 465,message = "Default port must be 465 ...")
	@Max(value  = 465,message = "Default port must be 465 ...")
	private int mailSmptSocketFactoryPort;
	
	@NotNull(message = "mailSmtpSslEnable attribute should not be null or empty...")
	@AssertTrue(message = "mailSmtpSslEnable attribute must be true....")
	private boolean mailSmtpSslEnable;
	
	@NotNull(message = "mailSmtpAuthEnable attribute should not be null or empty...")
	@AssertTrue(message = "mailSmtpAuthEnable attribute must be true....")
	private boolean mailSmtpAuthEnable;
	
	@NotNull(message = "enableSmptStarttls attribute should not be null or empty...")
	@AssertTrue(message = "enableSmptStarttls attribute must be true....")
	private boolean enableSmptStarttls;
}
