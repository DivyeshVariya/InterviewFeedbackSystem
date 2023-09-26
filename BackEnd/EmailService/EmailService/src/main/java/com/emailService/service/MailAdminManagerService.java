package com.emailService.service;

import com.emailService.dto.AllMailAdminResponseDto;
import com.emailService.dto.MailAdminRequestDto;
import com.emailService.dto.NewMailAdminRequestDto;
import com.emailService.dto.ResponseDto;

public interface MailAdminManagerService {

	ResponseDto newMailAdmin(NewMailAdminRequestDto newMailAdminRequestDto);

	AllMailAdminResponseDto getAllMailAdmin();

	ResponseDto updateMailAdmin(MailAdminRequestDto mailAdminRequestDto);

	ResponseDto deleteMailAdmin(String email);

}
