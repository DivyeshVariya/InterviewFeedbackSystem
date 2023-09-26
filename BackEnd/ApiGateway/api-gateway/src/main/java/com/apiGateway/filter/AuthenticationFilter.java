package com.apiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.apiGateway.exception.AuthorizationHeaderMissingException;
import com.apiGateway.exception.UnKnownURLException;
import com.apiGateway.exception.UnauthorizedAccessException;
import com.apiGateway.utils.JwtHelper;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	public AuthenticationFilter() {
		super(Config.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	JwtHelper jwtHelper;

	@Autowired
	RouteValidator routeValidator;

	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		logger.info("Outside Authentication GatewayFilter");

		return ((exchange, chain) -> {

			logger.info("From AuthenticationFilter : " + exchange.getRequest().getPath().toString());
			
			logger.info("From AuthenticationFilter : {}" + routeValidator.isOpenEndPoint.test(exchange.getRequest()));
			
			if (routeValidator.isOpenEndPoint.test(exchange.getRequest())) {
				logger.info("Inside if block ");
				return chain.filter(exchange);
			} 
			else if (routeValidator.isEndPointForHR.test(exchange.getRequest())
					|| routeValidator.isEndPointForInterviewer.test(exchange.getRequest())) 
			{

				logger.info("Inside if else block Authentication GatewayFilter");

				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

					throw new AuthorizationHeaderMissingException("AuthHeaderMissing : Authorization Header is Missing");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

				logger.info("Token with Bearer {}", authHeader);

				if (authHeader != null && authHeader.startsWith("Bearer ")) {

					authHeader = authHeader.substring(7);

					logger.info("Substring {}", authHeader);

				}
				try {
					logger.info(authHeader);
					jwtHelper.validateToken(authHeader);

				} catch (Exception e) {
					throw new UnauthorizedAccessException("UnAuthenticatedAccess : Unauthenticated access to application");
				}
				return chain.filter(exchange);
			}
			else
			{
				logger.info("else block");
				throw new UnKnownURLException("UnKnowURL : Unknow request url : "+exchange.getRequest().getPath().toString());
			}
		});
	}

	public static class Config {
	}
}
