package com.apiGateway.exception;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalExceptionHandler extends DefaultErrorAttributes{

	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		// TODO Auto-generated method stub
		
        Throwable error = this.getError(request);
        logger.error("Error occured : ", error);
        Map<String, Object> map = super.getErrorAttributes(request, options);
        map.remove("error");
        map.remove("requestId");
        map.remove("trace");
        
        return map;

	}
}
