package com.emailService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.emailService.dto.MailConfigRequestDto;
import com.emailService.dto.NewMailConfigRequestDto;
import com.emailService.model.ConfigMail;

@Mapper
public interface AutoConfigMailMapper {

	AutoConfigMailMapper MAPPER = Mappers.getMapper(AutoConfigMailMapper.class);
	
	NewMailConfigRequestDto mapToNewMailConfigRequestDto(ConfigMail configMail);
	
	@Mapping(target = "createdByUser",ignore = true)
	@Mapping(target = "creationDate",ignore = true)
	@Mapping(target = "lastModifiedDate",ignore = true)
	@Mapping(target = "lastModifiedUserId",ignore = true)
	@Mapping(target = "version",ignore = true)
	@Mapping(target = "id",ignore = true)
	ConfigMail maptoConfigMail(NewMailConfigRequestDto newMailConfigRequestDto);
	
	@Mapping(target = "createdByUser",ignore = true)
	@Mapping(target = "creationDate",ignore = true)
	@Mapping(target = "lastModifiedDate",ignore = true)
	@Mapping(target = "lastModifiedUserId",ignore = true)
	@Mapping(target = "version",ignore = true)
	@Mapping(target = "mailSmtpAuthEnable",ignore = true)
	@Mapping(target = "enableSmptStarttls",ignore = true)
	ConfigMail mapToConfigMail(MailConfigRequestDto mailConfigRequestDto);
}
