package com.org.hermes.user.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.hermes.user.server.model.ResponseDto;
import com.org.hermes.user.server.service.UserMasterService;

@RestController
public class UserMasterController {

	@Autowired
	private UserMasterService userMasterService;
	
	@RequestMapping(value = "/appdetails/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAppDetailsCache() {
		userMasterService.loadConfig();
		ResponseDto resp = new ResponseDto();
		resp.setMessage("App details cache reloaded");
		resp.setStatus(200);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
