package com.emailService.jwt;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emailService.exception.TokenNotFoundException;
import com.emailService.exception.UnauthorizedAccessException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	private Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
	private final List<String> allowedRequestMatchers = List.of("swagger-ui","v3");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		logger.info(request.getRequestURI());
		
		String requestAuthHeader=request.getHeader("My_SECRET");
		
		logger.info("authHeader : {}",requestAuthHeader);
		
		String token=null;
		String message=null;
		
		if(requestAuthHeader!=null)
		{
			token=requestAuthHeader;
			logger.info("token : {}",token);
			
			try {
					if(!token.equals("zxCvbNm<>"))
					{
						throw new UnauthorizedAccessException("UnAuthenticatedAccess : Unauthenticated access to application");
					}
					logger.info("from validate method : {}",request.getRequestURI());
			}
			catch (Exception e) {

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
					return;
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
