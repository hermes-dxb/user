package com.org.hermes.user.server.util.converter;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.org.hermes.user.server.model.RoleDto;
import com.org.hermes.user.server.model.UserDto;
import com.org.hermes.user.server.model.UserRegistrationDto;
import com.org.hermes.user.server.repository.entity.Role;
import com.org.hermes.user.server.repository.entity.User;
import com.org.hermes.user.server.repository.entity.UserLogin;
import com.org.hermes.user.server.util.UserConstant;

/**
 * user dto to entity and vice versa converter
 * 
 * @author v.nayanar
 *
 */
public class UserConverter {
	
	/**
	 * 
	 * @param userRegistrationDto
	 * @return
	 */
	public static UserLogin convertToUserLogin (UserRegistrationDto userRegistrationDto) {
		UserLogin userLogin = new UserLogin();
		userLogin.setUserId(userRegistrationDto.getUserId());
		userLogin.setEmail(userRegistrationDto.getEmail());
		userLogin.setPassword(userRegistrationDto.getPassword());
		userLogin.setStatus(userRegistrationDto.getStatus());
		userLogin.setCreatedBy(StringUtils.isBlank(userRegistrationDto.getCreatedBy()) ? UserConstant.USER_SELF : userRegistrationDto.getCreatedBy());
		userLogin.setCreatedDate(userRegistrationDto.getCreatedDate() != null ? userRegistrationDto.getCreatedDate() : LocalDateTime.now());
		return userLogin;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public static UserDto convertToUserDto (User user) {
		UserDto userDto = null;
		if(user != null) {
			userDto = new UserDto();
			userDto.setUserId(user.getUserId());
			userDto.setEmail(user.getEmail());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setMiddleName(user.getMiddleName());
			userDto.setMobile(user.getMobile());
			userDto.setStatus(user.getStatus());
			userDto.setCreatedBy(userDto.getCreatedBy());
			userDto.setCreatedDate(user.getCreatedDate());
			if(user.getRoles() != null && user.getRoles().size() > 0) {
				Set<RoleDto> roles = new HashSet<RoleDto>(user.getRoles().size());
				user.getRoles().forEach( roleEntity -> {
					roles.add(RoleConverter.convertToRoleDto(roleEntity));
				});
				userDto.setRoles(roles);
			}
		}
		return userDto;
	}
	
	public static User convertToUserEntity (UserRegistrationDto userRegistrationDto) {
		User user = new User();
		user.setUserId(userRegistrationDto.getUserId());
		user.setEmail(userRegistrationDto.getEmail());
		user.setFirstName(userRegistrationDto.getFirstName());
		user.setMiddleName(userRegistrationDto.getMiddleName());
		user.setLastName(userRegistrationDto.getLastName());
		user.setMobile(userRegistrationDto.getMobile());
		user.setStatus(userRegistrationDto.getStatus());
		user.setCreatedBy(StringUtils.isBlank(userRegistrationDto.getCreatedBy()) ? UserConstant.USER_SELF : userRegistrationDto.getCreatedBy());
		user.setCreatedDate(userRegistrationDto.getCreatedDate() != null ? userRegistrationDto.getCreatedDate() : LocalDateTime.now());
		Set<RoleDto> roleDtos = userRegistrationDto.getRoles();
		if(roleDtos != null && ! roleDtos.isEmpty()) {
			user.setRoles(new HashSet<>(roleDtos.size()));
			roleDtos.forEach(roleDto -> {
				Role role = RoleConverter.convertToRoleEntity(roleDto);
				user.getRoles().add(role);
			});
		}
		return user;
	}
	
	public static User convertToUserEntity (UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setMiddleName(userDto.getMiddleName());
		user.setLastName(userDto.getLastName());
		user.setMobile(userDto.getMobile());
		user.setOfficeNum(userDto.getOfficeNum());
		user.setOfficeExt(userDto.getOfficeExt());
		user.setStatus(userDto.getStatus());
		user.setCreatedBy(StringUtils.isBlank(userDto.getCreatedBy()) ? UserConstant.USER_SELF : userDto.getCreatedBy());
		user.setCreatedDate(userDto.getCreatedDate() != null ? userDto.getCreatedDate() : LocalDateTime.now());
		Set<RoleDto> roleDtos = userDto.getRoles();
		if(roleDtos != null && ! roleDtos.isEmpty()) {
			user.setRoles(new HashSet<>(roleDtos.size()));
			roleDtos.forEach(roleDto -> {
				Role role = RoleConverter.convertToRoleEntity(roleDto);
				user.getRoles().add(role);
			});
		}
		return user;
	}

}
