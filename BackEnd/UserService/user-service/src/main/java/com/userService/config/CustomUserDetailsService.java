package com.userService.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.userService.model.Users;
import com.userService.repository.UserManagerRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserManagerRepository userManagerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		Optional<Users> user=userManagerRepository.findByUserEmailId(emailId);
		return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found..."));
	}

}
