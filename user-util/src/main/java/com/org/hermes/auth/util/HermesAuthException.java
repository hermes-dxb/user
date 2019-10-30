package com.org.hermes.auth.util;

import javax.security.auth.login.LoginException;

public class HermesAuthException extends LoginException {

	public HermesAuthException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
