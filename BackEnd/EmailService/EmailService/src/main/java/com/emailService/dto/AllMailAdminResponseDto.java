package com.emailService.dto;

import java.util.List;

import com.emailService.model.MailAdmin;

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
public class AllMailAdminResponseDto extends ResponseDto{

	private List<MailAdmin> listOfMailAdmin;
}
