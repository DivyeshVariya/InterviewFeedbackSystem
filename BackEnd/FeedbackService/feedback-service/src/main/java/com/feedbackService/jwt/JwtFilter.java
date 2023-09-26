package com.feedbackService.jwt;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.AntPathRequestMatcherProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.handler.RequestMatchResult;

import com.feedbackService.audit.DatabaseAuditing;
import com.feedbackService.exception.TokenNotFoundException;
import com.feedbackService.exception.UnauthorizedAccessException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	JwtHelper jwtHelper;
 
	@Autowired
	DatabaseAuditing databaseAuditing;
	
	private final List<String> allowedRequestMatchers = List.of("swagger-ui","v3");
	private Logger logger=LoggerFactory.getLogger(JwtFilter.class);


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		logger.info(request.getRequestURI());
		
		String requestAuthHeader=request.getHeader("Authorization");
		
		logger.info("authHeader : {}",requestAuthHeader);
		
		String token=null;
		String userName=null;
		String message=null;
		
		if(requestAuthHeader!=null && requestAuthHeader.startsWith("Bearer"))
		{
			token=requestAuthHeader.substring(7);
			logger.info("token : {}",token);
			
			try {
				userName=jwtHelper.getUsernameFromToken(token);
				databaseAuditing.setCurrentUser(userName);
				logger.info(userName);
				if(userName!=null)
				{
					try
					{
					jwtHelper.validateToken(token);
					logger.info("from validate method : {}",request.getRequestURI());
					}
					catch(Exception e)
					{
						throw new UnauthorizedAccessException("UnAuthenticatedAccess : Unauthenticated access to application");
					}
				}
			}
			 catch (IllegalArgumentException e) {

						message = e.getClass() + " from JwtFilter : Illegal Argument while fetching the username !!";
						logger.error(message);
						e.printStackTrace();
						response.sendError(HttpStatus.BAD_REQUEST.value(),"UnAuthenticated access to application");
						return ;
				} catch (ExpiredJwtException e) {

						message = e.getClass() + " from JwtFilter : Given jwt token is expired !!";
						logger.error(message);
						e.printStackTrace();
						response.sendError(HttpStatus.BAD_REQUEST.value(),"UnAuthenticated access to application");
						return ;
				} catch (MalformedJwtException e) {

					message = e.getClass() + " from JwtFilter : Some changed has done in token !! Invalid Token";
						logger.error(message);
						e.printStackTrace();
						response.sendError(HttpStatus.BAD_REQUEST.value(),"UnAuthenticated access to application");
						return ;
				} catch (Exception e) {

					message = e.getClass() + " from JwtFilter : " + e.getMessage();
						logger.error(message);
						e.printStackTrace();
						response.sendError(HttpStatus.BAD_REQUEST.value(),"UnAuthenticated access to application");
						return ;
				}

			} else {
				try {
					throw new TokenNotFoundException("Token not found !!!");
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("TokenNotFoundException from JwtFilter : " + e1.getMessage());
					response.sendError(HttpStatus.BAD_REQUEST.value(),"UnAuthenticated access to application");
					return ;
				}
			}

			filterChain.doFilter(request, response);
	}

@Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	// TODO Auto-generated method stub
	logger.info("incomming request : {}",request.getRequestURI());
	logger.info("test : {}",request.getRequestURI().split("/")[1]);
	return allowedRequestMatchers.stream().anyMatch(matcher->matcher.matches(request.getRequestURI().split("/")[1]));
}
	
}
