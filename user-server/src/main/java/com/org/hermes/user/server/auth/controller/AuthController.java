package com.org.hermes.user.server.auth.controller;

import java.security.GeneralSecurityException;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.hermes.user.server.auth.service.AuthService;
import com.org.hermes.user.server.model.AuthReqDto;
import com.org.hermes.user.server.model.AuthenticationDto;
import com.org.hermes.user.server.model.ResponseDto;

@RestController
public class AuthController {

	protected final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	protected AuthService authService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ResponseDto> authenticate(@RequestBody AuthReqDto authReq) throws GeneralSecurityException {
		LOGGER.debug("authenticate:ENTRY");
		try {
			LOGGER.info("Attempt to login ===> {},{}", authReq.getAppId(), authReq.getUserName());
			if (StringUtils.isEmpty(authReq.getUserName()) || StringUtils.isEmpty(authReq.getPassword())
					|| StringUtils.isEmpty(authReq.getAppId())) {
				return getBadRequest();
			}
			LOGGER.info("{} ||| {}",authReq.getPassword() , passwordEncoder.encode(authReq.getPassword()));
			AuthenticationDto authResDto = authService.authenticate(authReq, false);
			authResDto.setStatus(200);
			authResDto.setMessage("Authenticated Successfully");
			LOGGER.debug("login:EXIT");
			return new ResponseEntity<ResponseDto>(authResDto, HttpStatus.OK);
		} catch (final LoginException ex) {
			LOGGER.error("login:EXIT {}|{}",authReq.getAppId(), authReq.getUserName(), ex);
			return getUnAuthorizedReq();
		} catch (final Exception ex) {
			LOGGER.error("login:EXIT {}|{}",authReq.getAppId(), authReq.getUserName(), ex);
			return getUnAuthorizedReq();
		}
	}

	private ResponseEntity<ResponseDto> getBadRequest() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage("Bad Request");
		responseDto.setStatus(400);
		ResponseEntity<ResponseDto> resp = new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
		LOGGER.debug("authentication:EXIT");
		return resp;

	}

	private ResponseEntity<ResponseDto> getUnAuthorizedReq() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage("Unauthorized");
		responseDto.setStatus(401);
		ResponseEntity<ResponseDto> resp = new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
		LOGGER.debug("authentication:EXIT");
		return resp;

	}
}
