package com.org.hermes.user.server.repository.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.repository.UserMasterDao;
import com.org.hermes.user.server.repository.sql.MasterQuery;
import com.org.hermes.user.server.repository.util.ObjectFactory;

@Repository
public class UserMasterDaoImpl implements UserMasterDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserMasterDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, AppDto> fetchAllConfig() {
		LOGGER.info("START : fetchAllConfig");
		Map<String, AppDto> configMap = new HashMap<String, AppDto>();
		try {
			SqlRowSet rs = jdbcTemplate.queryForRowSet(MasterQuery.SELECT_ACTIVE_APP_DETAILS);
			while (rs.next()) {
				configMap.put(rs.getString(MasterQuery.COLUMN_NAME_APP_ID), ObjectFactory.createAppDto(rs));
			}
		} catch (Exception e) {
			LOGGER.error("Exception in loading application configurations");
			throw e;
		}
		LOGGER.info("END : fetchAllConfig");
		return configMap;
	}
}
