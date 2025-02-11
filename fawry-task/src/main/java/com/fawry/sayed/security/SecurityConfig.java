package com.fawry.sayed.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fawry.sayed.security.filters.CustomUserPasswordAuthenticationFilter;
import com.fawry.sayed.security.filters.ExceptionHandlingFilter;
import com.fawry.sayed.security.filters.JwtAuthenticationFilter;
import com.fawry.sayed.services.JWTService;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserPasswordAuthenticationFilter customUserPasswordAuthenticationFilter;
	private final ExceptionHandlingFilter exceptionHandlingFilter; 

	

	public SecurityConfig(JWTService jwtService, AuthenticationManager authenticationManager,
			JwtAuthenticationFilter jwtAuthenticationFilter,
			CustomUserPasswordAuthenticationFilter customUserPasswordAuthenticationFilter,
			ExceptionHandlingFilter exceptionHandlingFilter) {
		super();
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.customUserPasswordAuthenticationFilter = customUserPasswordAuthenticationFilter;
		this.exceptionHandlingFilter = exceptionHandlingFilter;
	}

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().cors().and()
        		.headers().frameOptions().sameOrigin().and()
        		
        		.authorizeRequests().requestMatchers("/login").permitAll().and()
        		.authorizeRequests().requestMatchers("/login2").permitAll().and()
        		
        		.authorizeRequests().requestMatchers("movies/**").authenticated().and()
//        		.authorizeRequests().requestMatchers("/movies/**").permitAll().and()
                .addFilterBefore(customUserPasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        		 .addFilterBefore(jwtAuthenticationFilter, CustomUserPasswordAuthenticationFilter.class)
        		 .addFilterBefore(exceptionHandlingFilter, JwtAuthenticationFilter.class)
        		 
        		 .sessionManagement(sess -> sess
        	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
        	            )
                // login configuration
                .formLogin().disable()
                // can either be mapping or file
                .build();
    }
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
}
