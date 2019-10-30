package com.org.hermes.user.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class AppDto extends AuditDto {
    private String appId;
    private String appSecret;
    private String appName;
    private String authSource;
    private String authType;
    //private String redirectionUrl;
    private String description;
	private String tokenType; 
	private int accessTokenValidity;
	private int status;
	private String cookiePath;
	private String cookieName;
	private String cookieDomain;
	private boolean cookieHideFromScript;
	private boolean cookieSecureOnly;
}
