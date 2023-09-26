package com.feedbackService.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component(value = "DatabaseAuditing")
public class DatabaseAuditing implements AuditorAware<String>{

	private String userName="anonymousUser";
	
	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub
        
		return Optional.of(userName);
	}

	public void setCurrentUser(String userName) {
		this.userName=userName;
	}
}
