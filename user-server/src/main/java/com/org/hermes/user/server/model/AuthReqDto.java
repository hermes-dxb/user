package com.org.hermes.user.server.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class AuthReqDto {
	
	String userName;
	String password;
	String appId;
	String appSecretId;
	//String grantType;

}
