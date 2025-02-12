package com.fawry.sayed.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginResponse {
	
	
	@Email(message = "Please use a proper email format")
	@NotBlank(message = "User email cannot be blank")
	private String email;
	
	@NotBlank(message = "Token cannot be empty")
	private String token;
	
	@NotBlank(message = "User roles should be provided!")
	private Collection<? extends GrantedAuthority> role;
	 
	public Collection<? extends GrantedAuthority> getRole() {
		return role;
	}
	public void setRole(Collection<? extends GrantedAuthority> role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}


}
