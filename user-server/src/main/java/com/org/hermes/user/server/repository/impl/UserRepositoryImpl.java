package com.org.hermes.user.server.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.org.hermes.user.server.model.UserDto;
import com.org.hermes.user.server.repository.UserJdbcRepository;
import com.org.hermes.user.server.repository.sql.UserQuery;

@Repository
public class UserRepositoryImpl implements UserJdbcRepository {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	protected final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	@Override
	public int updateUserByUserId(UserDto userDto) {
		int status = 0;
		Object[] params = new Object[] {
				userDto.getStatus(),
				
				userDto.getEmail(),
				userDto.getFirstName(),
				userDto.getLastName(),
				userDto.getMobile(),
				userDto.getUpdatedBy(),
				userDto.getUserId(),
		};
		try {
			status = jdbcTemplate.update(UserQuery.UPDATE_USER_BY_ID, params);
		} catch (Exception e) {
			LOGGER.error("Exception in user update : ", userDto, e);
			throw e;
		} 
		return status;
	}
}
