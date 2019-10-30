package com.org.hermes.user.server.repository;

import java.util.Map;

import com.org.hermes.user.server.model.AppDto;

public interface UserMasterDao {

	Map<String, AppDto> fetchAllConfig();
}
