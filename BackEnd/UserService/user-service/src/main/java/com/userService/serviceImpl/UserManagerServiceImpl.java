package com.userService.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userService.dto.ChangePasswordRequestDto;
import com.userService.dto.ResponseDto;
import com.userService.dto.UserDetailsResponseDto;
import com.userService.dto.UserUpdateProfileRequestDto;
import com.userService.exception.PasswordMisMatchException;
import com.userService.exception.UserDetailsNotFoundException;
import com.userService.exception.UserNotFoundException;
import com.userService.mapper.AutoUserMapper;
import com.userService.model.Users;
import com.userService.repository.UserManagerRepository;
import com.userService.service.UserManagerService;

@Service
@RefreshScope
public class UserManagerServiceImpl implements UserManagerService{

	@Autowired
	UserManagerRepository userManagerRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private Logger logger =LoggerFactory.getLogger(UserManagerServiceImpl.class);
	
	@Override
	public UserDetailsResponseDto getUserdetails(String userEmailId) {
		// TODO Auto-generated method stub
		Optional<Users> userInfoFromDB=userManagerRepository.findByUserEmailId(userEmailId);
		if(userInfoFromDB.isEmpty())
		{
			throw new UserDetailsNotFoundException("User details not found...!!");
		}
		logger.info("From GetUserdetails method :"+userEmailId);
		return AutoUserMapper.MAPPER.mapToUserDetailsResponseDeto(userInfoFromDB.get());
	}

	@Override
	public ResponseDto changePassword(ChangePasswordRequestDto changePasswordDto) {
		// TODO Auto-generated method stub
		Optional<Users> userInfoFromDB=userManagerRepository.findByUserEmailId(changePasswordDto.getUserEmailId());
		if(userInfoFromDB.isEmpty())
		{
			throw new UserDetailsNotFoundException("User details not found...!!");
		}
		logger.info("From GetUserdetails method :"+userInfoFromDB);
		ResponseDto responseDto=new ResponseDto();
		if(changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmedPassword()))
		{
			userInfoFromDB.get().setUserPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
			userManagerRepository.save(userInfoFromDB.get());
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("Password Changed successfully...");
			return responseDto;
		}
		else
		{
			throw new PasswordMisMatchException("New password and Confirmed password must be same !!!");
		}
	}

	@Override
	public ResponseDto updateProfile(UserUpdateProfileRequestDto userUpdateProfileRequestDto) {
		// TODO Auto-generated method stub
		logger.info(userUpdateProfileRequestDto.toString());
		Optional<Users> savedUserFromDB=userManagerRepository.findByUserEmailId(userUpdateProfileRequestDto.getUserEmailId());
		logger.info(savedUserFromDB.toString());
		if(savedUserFromDB.isEmpty())
		{
			throw new UserNotFoundException("User not found !!!");
		}
		else
		{
			Users updatedDocument=savedUserFromDB.get();
			logger.info(updatedDocument.toString());
			updatedDocument.setUserEmailId(userUpdateProfileRequestDto.getUserEmailId());
			updatedDocument.setUserDesignation(userUpdateProfileRequestDto.getUserDesignation());
			updatedDocument.setUserDepartment(userUpdateProfileRequestDto.getUserDepartment());
			updatedDocument.setUserFullName(userUpdateProfileRequestDto.getUserFullName());
			updatedDocument.setUserContactNo(userUpdateProfileRequestDto.getUserContactNo());
			logger.info(updatedDocument.toString());
			userManagerRepository.save(updatedDocument);
			logger.info("user updated successfully !!!");
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("User updated successfully !!!");
			return responseDto;
		}
	
	}

	@Override
	public ResponseDto deleteUser(String userEmailId) {
		// TODO Auto-generated method stub
		Optional<Users> savedUserFromDB=userManagerRepository.findByUserEmailId(userEmailId);
		if(savedUserFromDB.isEmpty())
		{
			throw new UserNotFoundException("User not found !!!");
		}
		else
		{
			userManagerRepository.deleteByUserEmailId(userEmailId);
			logger.info("user deleted successfully !!!");
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("User deleted successfully !!!");
			return responseDto;
		}
	}

}
