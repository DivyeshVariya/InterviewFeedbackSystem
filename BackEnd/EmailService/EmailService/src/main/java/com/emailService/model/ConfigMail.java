package com.emailService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document("ConfigMail")
public class ConfigMail extends BaseEntity{

	@Id
	private String id;
	private String configFor;
	private String mailHost;
	private int mailPort;
	private int mailSmptSocketFactoryPort;
	private boolean mailSmtpSslEnable;
	private boolean mailSmtpAuthEnable;
	private boolean enableSmptStarttls;
}
