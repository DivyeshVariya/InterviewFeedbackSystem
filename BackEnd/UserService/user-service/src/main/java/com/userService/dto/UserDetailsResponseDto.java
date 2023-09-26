package com.userService.dto;

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
public class UserDetailsResponseDto {

	private String userRole;
	private String userEmailId;
	private String userFullName;
	private String userContactNo;
	private String userDesignation;
	private String userDepartment;

}
