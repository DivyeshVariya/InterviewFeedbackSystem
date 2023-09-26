package com.emailService.service;

import com.emailService.dto.AllMailTemplateResponseDto;
import com.emailService.dto.MailTemplateRequestDto;
import com.emailService.dto.NewMailTemplateRequestDto;
import com.emailService.dto.ResponseDto;

public interface MailTemplateManagerService {

	ResponseDto newMailTemplate(NewMailTemplateRequestDto newMailTemplateRequestDto);

	AllMailTemplateResponseDto getAllMailTemplate();

	ResponseDto updateMailTemplate(MailTemplateRequestDto mailTemplateRequestDto);

	ResponseDto deleteMailTemplate(String id);

}
