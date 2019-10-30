package com.org.hermes.user.server.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuthenticationDto extends ResponseDto {
	private String token;
	//private String serviceKey;
	private String userName;
	private String tokenType;
	private int expiresIn;
	private List<String> roles;
	//private String redirectUrl;
	private String userId;
	private String emailId;
}
