package com.org.hermes.user.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.repository.UserMasterDao;


@Service
public class UserMasterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserMasterService.class);

	@Autowired
	private UserMasterDao userMasterDao;
	public static Map<String, AppDto> configMap = new ConcurrentHashMap<String, AppDto>();

	public Map<String, AppDto> getAllConfig() {
		Map<String, AppDto> configurations = userMasterDao.fetchAllConfig();
		return configurations;
	}
	public void loadConfig() {
		LOGGER.info("START : loadConfig");
		configMap.clear();
		Map<String, AppDto> configurations = getAllConfig();
		configMap.putAll(configurations);
		LOGGER.info("END : loadConfig");
	}
}
