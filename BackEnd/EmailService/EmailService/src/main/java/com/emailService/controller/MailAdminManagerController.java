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

import com.emailService.dto.AllMailAdminResponseDto;
import com.emailService.dto.MailAdminRequestDto;
import com.emailService.dto.NewMailAdminRequestDto;
import com.emailService.dto.ResponseDto;
import com.emailService.service.MailAdminManagerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/mailAdminManager")
public class MailAdminManagerController {

	@Autowired
	MailAdminManagerService mailAdminManagerService;
	
	@PostMapping("/new-mail-admin")
	public ResponseEntity<ResponseDto> newMailAdmin(@Valid @RequestBody NewMailAdminRequestDto newMailAdminRequestDto)
	{
		return new ResponseEntity<>(mailAdminManagerService.newMailAdmin(newMailAdminRequestDto),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-mail-admin")
	public ResponseEntity<AllMailAdminResponseDto> getAllMailAdmin()
	{
		return new ResponseEntity<>(mailAdminManagerService.getAllMailAdmin(),HttpStatus.OK);
	}
	
	@PutMapping("/update-mail-admin")
	public ResponseEntity<ResponseDto> updateMailAdmin(@Valid @RequestBody MailAdminRequestDto mailAdminRequestDto)
	{
		return new ResponseEntity<>(mailAdminManagerService.updateMailAdmin(mailAdminRequestDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-mail-admin/{email}")
	public ResponseEntity<ResponseDto> deleteMailAdmin(@PathVariable(name = "email",required =true) String email)
	{
		return new ResponseEntity<>(mailAdminManagerService.deleteMailAdmin(email),HttpStatus.OK);
	}
}
