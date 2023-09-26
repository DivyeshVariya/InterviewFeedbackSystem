package com.apiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.apiGateway.exception.UnauthorizedAccessException;
import com.apiGateway.utils.JwtHelper;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config>{

	private Logger logger=LoggerFactory.getLogger(AuthorizationFilter.class);
	
	@Autowired
	JwtHelper jwtHelper;
	
	@Autowired
	RouteValidator routeValidator;
	
	public AuthorizationFilter() {
		super(Config.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		logger.info("Outside the AuthorizationFilter ");
		return ((exchange, chain) -> {
			logger.info("From AuthorizationFilter : "+exchange.getRequest().getPath().toString());
			logger.info("check role interviewer : {}", routeValidator.isEndPointForHR.test(exchange.getRequest()));
			logger.info("check role hr : {}", routeValidator.isEndPointForInterviewer.test(exchange.getRequest()));
			if(routeValidator.isOpenEndPoint.test(exchange.getRequest()))
			{
				return chain.filter(exchange);
			}
			else
			{
			String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring(7);
			logger.info(jwtHelper.getRole(token));

				if (!(routeValidator.isEndPointForHR.test(exchange.getRequest()) && "ROLE_HR".equals(jwtHelper.getRole(token))) && !(routeValidator.isEndPointForInterviewer.test(exchange.getRequest()) && "ROLE_INTERVIEWER".equals(jwtHelper.getRole(token)))) {
					throw new UnauthorizedAccessException("UnAuthorizedAccess : Unauthorized access to application");
				}
			return chain.filter(exchange);
			}
		});

	}

	public static class Config {
	}
}
