package com.emailService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.emailService.dto.MailAdminRequestDto;
import com.emailService.dto.NewMailAdminRequestDto;
import com.emailService.model.MailAdmin;

@Mapper
public interface AutoMailAdminMapper {

	AutoMailAdminMapper MAPPER=Mappers.getMapper(AutoMailAdminMapper.class);
	
	@Mapping(source = "mailPassword",target = "mailPassword")
	@Mapping(source = "mailFrom",target = "mailFrom")
	@Mapping(source = "mailAdminFor",target = "mailAdminFor")
	MailAdmin maptoMailAdmin(NewMailAdminRequestDto newMailAdminDto);
	
	NewMailAdminRequestDto mapToNewMailAdminRequestDto(MailAdmin mailAdmin);
	
	MailAdmin mapToMailAdmin(MailAdminRequestDto mailAdminRequestDto);
}
