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
@Document("Templates")
public class MailTemplate extends BaseEntity{

	@Id
	private String id;
	private String templateFor;
	private String mailSubject;
	private String mailBody;
}
