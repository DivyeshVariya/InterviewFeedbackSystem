package com.emailService.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emailService.dto.AllMailTemplateResponseDto;
import com.emailService.dto.MailTemplateRequestDto;
import com.emailService.dto.NewMailTemplateRequestDto;
import com.emailService.dto.ResponseDto;
import com.emailService.exception.MailTemplateNotFoundexception;
import com.emailService.mapper.AutoMailTemplateMapper;
import com.emailService.model.MailTemplate;
import com.emailService.repository.MailTemplateRepository;
import com.emailService.service.MailTemplateManagerService;

@Service
public class MailTemplateManagerServiceImpl implements MailTemplateManagerService{

	@Autowired
	MailTemplateRepository mailTemplateRepository;
	
	private Logger logger= LoggerFactory.getLogger(MailTemplateManagerServiceImpl.class); 
	
	@Override
	public ResponseDto newMailTemplate(NewMailTemplateRequestDto newMailTemplateRequestDto) {
		// TODO Auto-generated method stub
		MailTemplate newMailTemplate=AutoMailTemplateMapper.MAPPER.mapToMailTemplate(newMailTemplateRequestDto);
		newMailTemplate.setId(UUID.randomUUID().toString());
		mailTemplateRepository.save(newMailTemplate);
		
		logger.info("New mail template added into DB");
		
		ResponseDto responseDto=new ResponseDto();
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setResponseMessage("New mail template added successfully !!!");
		return responseDto;
	}

	@Override
	public AllMailTemplateResponseDto getAllMailTemplate() {
		// TODO Auto-generated method stub
		
		logger.info("All mail templates fetched from DB...");
		
		AllMailTemplateResponseDto allMailTemplateResponseDto=new AllMailTemplateResponseDto();
		allMailTemplateResponseDto.setResponseCode(HttpStatus.OK.value());
		allMailTemplateResponseDto.setResponseMessage("All mail templates fetched successfully !!!");
		allMailTemplateResponseDto.setListOfMailTemplate(mailTemplateRepository.findAll());
		return allMailTemplateResponseDto;
	}

	@Override
	public ResponseDto updateMailTemplate(MailTemplateRequestDto mailTemplateRequestDto) {
		// TODO Auto-generated method stub
		MailTemplate updatedMailTemplate=AutoMailTemplateMapper.MAPPER.mapToMailTemplate(mailTemplateRequestDto);
		Optional<MailTemplate> mailTemplateFromDB=mailTemplateRepository.findById(updatedMailTemplate.getId());
		if(mailTemplateFromDB.isEmpty())
		{
			throw new MailTemplateNotFoundexception("Invalid Id ! Requested resource id data not found !!!");
		}
		else
		{
			MailTemplate updatedDocument =mailTemplateFromDB.get();
			updatedDocument.setMailBody(updatedMailTemplate.getMailBody());
			updatedDocument.setMailSubject(updatedMailTemplate.getMailSubject());
			updatedDocument.setTemplateFor(updatedMailTemplate.getTemplateFor());
			logger.info("Mail template updated successfully!!!");
			mailTemplateRepository.save(updatedDocument);
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setResponseMessage("Mail template updated successfully!!!");
			return responseDto;
		}
	}

	@Override
	public ResponseDto deleteMailTemplate(String id) {
		// TODO Auto-generated method stub
		
		Optional<MailTemplate> savedMailTemplate=mailTemplateRepository.findById(id);
		if(savedMailTemplate.isEmpty())
		{
			throw new MailTemplateNotFoundexception("Invalid Id ! Requested resource id data not found !!!");
		}
		else
		{
		logger.info("Mail template deleted successfully!!!");
		mailTemplateRepository.deleteById(id);
		ResponseDto responseDto=new ResponseDto();
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setResponseMessage("Mail template deleted successfully!!!");
		return responseDto;
		}
	}

}
