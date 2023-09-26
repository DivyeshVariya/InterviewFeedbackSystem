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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("EmailAdmins")
public class MailAdmin extends BaseEntity{

	@Id
	private String id;
	private String mailAdminFor;
	private String mailFrom;
	private String mailPassword;
}
