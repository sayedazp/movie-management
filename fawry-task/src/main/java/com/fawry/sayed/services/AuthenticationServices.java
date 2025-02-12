package com.fawry.sayed.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServices implements AuthServices{
	
	private final UserDetailsService userDetailsService;
	private final JWTService jwtService;
	
	
	@Autowired
	public AuthenticationServices(UserDetailsService userDetailsService, JWTService jwtService) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
	}

	@Override
	public boolean isAuthenticated(String token, String mail) {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(mail);
			return jwtService.isTokenValid(token, userDetails);
		}catch (UsernameNotFoundException e){
			return false;
		}
	}
}
