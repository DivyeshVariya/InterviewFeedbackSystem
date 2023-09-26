package com.userService.jwt;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import com.userService.exception.TokenNotFoundException;
import com.userService.exception.InvalidTokenException;
import com.userService.exception.TokenNotFoundDBException;
import com.userService.model.JwtToken;
import com.userService.repository.JwtTokenRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Service
public class JwtLogoutHandler implements LogoutHandler{

	private Logger logger = LoggerFactory.getLogger(LogoutHandler.class);
	
	@Autowired
	JwtHelper jwtHelper;
	
	@Autowired
	JwtTokenRepository jwtTokenRepository;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.info(request.getRequestURI());
		// Authorization

		String requestHeader = request.getHeader("Authorization");
		// Bearer
		logger.info(" Header :  {}", requestHeader);
		String username = null;
		String token = null;
		String message=null;
		String errorCode=null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			// looking good
			token = requestHeader.substring(7);
			try {

				username = this.jwtHelper.getUsernameFromToken(token);
				Optional<JwtToken> savedToken=jwtTokenRepository.findById(token);
				if(savedToken.isEmpty())
				{
					try {
						message="TokenNotFoundDB from JwtLogoutHandler : Given token not found in DB...";
						errorCode="TOKEN_NOTFOUND_DB";
						logger.error(message);
						response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
						throw new TokenNotFoundDBException("Given token not found in DB...");
					} catch (Exception e1) {
						e1.printStackTrace();
						logger.error(e1.getClass()+" from JwtLogoutHandler : "+e1.getMessage());
					}
				}
				else
				{
				if(!savedToken.get().isExpaired())
				{
					savedToken.get().setExpaired(true);
					jwtTokenRepository.save(savedToken.get());
					response.setStatus(HttpStatus.OK.value());
					try {
						response.getOutputStream().print("Logout successfull...");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}
				else
				{	
					try {
						message="InvalidTokenException from JwtLogoutHandler : Some changed has done in token !! Invalid Token";
						errorCode="INVALID_TOKEN";
						logger.error(message);
						response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
						throw new InvalidTokenException("Invalid token...");
					} catch (Exception e1) {
						e1.printStackTrace();
						logger.error(e1.getClass()+" from JwtLogoutHandler : "+e1.getMessage());
					}
				}
				}
			} catch (IllegalArgumentException e) {
				
				try {
					message=e.getClass()+" from JwtLogoutHandler : Illegal Argument while fetching the username !!";
					errorCode="ILLEGAL_ARGUMENT_TOKEN";
					logger.error(message);
					e.printStackTrace();
					response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
					} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass()+" from JwtLogoutHandler : "+e1.getMessage());
				}
				
			} catch (ExpiredJwtException e) {
		
				try {
					message=e.getClass()+" from JwtLogoutHandler : Given jwt token is expired !!";
					errorCode="EXPIRED_JWT_TOKEN";
					logger.error(message);
					e.printStackTrace();
					response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
					} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass()+" from JwtLogoutHandler : "+e1.getMessage());
				}
			
			} catch (MalformedJwtException e) {
				try {
					message=e.getClass()+" from JwtLogoutHandler : Some changed has done in token !! Invalid Token";
					errorCode="MALFORMED_JWT_TOKEN";
					logger.error(message);
					e.printStackTrace();
					response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
					} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass()+" from JwtLogoutHandler : "+e1.getMessage());
				}
			} catch (Exception e) {
				try {
					message=e.getClass()+" from JwtLogoutHandler : "+e.getMessage();
					errorCode="OTHER_EXCEPTION";
					logger.error(message);
					e.printStackTrace();
					response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
					} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass()+" from JwtLogoutHandler : "+e1.getMessage());
				}
			}

		} else {
			try {
				message="TokenNotFoundException from JwtLogoutHandler : Token Not Found!!!";
				errorCode="TOKEN_NOT_FOUND";
				response.sendRedirect("http://localhost:8081/userAuth/resolveException?requestUrl="+request.getRequestURI()+"&message="+message+"&errorCode="+errorCode);
//				throw new TokenNotFoundException("Token not found !!!");
				} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("TokenNotFoundException from JwtLogoutHandler : "+e1.getMessage());
			}
		}
	}

}
