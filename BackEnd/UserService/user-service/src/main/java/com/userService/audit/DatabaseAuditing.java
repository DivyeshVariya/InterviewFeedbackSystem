package com.userService.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAuditing implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String uname = authentication
                    .getName();
        
        return Optional.of(uname);

	}

}
