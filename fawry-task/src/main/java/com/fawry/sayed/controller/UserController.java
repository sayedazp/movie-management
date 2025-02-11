package com.fawry.sayed.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fawry.sayed.services.AuthServices;

import jakarta.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
	
	
	private final AuthServices authServices;

	public UserController(AuthServices authServices) {
		this.authServices = authServices;
	}
	
	@GetMapping("is-auth")
	public ResponseEntity<Boolean> isAuth(@CookieValue(value = "JWt") String token,
			@RequestParam(name = "mail", required = true) String mail){
		
		return ResponseEntity.ok(authServices.isAuthenticated(token, mail));
		
	}
}
