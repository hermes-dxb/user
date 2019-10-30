package com.org.hermes.user.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.hermes.user.server.model.UserDto;
import com.org.hermes.user.server.model.UserRegistrationDto;
import com.org.hermes.user.server.repository.RoleRepository;
import com.org.hermes.user.server.repository.UserJdbcRepository;
import com.org.hermes.user.server.repository.UserRepository;
import com.org.hermes.user.server.repository.entity.Role;
import com.org.hermes.user.server.repository.entity.User;
import com.org.hermes.user.server.util.UserConstant;
import com.org.hermes.user.server.util.converter.UserConverter;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service("userService")
@EnableCaching
public class UserService {

	@Autowired
    private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserJdbcRepository userJdbcRepository;
    
	protected final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
    public UserDto findUserByEmail(String email) {
    	User userEntity = userRepository.findByEmail(email);
        return UserConverter.convertToUserDto(userEntity);
    }

    public void saveUserToCache(UserDto userDto) {
    	addToCache(UserConstant.CACHE_NAME_USER, userDto.getEmail(), userDto);
    }
    public int saveUser(UserRegistrationDto userRegistrationDto) {
    	int status = 1;
    	if(! StringUtils.isBlank(userRegistrationDto.getPassword())) {
    		userRegistrationDto.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
    	}
        userRegistrationDto.setStatus(1);
        //TODO
        Role role = roleRepository.findByRoleName("ADMIN");
        //RoleDto roleDto = RoleConverter.convertToRoleDto(role);
        //userDto.setRoles(new HashSet<RoleDto>(Arrays.asList(roleDto)));
        User userEntity = UserConverter.convertToUserEntity(userRegistrationDto);
        userEntity.setRoles(new HashSet<Role>(Arrays.asList(role)));
        try {
        	User user = userRepository.save(userEntity);
        	LOGGER.info("Saved UsedId {}", user.getUserId());	
		} catch (Exception e) {
			LOGGER.error("Exception in Save User : ", userRegistrationDto, e);
			status = 0;
		}
        return status;
    }
    
    public int saveUser(String userName) {
    	int status = 1;
    	UserDto userDto = (UserDto) getValueFromCache(UserConstant.CACHE_NAME_USER, userName);
    	/*if(! StringUtils.isBlank(userDto.getPassword())) {
    		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    	}*/
        userDto.setStatus(1);
        //TODO
        Role role = roleRepository.findByRoleName("ADMIN");
        //RoleDto roleDto = RoleConverter.convertToRoleDto(role);
        //userDto.setRoles(new HashSet<RoleDto>(Arrays.asList(roleDto)));
        User userEntity = UserConverter.convertToUserEntity(userDto);
        userEntity.setRoles(new HashSet<Role>(Arrays.asList(role)));
        try {
        	User user = userRepository.save(userEntity);
        	LOGGER.info("Saved UsedId {}", user.getUserId());
		} catch (Exception e) {
			LOGGER.error("Exception in Save User : ", userDto, e);
			status = 0;
		}
        return status;
    }
    
    public void sendOtp(UserDto userDto) {
    	String email = userDto.getEmail();
    	int otp = generateOTP();
    	LOGGER.info("OTP Send {}", otp);
    	addToCache(UserConstant.CACHE_NAME_OTP, email, otp);
    }
    
	private int generateOTP() {
		Random random = new Random();
		int otp = 1000 + random.nextInt(9000);
		return otp;
	}
	
    public boolean validateOtp(UserDto userDto) {
    	boolean isValid = false;
    	int cachedOtp = (int) getValueFromCache(UserConstant.CACHE_NAME_OTP, userDto.getEmail());
    	/*if(userDto.getOtp() == cachedOtp) {
    		isValid = true;
    	}*/
    	return isValid;
    }
    
    private Object getValueFromCache (String cache, String email) {
    	Object cachedValue = -1;
    	Cache cacheStore = CacheManager.getInstance().getCache(cache);
        Element otpElement =  cacheStore.get(email);
        if (otpElement != null) {
        	cachedValue =  otpElement.getObjectValue();
        }
        return cachedValue;
    }
    
    private void addToCache (String cache, String email, Object value) {
    	Cache cacheStore = CacheManager.getInstance().getCache(cache);
        Element element = new Element(email, value);
        cacheStore.put(element);
    }
    
    public List<UserDto> getAllUsers() {
    	List<User> users = userRepository.findAll();
    	if(users != null && ! users.isEmpty()) {
    		List<UserDto> userDtos = new ArrayList<>(users.size());
    		users.forEach(userEntity -> userDtos.add(UserConverter.convertToUserDto(userEntity)));
    		return userDtos;
    	} else {
    		return null;
    	}
    }
    
    public int deleteUser(UserDto userDto) {
    	User userEntity =  UserConverter.convertToUserEntity(userDto);
    	int status = 1;
    	try {
    		userRepository.delete(userEntity);
		} catch (Exception e) {
			LOGGER.error("Exception in delete user : ", userDto, e);
			status = 0;
		}
    	return status;
    }
    
    public int updateUser(UserDto userDto) {
    	int status = 0;
    	try {
    		status = userJdbcRepository.updateUserByUserId(userDto);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return status;
    }
    
	public UserDto findUserByUserName(String userName) {
		UserDto userDto = null;
		try {
			User userEntity = userRepository.findByEmail(userName);
			userDto = UserConverter.convertToUserDto(userEntity);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("User details not found : {}", userName);
		} catch (Exception e) {
			LOGGER.error("Exception in fetching User details : {}", userName, e);
		}
		return userDto;
	}

}