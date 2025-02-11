package com.fawry.sayed.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginResponse {
	
	private String email;
	private String token;
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
