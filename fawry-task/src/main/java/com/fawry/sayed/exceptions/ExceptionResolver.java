package com.fawry.sayed.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fawry.sayed.dto.ErrorResponseDto;
import com.fawry.sayed.services.JWTService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionResolver{
	protected final Log logger = LogFactory.getLog(getClass());



	
	@ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponseDto processUsernameNotFoundException(UsernameNotFoundException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.FORBIDDEN.value(),
				"Bad credentials!").setTimestamp(LocalDateTime.now()).build();
		return errorResponse;
    }
	
	@ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponseDto procssAuthException(AuthenticationException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.FORBIDDEN.value(),
				"Bad credentials!").setTimestamp(LocalDateTime.now()).build();
		return errorResponse;
    }
	
	
	@ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponseDto processJwtExpiry(ExpiredJwtException e, HttpServletRequest request, HttpServletResponse response) {
		String token = JWTService.getJwtFromCookies(request);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.FORBIDDEN.value(),
				"Json token expired!").setTimestamp(LocalDateTime.now()).build();
		Cookie cookie = JWTService.removeTokenFromCookie(token);
		response.addCookie(cookie);
		return errorResponse;
    }
	
	@ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponseDto processJwtExpiry(BadCredentialsException e, HttpServletRequest request, HttpServletResponse response) {
		logger.error(e);
		String token = JWTService.getJwtFromCookies(request);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.FORBIDDEN.value(),
				"Bad credentials!").setTimestamp(LocalDateTime.now()).build();
		Cookie cookie = JWTService.removeTokenFromCookie(token);
		response.addCookie(cookie);
		return errorResponse;
    }
	
	
	@ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto processJwtExpiry(ConstraintViolationException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.BAD_REQUEST.value(),
				e.getMessage()).setTimestamp(LocalDateTime.now()).build();
		return errorResponse;
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.BAD_REQUEST.value(),
				e.getFieldErrors().stream().map(x->x.getDefaultMessage())
				.collect(Collectors.toList()).toString())
				.setTimestamp(LocalDateTime.now()).build();
		return errorResponse;
    }
	@ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseDto processEntityNotFoundException(EntityNotFoundException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.NOT_FOUND
				.value(), e.getMessage())
				.setTimestamp(LocalDateTime.now()).build();
		return errorResponse;
    }
	

	@ExceptionHandler(MalformedRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponseDto processDataBindException(MalformedRequestException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.BAD_REQUEST.value(),
				e.getMessage()).setTimestamp(LocalDateTime.now()).build();
        return errorResponse;
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDto handleException(HttpMessageNotReadableException e) {
		logger.error(e);
		ErrorResponseDto errorResponse = new ErrorResponseDto.ErrorBuilder(HttpStatus.BAD_REQUEST.value(),
				e.getMessage()).setTimestamp(LocalDateTime.now()).build();
		return errorResponse;
    }
	@ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String processRuntimeException(RuntimeException e) {
		logger.error(e);
        return "problem happened";
    }

}
