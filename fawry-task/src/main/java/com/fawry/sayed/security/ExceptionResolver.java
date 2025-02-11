package com.fawry.sayed.security;

import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DatabindException;
import com.fawry.sayed.dto.ErrorResponseDto;
import com.fawry.sayed.services.JWTService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionResolver{
	protected final Log logger = LogFactory.getLog(getClass());

	@ExceptionHandler
    public ResponseEntity<String> handleException(Exception e, HttpServletRequest request){
		System.out.println(e.getMessage());
        return ResponseEntity.status(401).body(e.getMessage());
    }
	
	@ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorResponseDto procssAuthException(AuthenticationException e) {
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setMessage("Bad credentials");
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		return errorResponse;
    }
	@ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorResponseDto processJwtExpiry(ExpiredJwtException e, HttpServletRequest request, HttpServletResponse response) {
		String token = JWTService.getJwtFromCookies(request);
		ErrorResponseDto errorResponse = new ErrorResponseDto();
		errorResponse.setMessage("Json token expired!");
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		Cookie cookie = JWTService.removeTokenFromCookie(token);
		response.addCookie(cookie);
		return errorResponse;
    }
	@ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String processRuntimeException(RuntimeException e) {
		System.out.println(e.getMessage());
        return "problem happened";
    }

	@ExceptionHandler(DatabindException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public String processDataBindException(DatabindException e) {
        return e.getMessage();
    }
}
