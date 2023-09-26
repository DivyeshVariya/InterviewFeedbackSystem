package com.userService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.userService.dto.UserDetailsResponseDto;
import com.userService.dto.UserRegisterRequestDto;
import com.userService.model.Users;

@Mapper
public interface AutoUserMapper {

	AutoUserMapper MAPPER= Mappers.getMapper(AutoUserMapper.class);
	
	UserDetailsResponseDto mapToUserDetailsResponseDeto(Users user);
	
	@Mapping(target = "createdByUser",ignore = true)
	@Mapping(target = "creationDate",ignore = true)
	@Mapping(target = "lastModifiedDate",ignore = true)
	@Mapping(target = "lastModifiedUserId",ignore = true)
	@Mapping(target = "version",ignore = true)
	@Mapping(target = "userId",ignore = true)
	@Mapping(target = "userPassword",ignore = true)
	Users mapToUsers(UserDetailsResponseDto userDetailsResponseDto);

	@Mapping(target = "createdByUser",ignore = true)
	@Mapping(target = "creationDate",ignore = true)
	@Mapping(target = "lastModifiedDate",ignore = true)
	@Mapping(target = "lastModifiedUserId",ignore = true)
	@Mapping(target = "version",ignore = true)
	@Mapping(target = "userId",ignore = true)
	Users mapToUsers(UserRegisterRequestDto userRegisterRequestDto);
	
	UserRegisterRequestDto mapToUserRegisterRequestDto(Users users);
}
