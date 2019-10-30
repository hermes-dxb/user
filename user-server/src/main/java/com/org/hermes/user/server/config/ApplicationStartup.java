package com.org.hermes.user.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.org.hermes.user.server.service.UserMasterService;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartup.class);

	@Autowired
	private UserMasterService userMasterSrvice;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOGGER.info("START : ############ Loading app details ###############");
		userMasterSrvice.loadConfig();
		LOGGER.info("END : ############ Loading app details ###############");
	}

}
