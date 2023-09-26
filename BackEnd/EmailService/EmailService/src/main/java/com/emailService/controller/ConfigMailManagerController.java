package com.emailService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emailService.dto.AllConfigMailResponseDto;
import com.emailService.dto.MailConfigRequestDto;
import com.emailService.dto.NewMailConfigRequestDto;
import com.emailService.dto.ResponseDto;
import com.emailService.service.ConfigMailManagerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/configMailManager")
public class ConfigMailManagerController {

	@Autowired
	ConfigMailManagerService configMailManagerService;
	
	Logger logger = LoggerFactory.getLogger(ConfigMailManagerController.class);
	
	@PostMapping("/add-new-mail-config")
	public ResponseEntity<ResponseDto> addNewConfigMail(@Valid @RequestBody NewMailConfigRequestDto newMailConfigRequestDto)
	{
		logger.info(newMailConfigRequestDto.toString());
		return new ResponseEntity<>(configMailManagerService.addNewConfigMail(newMailConfigRequestDto),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-mail-config")
	public ResponseEntity<AllConfigMailResponseDto> getAllConfigMail()
	{
		return new ResponseEntity<>(configMailManagerService.getAllConfigMails(),HttpStatus.OK);
	}
	
	@PutMapping("/update-mail-config")
	public ResponseEntity<ResponseDto> updateConfigMail(@Valid @RequestBody MailConfigRequestDto mailConfigRequestDto)
	{
		return new ResponseEntity<>(configMailManagerService.updateConfigMail(mailConfigRequestDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-mail-config/{id}")
	public ResponseEntity<ResponseDto> deleteConfigMail(@PathVariable(name = "id",required = true) String id)
	{
		return new ResponseEntity<>(configMailManagerService.deleteConfigMail(id),HttpStatus.OK);
	}
}
