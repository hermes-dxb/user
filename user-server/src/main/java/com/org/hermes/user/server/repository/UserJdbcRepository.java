package com.org.hermes.user.server.repository;

import org.springframework.stereotype.Repository;

import com.org.hermes.user.server.model.UserDto;

@Repository
public interface UserJdbcRepository {

	int updateUserByUserId(UserDto userDto);
}
