package com.fawry.sayed.security.filters;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fawry.sayed.services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter{
	
	
	@Qualifier("handlerExceptionResolver")
	private final HandlerExceptionResolver handlerExceptionResolver;
	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;
	
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, HandlerExceptionResolver handlerExceptionResolver, JWTService jwtService,
			UserDetailsService userDetailsService) {
        super(authenticationManager);
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String token = getJwtFromCookies(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
            final String userEmail = jwtService.extractUsername(token);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            filterChain.doFilter(request, response);
        }
    }		
	
	private String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> JWTService.COOKIE_NAME.
                    		equals(cookie.getName())) // Match the cookie name
                    .map(Cookie::getValue) // Extract the cookie value (JWT token)
                    .findFirst()
                    .orElse(null); // Return null if not found
        }
        return null;
    }
	private void removeTokenFromCookie(HttpServletRequest request, HttpServletResponse response,String token) {
		Cookie jwtCookie = new Cookie("JWt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false); 
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
	}

}
