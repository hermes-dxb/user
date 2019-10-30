package com.org.hermes.user.server.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.model.AuthenticationDto;
import com.org.hermes.user.server.model.ResponseDto;
import com.org.hermes.user.server.model.UserDto;
import com.org.hermes.user.server.model.UserRegistrationDto;
import com.org.hermes.user.server.service.UserMasterService;
import com.org.hermes.user.server.service.UserService;

@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<ResponseDto> createNewUser(@RequestBody @Valid UserRegistrationDto userDto) {
		ResponseEntity<ResponseDto> respEnt = null;
        UserDto userExists = userService.findUserByEmail(userDto.getEmail());
        if (userExists != null) {
        	ResponseDto resp = createResponseDto(412, "User with same email exists", null);
        	respEnt = new ResponseEntity<ResponseDto>(resp, HttpStatus.PRECONDITION_FAILED);
        } else {
        	int status = userService.saveUser(userDto);
        	if (status != 0) {
            	ResponseDto resp = createResponseDto(200, "User created Successfully", null);
            	respEnt = new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
            	ResponseDto resp = createResponseDto(500, "User creation failed", null);
            	respEnt = new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return respEnt;
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> updateUser(UserDto userDto) {
		ResponseEntity<ResponseDto> respEnt = null;
		int status = userService.updateUser(userDto);
        if (status != 0) {
        	ResponseDto resp = createResponseDto(200, "User updated Successfully", null);
        	respEnt = new ResponseEntity<>(resp, HttpStatus.OK);
        } else {
        	ResponseDto resp = createResponseDto(500, "User update failed", null);
        	respEnt = new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respEnt;
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.DELETE, consumes="application/json")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody UserDto userDto) {
		ResponseEntity<ResponseDto> respEnt = null;
		int status = userService.deleteUser(userDto);
        if (status != 0) {
        	ResponseDto resp = createResponseDto(200, "User deleted Successfully", null);
        	respEnt = new ResponseEntity<>(resp, HttpStatus.OK);
        } else {
        	ResponseDto resp = createResponseDto(500, "User delete failed", null);
        	respEnt = new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respEnt;
    }
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUser() {
        List<UserDto> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/user-manager", method = RequestMethod.GET)
    public ModelAndView userManagement() {
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-list");
        //List<UserDto> userList = userService.getAllUsers();
        modelAndView.addObject("users", null);
        return modelAndView;
    }
	
	private ResponseDto createResponseDto (int status, String message, String detailedMessage) {
		ResponseDto resp = new ResponseDto();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setDetailedMessage(detailedMessage);
		return resp;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> createNewUser(@Valid UserDto userDto) {
        UserDto userExists = userService.findUserByEmail(userDto.getEmail());
        if (userExists != null) {
        	return new ResponseEntity<>("User with same email exists", HttpStatus.PRECONDITION_FAILED);
        } else {
        	userService.saveUserToCache(userDto);
        	userService.sendOtp(userDto);
        }
        return new ResponseEntity<>("OTP sent for validatation", HttpStatus.OK);
    }

    
    @RequestMapping(value = "/validate/otp", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> validateOtp(@RequestBody UserDto userDto, BindingResult bindingResult) {
    	ResponseEntity<ResponseDto> respEnt = null;
    	boolean isValid = userService.validateOtp(userDto);
    	if(isValid) {
    		int status = userService.saveUser(userDto.getEmail());
    		if (status != 0) {
            	ResponseDto resp = createResponseDto(200, "User created Successfully", null);
            	respEnt = new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
            	ResponseDto resp = createResponseDto(500, "User creation failed", null);
            	respEnt = new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    		
    	} else {
    		ResponseDto resp = createResponseDto(412, "OTP validation failed", null);
        	respEnt = new ResponseEntity<>(resp, HttpStatus.PRECONDITION_FAILED);;
    	}
    	return respEnt;
    }

    
	private Cookie createCookie(String appId, AuthenticationDto authDto) {
		Cookie cookie = null;
		AppDto appDto = UserMasterService.configMap.get(appId);
		cookie = new Cookie(appDto.getCookieName(), authDto.getToken());
		cookie.setDomain(appDto.getCookieDomain());
		cookie.setHttpOnly(appDto.isCookieHideFromScript());
		cookie.setMaxAge(appDto.getAccessTokenValidity()/1000);
		cookie.setPath(appDto.getCookiePath());
		cookie.setSecure(appDto.isCookieSecureOnly());
		return cookie;
	}

}
