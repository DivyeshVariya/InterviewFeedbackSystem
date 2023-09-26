package com.emailService.service;

import com.emailService.dto.AllConfigMailResponseDto;
import com.emailService.dto.MailConfigRequestDto;
import com.emailService.dto.NewMailConfigRequestDto;
import com.emailService.dto.ResponseDto;

public interface ConfigMailManagerService {

	ResponseDto addNewConfigMail(NewMailConfigRequestDto newMailConfigRequestDto);

	ResponseDto deleteConfigMail(String id);

	AllConfigMailResponseDto getAllConfigMails();

	ResponseDto updateConfigMail(MailConfigRequestDto mailConfigRequestDto);

}
