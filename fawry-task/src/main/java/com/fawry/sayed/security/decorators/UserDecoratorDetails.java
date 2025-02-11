package com.fawry.sayed.security.decorators;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fawry.sayed.entities.User;



public class UserDecoratorDetails implements UserDetails{
	
	private final User user;

	public UserDecoratorDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRole().stream().map(AuthorityDecoratorSecurity::new).
				collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
