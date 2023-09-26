package com.userService.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.userService.config.CustomUserDetailsService;
import com.userService.dto.ForgetPasswordResponseDto;
import com.userService.dto.LoginResponseDto;
import com.userService.dto.ResponseDto;
import com.userService.dto.SendMailRequestDto;
import com.userService.dto.UserLoginRequestDto;
import com.userService.dto.UserRegisterRequestDto;
import com.userService.exception.FailToRegisterException;
import com.userService.exception.UserNotFoundException;
import com.userService.jwt.JwtHelper;
import com.userService.mapper.AutoUserMapper;
import com.userService.model.JwtToken;
import com.userService.model.Users;
import com.userService.repository.JwtTokenRepository;
import com.userService.repository.UserManagerRepository;
import com.userService.service.IdManagerService;
import com.userService.service.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserAuthService{

	@Autowired
	UserManagerRepository usersRepository;
	
	@Autowired
	IdManagerService idManagerService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenRepository jwtTokenRepository;
	
	@Autowired
	JwtHelper jwtHelper;

	@Autowired
	WebClient webclient;
	
	private Logger logger=LoggerFactory.getLogger(UserAuthServiceImpl.class);
	
	@Override
	public ResponseDto register(UserRegisterRequestDto userRegisterDto) throws FailToRegisterException 
	{
		int id=idManagerService.generateSequence(Users.SEQUENCE_NAME);
		Users user= AutoUserMapper.MAPPER.mapToUsers(userRegisterDto);
		user.setUserId((long)id);
		user.setUserRole("ROLE_"+user.getUserRole());
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		Optional<Users> savedUser=Optional.of(usersRepository.save(user));
		if(savedUser.isEmpty())
		{
			throw new FailToRegisterException("Fail to register user !!");
		}
		else
		{
			return new ResponseDto(HttpStatus.OK.value(),"User register successfully...");
		}
	}

	@Override
	public LoginResponseDto Login(UserLoginRequestDto userLoginDto) 
	{
		logger.info(userLoginDto.toString());
		UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userLoginDto.getUserEmailId(), userLoginDto.getUserPassword());
		try
		{
			authenticationManager.authenticate(authentication);
			UserDetails UserDetails=customUserDetailsService.loadUserByUsername(userLoginDto.getUserEmailId());
			logger.info(UserDetails.getAuthorities().toString());
			String token =this.jwtHelper.generateToken(UserDetails);
			jwtTokenRepository.save(new JwtToken(token,false,LocalDateTime.now()));
			logger.info("login successfully !!");
			LoginResponseDto loginResponseDto=new LoginResponseDto();
			loginResponseDto.setJwtToken(token);
			loginResponseDto.setResponseCode(HttpStatus.OK.value());
			loginResponseDto.setResponseMessage("Login Successfully...");
			return loginResponseDto;
		}
		catch(BadCredentialsException e)
		{
			throw new BadCredentialsException("Invaild username or password...");
		}
	}

	@Override
	public ForgetPasswordResponseDto forgetPassword(String userEmailId) {
		// TODO Auto-generated method stub
		SendMailRequestDto sendMailRequestDto=new SendMailRequestDto();
		sendMailRequestDto.setMailFor("Forget_Password");
		sendMailRequestDto.setMailTo(userEmailId);
		ForgetPasswordResponseDto response=webclient.post()
				.uri("http://localhost:8082/emailManager/send-mail")
				.header("My_SECRET", "zxCvbNm<>")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(sendMailRequestDto)
				.retrieve()
				.bodyToMono(ForgetPasswordResponseDto.class)
				.block();
		logger.info(response.toString());
		Optional<Users> savedUserFromDB=usersRepository.findByUserEmailId(userEmailId);
		logger.info(savedUserFromDB.get().toString()+" fetch from db");
		if(savedUserFromDB.isEmpty())
		{
			throw new UserNotFoundException("User not found from db !! ");
		}
		else
		{
			savedUserFromDB.get().setUserPassword(passwordEncoder.encode(response.getNewPassword()));
			logger.info(savedUserFromDB.get().toString()+" updated data ");
			usersRepository.save(savedUserFromDB.get());
			logger.info("forget password successfully !!!");
			return response;
		}
	}
}
