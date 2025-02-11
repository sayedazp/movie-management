package com.fawry.sayed.security.filters;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fawry.sayed.dto.LoginRequest;
import com.fawry.sayed.dto.LoginResponse;
import com.fawry.sayed.security.CustomAuthenticationFailureHandler;
import com.fawry.sayed.services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomUserPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final JWTService jwtService;
	private final HandlerExceptionResolver handlerExceptionResolver;
    private final ObjectMapper objectMapper = new ObjectMapper();

	public CustomUserPasswordAuthenticationFilter(JWTService jwtService,
			HandlerExceptionResolver handlerExceptionResolver, AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.jwtService = jwtService;
		this.handlerExceptionResolver = handlerExceptionResolver;
		setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
		setFilterProcessesUrl("/login");
	}

//	public CustomUserPasswordAuthenticationFilter(JWTService jwtService, AuthenticationManager authenticationManager) {
//		this.jwtService = jwtService;
//		this.authenticationManager = authenticationManager;
//	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
		
		super.doFilter(request, response, filterChain);
		
	}

	@Override   
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			LoginRequest login = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
			String email = login.getEmail();
			String password = login.getPassword();
	        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(email, password);
	        return getAuthenticationManager().authenticate(authenticationToken);
		}catch (DatabindException dBE){
			throw new RuntimeException("Please make sure you are entering valid input", dBE);
		} 
		catch (IOException e) {
			throw new RuntimeException("lol", e);
		}
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        String email = authentication.getName();
        String token = jwtService.generateToken(new HashMap<String, Object>(), email);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.addHeader("Authorization", "Bearer " + token);
        LoginResponse responseBody = new LoginResponse();
        responseBody.setEmail(email);
        responseBody.setRole(authentication.getAuthorities());
        Cookie jwtCookie = new Cookie(JWTService.COOKIE_NAME, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); 
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(jwtService.getJwtExpiration());
        response.addCookie(jwtCookie);
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
    @Override
    protected String obtainUsername(HttpServletRequest request) {
    	// TODO Auto-generated method stub
    	return super.obtainUsername(request);
    }
}
