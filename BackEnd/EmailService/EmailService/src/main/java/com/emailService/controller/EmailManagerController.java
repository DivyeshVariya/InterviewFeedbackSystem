package com.emailService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emailService.dto.ResponseDto;
import com.emailService.dto.SendMailRequestDto;
import com.emailService.service.EmailManagerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/emailManager")
public class EmailManagerController implements ErrorController{

	@Autowired
	EmailManagerService emailManagerService;
	
	@PostMapping("/send-mail")
	public ResponseEntity<ResponseDto> sendMail(@Valid @RequestBody SendMailRequestDto sendMailRequestDto) throws Exception
	{
		return new ResponseEntity<>(emailManagerService.sendMail(sendMailRequestDto),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/error")
    public String error() {
		return "<center><h1 style=\"color:red;\">Unauthorized Access!</h1></center>";
    }
}
