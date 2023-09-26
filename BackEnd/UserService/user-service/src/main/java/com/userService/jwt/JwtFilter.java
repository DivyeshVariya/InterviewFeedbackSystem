package com.userService.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.userService.config.CustomUserDetailsService;
import com.userService.exception.InvalidTokenException;
import com.userService.exception.TokenNotFoundDBException;
import com.userService.exception.TokenNotFoundException;
import com.userService.model.JwtToken;
import com.userService.repository.JwtTokenRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	private final List<AntPathRequestMatcher> excludedMatchers = List.of(new AntPathRequestMatcher("/swagger-ui/**"),
			new AntPathRequestMatcher("/v3/api-docs/**"), new AntPathRequestMatcher("/userAuth/**"));

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info(request.getRequestURI());
		// Authorization

		String requestHeader = request.getHeader("Authorization");
		// Bearer
		logger.info(" Header :  {}", requestHeader);
		String username = null;
		String token = null;

		String message = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			// looking good
			token = requestHeader.substring(7);
			logger.info(token);
			try {

				username = this.jwtHelper.getUsernameFromToken(token);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					// fetch user detail from username
					UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
					Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
					Boolean alreadyExpairedToken = this.jwtTokenRepository.findById(token).get().isExpaired();
					if (validateToken && !alreadyExpairedToken) {

						// set the authentication
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						logger.info("Authentication and Validation is done !!!");
					}
					else {
						
						try {
							message="ValidationFailException from JwtLogoutHandler : Validation fails !!";
							logger.error(message);
							throw new ValidationException("Validation fails !!");
						} catch (Exception e1) {
							e1.printStackTrace();
							logger.error(e1.getClass()+" from JwtFilter : "+e1.getMessage());
						}
					}
				}

			} catch (IllegalArgumentException e) {

				try {
					message = e.getClass() + " from JwtFilter : Illegal Argument while fetching the username !!";
					logger.error(message);
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass() + " from JwtFilter : " + e1.getMessage());
				}

			} catch (ExpiredJwtException e) {

				try {
					message = e.getClass() + " from JwtFilter : Given jwt token is expired !!";
					logger.error(message);
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass() + " from JwtFilter : " + e1.getMessage());
				}

			} catch (MalformedJwtException e) {
				try {
					message = e.getClass() + " from JwtFilter : Some changed has done in token !! Invalid Token";
					logger.error(message);
					e.printStackTrace();
					} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass() + " from JwtFilter : " + e1.getMessage());
				}
			} catch (Exception e) {
				try {
					message = e.getClass() + " from JwtFilter : " + e.getMessage();
					logger.error(message);
					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error(e1.getClass() + " from JwtFilter : " + e1.getMessage());
				}
			}

		} else {
			try {
				throw new TokenNotFoundException("Token not found !!!");
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("TokenNotFoundException from JwtFilter : " + e1.getMessage());
			}
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// TODO Auto-generated method stub
		return excludedMatchers.stream().anyMatch(matcher -> matcher.matches(request));
	}

}
