package com.emailService.dto;

import java.util.List;

import com.emailService.model.ConfigMail;

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
public class AllConfigMailResponseDto extends ResponseDto{

	private List<ConfigMail> listOfConfigMail;
}
