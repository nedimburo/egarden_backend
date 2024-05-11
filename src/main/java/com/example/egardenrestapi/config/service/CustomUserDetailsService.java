package com.example.egardenrestapi.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.egardenrestapi.users.entities.UserEntity;
import com.example.egardenrestapi.users.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity =userRepository.findByUsernameOrEmail(username, username);
		if (userEntity ==null) {
			throw new UsernameNotFoundException("User not found.");
		}
		return new CustomUserDetails(userEntity);
	}

}
