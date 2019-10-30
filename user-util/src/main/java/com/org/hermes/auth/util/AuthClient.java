package com.org.hermes.auth.util;

import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthClient {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthClient.class);
	public static final String CLAIM_USERNAME = "userName";
	public static final String CLAIM_ROLES = "roles";

	public static Map<String, Object> validateJwtToken(String appId, String appSecretId, String authorization)
			throws GeneralSecurityException {
		Map<String, Object> payLoadMap = new HashMap<String, Object>();
		if (appId == null || authorization == null) {
			throw new LoginException("Invalid Token");
		} else {
			String jwtToken = getToken(authorization);
			String sigingKey = Base64.getEncoder().encodeToString(appSecretId.getBytes());
			try {
				Jws<Claims> jwsClaims = Jwts.parser()
						.setSigningKey(sigingKey)
						.parseClaimsJws(jwtToken);
				
				Claims claims = jwsClaims.getBody();
				Date expDate = claims.getExpiration();
				Date issueDate = claims.getIssuedAt();
				Date currentDate = new Date();
				if (expDate == null
						|| expDate.getTime() < currentDate.getTime()
						|| issueDate == null) {
					LOGGER.info("expDate:{} | issueDate: {} | currenttime: {}",
							expDate, issueDate, currentDate.getTime());
					throw new HermesAuthException("Token Expires", 1003);
				}
				claims.entrySet().forEach(claim -> {
					payLoadMap.put(claim.getKey(), claim.getValue());
				});
				return payLoadMap;
			} catch (SignatureException e) {
				LOGGER.info("Invalid JWT signature: ", e);
				throw new HermesAuthException("Invalid Token", 1000);
			} catch (MissingClaimException e) {
				LOGGER.info("Missing JWT Claim JWT : ", e);
				throw new HermesAuthException("Invalid Token", 1000);
			} catch (ExpiredJwtException e) {
				LOGGER.info("JWT Token Expired : ", e);
				throw new HermesAuthException("Token Expires", 1003);
			} catch (PrematureJwtException e) {
				LOGGER.info("Premature JWT : ", e);
				throw new HermesAuthException("Invalid Token", 1000);
			} catch (MalformedJwtException e) {
				LOGGER.info("Malformed JWT : ", e);
				throw new HermesAuthException("Invalid Token", 1000);
			} catch (ClaimJwtException e) {
				LOGGER.info("Invalid Claim JWT: ", e);
				throw new HermesAuthException("Invalid Token", 1000);
			} catch (UnsupportedJwtException e) {
				LOGGER.info("Unsupported JWT : ", e);
				throw new HermesAuthException("Invalid Token", 1000);
			}
		}
	}
	
	private static String getToken(String authorization) {
		LOGGER.info("getToken authorization {}", authorization);
		String token = null;
		if (authorization != null && authorization.contains(" ")) {
			token = authorization.split(" ")[1];
		} else {
			token = authorization;
		}
		return token;
	}
	
	/*public static void main (String[] args) {
		
		String token = generateToken();
				//"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb25zIiwicm9sZXMiOiJBRE1JTiIsImF1dGgiOiJTWVNURU0iLCJpYXQiOjE1NDg5MTE0NDcsImV4cCI6MTU0ODkxNTA0N30.cWjC7u-x4OaMA6q16ZjJHNooYq4hU5SH0-CT9BhHKtnWSi66SrZRO2pkQ23XRrBUjGHTJOBIGYrYC4j0QzuBGA";
		System.out.println(token);
		try {
			Map isValid = LoginClient.validateJwtToken("cons", "c5652731-b977-4ea0-bfea-71f871025053", token);
			System.out.println("#### " + isValid);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}*/
	
	private static String generateToken() {
		return Jwts.builder().setSubject("cons")
				.claim("roles", "HERMES_ADMIN;")
				.claim("auth", "SYSTEM")
				.claim("userName", "ram")
				.signWith(SignatureAlgorithm.HS512,
						Base64.getEncoder().encodeToString("c5652731-b977-4ea0-bfea-71f871025053".getBytes()))
				.setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 9000000000l)).compact();
	}
}
