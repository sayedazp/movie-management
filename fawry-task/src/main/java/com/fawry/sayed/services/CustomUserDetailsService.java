package com.fawry.sayed.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fawry.sayed.dto.LoginResponse;
import com.fawry.sayed.entities.User;
import com.fawry.sayed.repositories.UserRepository;
import com.fawry.sayed.security.decorators.UserDecoratorDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Autowired
	private JWTService jwtService;
	
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user =  userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User by this mail connot be found in the DB"));
		return new UserDecoratorDetails(user);
	}

	
}
