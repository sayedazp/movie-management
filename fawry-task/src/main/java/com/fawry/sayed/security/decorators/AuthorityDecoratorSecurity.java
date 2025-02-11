package com.fawry.sayed.security.decorators;

import org.springframework.security.core.GrantedAuthority;

import com.fawry.sayed.entities.Authority;

public class AuthorityDecoratorSecurity implements GrantedAuthority{
	
	private final Authority authority;
	
	
	public AuthorityDecoratorSecurity(Authority authority) {
		this.authority = authority;
	}


	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority.getName();
	}


	@Override
	public String toString() {
		return "AuthorityDecoratorSecurity [authority=" + authority.getName() + "]";
	}
	
	

}
