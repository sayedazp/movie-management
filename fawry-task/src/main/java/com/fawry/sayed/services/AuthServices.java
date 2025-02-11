package com.fawry.sayed.services;

import com.fawry.sayed.dto.LoginResponse;

public interface AuthServices {
	boolean isAuthenticated(String token, String mail);
}
