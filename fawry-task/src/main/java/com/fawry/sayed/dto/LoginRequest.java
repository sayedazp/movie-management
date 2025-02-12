package com.fawry.sayed.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	
	@Email(message = "Please use a proper email format")
	@NotBlank(message = "User email cannot be blank")
	private String email;
	
	@NotBlank(message="Password cannot be blank!")
	private String password;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
