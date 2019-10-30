package com.org.hermes.user.server.controller;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.org.hermes.user.server.auth.service.AuthService;
import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.model.AuthenticationDto;
import com.org.hermes.user.server.model.ResponseDto;
import com.org.hermes.user.server.model.UserDto;
import com.org.hermes.user.server.service.UserMasterService;
import com.org.hermes.user.server.service.UserService;

@Controller
@EnableCaching
public class RegController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    


    
    
}
