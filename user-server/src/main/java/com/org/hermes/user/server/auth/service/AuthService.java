package com.org.hermes.user.server.auth.service;

import java.security.GeneralSecurityException;

import com.org.hermes.user.server.model.AuthReqDto;
import com.org.hermes.user.server.model.AuthenticationDto;
import com.org.hermes.user.server.model.UserDto;

public interface AuthService {

	UserDto authenticate(String userName, String password) throws GeneralSecurityException;
	AuthenticationDto authenticate(AuthReqDto userName, boolean fromUi) throws GeneralSecurityException;
}
