package com.emailService.service;

import com.emailService.dto.LoginRequestDto;
import com.emailService.dto.LoginResponseDto;

public interface AuthManagerService {

	LoginResponseDto userLogin(LoginRequestDto loginRequestDto);

}
