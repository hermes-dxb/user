package com.org.hermes.user.server.auth.service.impl;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.security.auth.login.LoginException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.org.hermes.user.server.auth.service.AuthService;
import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.model.AuthReqDto;
import com.org.hermes.user.server.model.AuthenticationDto;
import com.org.hermes.user.server.model.RoleDto;
import com.org.hermes.user.server.model.UserDto;
import com.org.hermes.user.server.service.UserMasterService;
import com.org.hermes.user.server.util.UserConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserAuthenticationServiceImpl implements AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDto authenticate(String userName, String password) throws GeneralSecurityException {
		UserDto userDto = null;
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);
			Authentication auth = authenticationManager.authenticate(authToken);
			userDto = new UserDto();
			userDto.setUserName(userName);
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			StringBuffer rolesString = new StringBuffer();
			for (GrantedAuthority authority : authorities) {
				if(!StringUtils.isBlank(authority.getAuthority())) {
					if(userDto.getRoles() == null) {
						userDto.setRoles(new HashSet<RoleDto>(authorities.size()));
					}
					RoleDto role = new RoleDto();
					role.setRoleName(authority.getAuthority().replace("ROLE_", ""));
					userDto.getRoles().add(role);
				}
				rolesString.append(authority.getAuthority() + ";");
			}
			/*try {
				CustomLdapUser ldapUser =   (CustomLdapUser) auth.getPrincipal();
				userDto.setEmail(ldapUser.getEmailId());
			} catch (Exception e) {
				LOGGER.info("Auth Principle is not CustomLdapUser {}", userName);
			}*/
		} catch (AuthenticationException e) {
			LOGGER.error("Authentication Exception user {}", userName, e);
			throw new LoginException("Bad Credentials!");
		} catch (BadCredentialsException | DisabledException | LockedException e) {
			LOGGER.error("Authentication Exception user {}", userName, e);
			throw new LoginException("Bad Credentials!");
		} catch (Exception e) {
			LOGGER.error("Authentication Exception user {}", userName, e);
			throw new LoginException("Bad Credentials!");
		}
		return userDto;
	}
	
	
	public AuthenticationDto authenticate(AuthReqDto authReqDto, boolean fromUi) throws GeneralSecurityException {
		/*if (fromUi) {
			validateClientId(authReqDto.getAppId());
		} else {
			validateClientId(authReqDto.getAppId(), authReqDto.getAppSecretId());
		}*/
		validateClientId(authReqDto.getAppId());
		if (StringUtils.isBlank(authReqDto.getPassword())) {
			LOGGER.error("login failed Empty password ===> client : {} userName : {} ", authReqDto.getAppId(),
					authReqDto.getUserName());
			throw new LoginException("Bad Credentials!");
		}
		UserDto userDto = authenticateUser(authReqDto.getUserName(), authReqDto.getPassword());
		try {
			List<String> roles = getUserRoles(userDto);
			AppDto clientDetail = UserMasterService.configMap.get(authReqDto.getAppId());
			String token = generateToken(clientDetail, userDto, roles);
			AuthenticationDto authDto = new AuthenticationDto();
			authDto.setToken(token);
			authDto.setUserName(authReqDto.getUserName());
			authDto.setEmailId(userDto.getEmail());
			authDto.setExpiresIn(clientDetail.getAccessTokenValidity());
			authDto.setRoles(roles);
			authDto.setTokenType(clientDetail.getTokenType());
			return authDto;
		} catch (Exception e) {
			LOGGER.error("login failed ===> client : {} userName : {} ", authReqDto.getAppId(),
					authReqDto.getUserName(), e);
			throw new GeneralSecurityException("Unauthorized");
		}
	}

	private void validateClientId(String clientId, String clientSecret) throws LoginException {
		String clientSecretId = null;
		int status = 0;
		AppDto appDto = UserMasterService.configMap.get(clientId);
		if (appDto != null) {
			clientSecretId = appDto.getAppSecret();
			status = appDto.getStatus();
		}
		if (clientSecretId == null || clientSecret == null || !clientSecret.equals(clientSecretId) || (status == 0)) {
			LOGGER.error("App auth failed ===> Invalid ClientId or Client Secret Id : {}", clientId);
			throw new LoginException("Invalid ClientId and Client Secret Id");
		}
	}

	private void validateClientId(String clientId) throws LoginException {
		AppDto appDto = UserMasterService.configMap.get(clientId);
		if (appDto != null) {
			if (appDto.getStatus() == 0) {
				LOGGER.error("App auth failed ===> Invalid ClientId {}", clientId);
				throw new LoginException("Invalid ClientId");
			}
		}

	}

	private UserDto authenticateUser(String userName, String password) throws GeneralSecurityException {
		UserDto userDto = authenticate(userName, password);
		return userDto;
	}

	private List<String> getUserRoles(UserDto userDto) {
		List<String> roles = new ArrayList<String>();
		
		if(userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
			userDto.getRoles().forEach(role-> roles.add(role.getRoleName()));
		} else {
			roles.add("CONS_USER");
		}
		return roles;
	}

	private String generateToken(AppDto appDto, UserDto userDto, List<String> roles) throws GeneralSecurityException {
		String token = null;
		if (!StringUtils.isBlank(appDto.getTokenType())
				&& appDto.getTokenType().equalsIgnoreCase(UserConstant.TOKEN_TYPE_JWT)) {
			token = createJwtToken(appDto, userDto, roles);
		} else {
			token = UUID.randomUUID().toString();
		}
		return token;
	}

	private String createJwtToken(AppDto appDto, UserDto userDto, List<String> roles) throws GeneralSecurityException {
		String roleString = "";
		if (roles != null && !roles.isEmpty()) {
			roleString = String.join(UserConstant.DELIMITER_SEMI_COLAN, roles);
		}
		String serviceToken = null;
		int tokenExpiresInMill = appDto.getAccessTokenValidity();
		Date now = new Date();
		long nowLong = now.getTime();
		Date validity = new Date(nowLong + tokenExpiresInMill);
		serviceToken = Jwts.builder().setSubject(appDto.getAppId()).claim(UserConstant.JWT_TOKEN_ROLES_KEY, roleString)
				.claim(UserConstant.JWT_TOKEN_AUTH_KEY, UserConstant.JWT_TOKEN_AUTH_VAL)
				.claim(UserConstant.JWT_TOKEN_USERNAME_KEY, userDto.getUserName())
				.claim(UserConstant.JWT_TOKEN_FIRST_NAME_KEY, userDto.getFirstName())
				.claim(UserConstant.JWT_TOKEN_LAST_NAME_KEY, userDto.getLastName())
				.signWith(SignatureAlgorithm.HS512,
						Base64.getEncoder().encodeToString(appDto.getAppSecret().getBytes()))
				.setIssuedAt(now).setExpiration(validity).compact();
		return serviceToken;

	}

}
