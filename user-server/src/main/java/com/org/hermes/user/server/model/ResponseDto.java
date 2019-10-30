package com.org.hermes.user.server.model;

import lombok.Data;

@Data
public class ResponseDto {
	int status;
	private String message;
	private String detailedMessage;
}
