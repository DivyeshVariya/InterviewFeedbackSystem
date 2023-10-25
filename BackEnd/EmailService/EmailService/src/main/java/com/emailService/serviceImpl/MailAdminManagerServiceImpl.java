package com.emailService.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emailService.dto.AllMailAdminResponseDto;
import com.emailService.dto.MailAdminRequestDto;
import com.emailService.dto.NewMailAdminRequestDto;
import com.emailService.dto.ResponseDto;
import com.emailService.exception.MailAdminNotFoundException;
import com.emailService.mapper.AutoMailAdminMapper;
import com.emailService.model.MailAdmin;
import com.emailService.repository.MailAdminRepository;
import com.emailService.service.MailAdminManagerService;

@Service
public class MailAdminManagerServiceImpl implements MailAdminManagerService{

	@Autowired
	MailAdminRepository mailAdminRepository;
	
	private Logger logger=LoggerFactory.getLogger(MailAdminManagerService.class);
	
	@Override
	public ResponseDto newMailAdmin(NewMailAdminRequestDto newMailAdminRequestDto) {
		// TODO Auto-generated method stub
		MailAdmin newMailAdmin=AutoMailAdminMapper.MAPPER.maptoMailAdmin(newMailAdminRequestDto);
		newMailAdmin.setId(UUID.randomUUID().toString());
		mailAdminRepository.save(newMailAdmin);
		
		logger.info("New mail admin added into DB");
		
		ResponseDto responseDto=new ResponseDto();
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setMessage("New mail admin added successfully !!!");
		return responseDto;
	}

	@Override
	public AllMailAdminResponseDto getAllMailAdmin() {
		// TODO Auto-generated method stub
		logger.info("All mail admins fetched from DB...");
		
		AllMailAdminResponseDto allMailAdminResponseDto=new AllMailAdminResponseDto();
		allMailAdminResponseDto.setResponseCode(HttpStatus.OK.value());
		allMailAdminResponseDto.setMessage("Mail admins fetched successfully !!!");
		allMailAdminResponseDto.setListOfMailAdmin(mailAdminRepository.findAll());
		return allMailAdminResponseDto;
	}

	@Override
	public ResponseDto updateMailAdmin(MailAdminRequestDto mailAdminRequestDto) {
		// TODO Auto-generated method stub
		MailAdmin updatedMailAdmin=AutoMailAdminMapper.MAPPER.mapToMailAdmin(mailAdminRequestDto);
		Optional<MailAdmin> mailAdminFromDB=mailAdminRepository.findById(updatedMailAdmin.getId());
		if(mailAdminFromDB.isEmpty())
		{
			throw new MailAdminNotFoundException("Invalid Input Value ! Requested mail admin id not found!!!");
		}
		else
		{
			logger.info("Mail admin updated successfully!!!");
			MailAdmin updatedDocument= mailAdminFromDB.get();
			updatedDocument.setMailAdminFor(updatedMailAdmin.getMailAdminFor());
			updatedDocument.setMailFrom(updatedMailAdmin.getMailFrom());
			updatedDocument.setMailPassword(updatedMailAdmin.getMailPassword());
			mailAdminRepository.save(updatedDocument);
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("Mail admin updated successfully!!!");
			return responseDto;
		}
		
	}

	@Override
	public ResponseDto deleteMailAdmin(String email) {
		// TODO Auto-generated method stub
		Optional<MailAdmin> savedMailAdmin=mailAdminRepository.findByMailFrom(email);
		if(savedMailAdmin.isEmpty())
		{
			throw new MailAdminNotFoundException("Invalid Input Value ! Requested mail admin id not found!!!");
		}
		else
		{
			logger.info("Mail admin deleted successfully!!!");
			mailAdminRepository.deleteByMailFrom(email);
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("Mail admin deleted successfully!!!");
			return responseDto;
		}
	}

}
