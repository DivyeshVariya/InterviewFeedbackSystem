package com.userService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users extends BaseEntity{
	
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private Long userId;
	private String userRole;
	private String userEmailId;
	private String userFullName;
	private String userContactNo;
	private String userDesignation;
	private String userDepartment;
	private String userPassword;
	
}
