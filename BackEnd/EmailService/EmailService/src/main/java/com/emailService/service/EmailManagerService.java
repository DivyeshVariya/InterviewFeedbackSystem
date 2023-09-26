package com.emailService.service;

import com.emailService.dto.ResponseDto;
import com.emailService.dto.SendMailRequestDto;

public interface EmailManagerService {

	ResponseDto sendMail(SendMailRequestDto sendMailRequestDto) throws Exception;

	
}
