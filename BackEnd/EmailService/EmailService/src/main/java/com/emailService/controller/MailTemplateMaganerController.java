package com.emailService.controller;

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

import com.emailService.dto.AllMailTemplateResponseDto;
import com.emailService.dto.MailTemplateRequestDto;
import com.emailService.dto.NewMailTemplateRequestDto;
import com.emailService.dto.ResponseDto;
import com.emailService.service.MailTemplateManagerService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/mailTemplateManager")
public class MailTemplateMaganerController {

	@Autowired
	MailTemplateManagerService mailTemplateService;
	
	@PostMapping("/new-mail-template")
	public ResponseEntity<ResponseDto> newMailTemplate(@Valid @RequestBody NewMailTemplateRequestDto newMailTemplateRequestDto)
	{
		return new ResponseEntity<>(mailTemplateService.newMailTemplate(newMailTemplateRequestDto),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-mail-template")
	public ResponseEntity<AllMailTemplateResponseDto> getAllMailTemplate()
	{
		return new ResponseEntity<>(mailTemplateService.getAllMailTemplate(),HttpStatus.OK);
	}
	
	@PutMapping("/update-mail-template")
	public ResponseEntity<ResponseDto> updateMailTemplate(@Valid @RequestBody MailTemplateRequestDto mailTemplateRequestDto)
	{
		return new ResponseEntity<>(mailTemplateService.updateMailTemplate(mailTemplateRequestDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-mail-template/{id}")
	public ResponseEntity<ResponseDto> deleteMailTemplate(@PathVariable("id") String id)
	{
		return new ResponseEntity<>(mailTemplateService.deleteMailTemplate(id),HttpStatus.OK);
	}
}
