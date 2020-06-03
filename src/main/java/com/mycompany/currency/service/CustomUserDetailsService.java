package com.mycompany.currency.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mycompany.currency.model.CustomUserDetails;
import com.mycompany.currency.model.User;
import com.mycompany.currency.repository.UserRepository;
import com.mycompany.currency.service.error.ErrorUnauthorizedException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws    ErrorUnauthorizedException{
		Optional<User> user = userRepository.findByUserName(userName);

		user.orElseThrow(() -> new ErrorUnauthorizedException("Unauthorized"));

		return user.map(CustomUserDetails::new).get();
	}
}
