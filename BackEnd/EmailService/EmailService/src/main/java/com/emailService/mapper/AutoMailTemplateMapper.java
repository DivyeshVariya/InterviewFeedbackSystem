package com.emailService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.emailService.dto.MailTemplateRequestDto;
import com.emailService.dto.NewMailTemplateRequestDto;
import com.emailService.model.MailTemplate;

@Mapper
public interface AutoMailTemplateMapper {

	AutoMailTemplateMapper MAPPER=Mappers.getMapper(AutoMailTemplateMapper.class);
	
	@Mapping(source = "mailBody",target = "mailBody")
	@Mapping(source = "mailSubject",target = "mailSubject")
	@Mapping(source = "templateFor",target = "templateFor")
	MailTemplate mapToMailTemplate(NewMailTemplateRequestDto newMailTemplateRequestDto);
	
	MailTemplate mapToMailTemplate(MailTemplateRequestDto mailTemplateRequestDto);
	
	NewMailTemplateRequestDto mapToNewMailTemplateRequestDto(MailTemplate mailTemplate);
}
