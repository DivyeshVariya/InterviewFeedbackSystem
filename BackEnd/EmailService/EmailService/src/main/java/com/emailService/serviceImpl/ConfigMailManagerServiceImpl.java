package com.emailService.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.emailService.dto.AllConfigMailResponseDto;
import com.emailService.dto.MailConfigRequestDto;
import com.emailService.dto.NewMailConfigRequestDto;
import com.emailService.dto.ResponseDto;
import com.emailService.exception.ConfigMailNotFoundException;
import com.emailService.mapper.AutoConfigMailMapper;
import com.emailService.model.ConfigMail;
import com.emailService.repository.ConfigMailRepository;
import com.emailService.service.ConfigMailManagerService;
import com.thoughtworks.xstream.converters.basic.UUIDConverter;

@Service
public class ConfigMailManagerServiceImpl implements ConfigMailManagerService {

	@Autowired
	ConfigMailRepository configMailRepository;

	private Logger logger = LoggerFactory.getLogger(ConfigMailManagerServiceImpl.class);

	@Override
	public ResponseDto addNewConfigMail(NewMailConfigRequestDto newMailConfigRequestDto) {
		// TODO Auto-generated method stub
		logger.info(newMailConfigRequestDto.toString());
		ConfigMail newConfigMail = AutoConfigMailMapper.MAPPER.maptoConfigMail(newMailConfigRequestDto);
		newConfigMail.setId(UUID.randomUUID().toString());
		logger.info(newConfigMail.toString());
		configMailRepository.save(newConfigMail);

		logger.info("New mail config added into DB");

		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setMessage("New mail configuration added successfully !!!");
		return responseDto;
	}

	@Override
	public ResponseDto deleteConfigMail(String id) {
		// TODO Auto-generated method stub
		Optional<ConfigMail> savedConfigMail = configMailRepository.findById(id);
		if (savedConfigMail.isEmpty()) {
			throw new ConfigMailNotFoundException("Requested mail configuration not found !!!");
		} else {
			configMailRepository.deleteById(id);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("Record deleted successfully !!!");
			return responseDto;
		}
	}

	@Override
	public AllConfigMailResponseDto getAllConfigMails() {
		// TODO Auto-generated method stub

		AllConfigMailResponseDto allConfigMailResponseDto = new AllConfigMailResponseDto();
		allConfigMailResponseDto.setListOfConfigMail(configMailRepository.findAll());
		allConfigMailResponseDto.setResponseCode(HttpStatus.OK.value());
		allConfigMailResponseDto.setMessage("All mail configuration fetched successfully !!!");
		return allConfigMailResponseDto;
	}

	@Override
	public ResponseDto updateConfigMail(MailConfigRequestDto mailConfigRequestDto) {
		// TODO Auto-generated method stub
		logger.info(mailConfigRequestDto.toString());
		ConfigMail updatedConfigMail = AutoConfigMailMapper.MAPPER.mapToConfigMail(mailConfigRequestDto);
		logger.info(updatedConfigMail.toString());
		Optional<ConfigMail> configMailFromDB = configMailRepository.findById(updatedConfigMail.getId());
		logger.info(configMailFromDB.toString());
		if (configMailFromDB.isEmpty()) {
			throw new ConfigMailNotFoundException("Requested mail configuration not found !!!");
		} else {
			ConfigMail updatedDocument = configMailFromDB.get();
			updatedDocument.setConfigFor(updatedConfigMail.getConfigFor());
			updatedDocument.setEnableSmptStarttls(updatedConfigMail.isEnableSmptStarttls());
			updatedDocument.setMailHost(updatedConfigMail.getMailHost());
			updatedDocument.setMailPort(updatedConfigMail.getMailPort());
			updatedDocument.setMailSmptSocketFactoryPort(updatedConfigMail.getMailSmptSocketFactoryPort());
			updatedDocument.setMailSmtpAuthEnable(updatedConfigMail.isMailSmtpAuthEnable());
			updatedDocument.setMailSmtpSslEnable(updatedConfigMail.isMailSmtpSslEnable());
			logger.info(updatedDocument.toString());
			configMailRepository.save(updatedDocument);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("Mail configuration updated successfully !!!");
			return responseDto;
		}
	}

}
