package com.fawry.sayed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fawry.sayed.repositories.UserRepository;
import com.fawry.sayed.security.filters.CustomUserPasswordAuthenticationFilter;
import com.fawry.sayed.services.CustomUserDetailsService;
import com.fawry.sayed.services.JWTService;

@Configuration
public class ApplicationConfiguration {
	
	private final UserRepository userRepository;
	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;
 

    public ApplicationConfiguration(UserRepository userRepository, JWTService jwtService, UserDetailsService userDetailsService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

    @Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public AuthenticationManager myAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(userDetailsService);
	    provider.setPasswordEncoder(passwordEncoder);
	    return provider::authenticate;
	}	
	

}
