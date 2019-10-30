package com.org.hermes.user.server.util.converter;

import java.time.LocalDateTime;

import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.repository.entity.App;

/**
 * 
 * @author v.nayanar
 *
 */
public class AppConverter {

	/**
	 * 
	 * @param appDto
	 * @return
	 */
	public static App convertToAppEntity(AppDto appDto) {
		App app = new App();
		app.setAppId(appDto.getAppId());
		app.setAppSecret(appDto.getAppSecret());
		app.setAppName(appDto.getAppName());
		app.setAuthSource(appDto.getAuthSource());
		app.setAuthType(appDto.getAuthType());
		//app.setRedirectionUrl(appDto.getRedirectionUrl());
		app.setDescription(appDto.getDescription());
		app.setCreatedBy(appDto.getCreatedBy());
		app.setCreatedDate(appDto.getCreatedDate() != null ? appDto.getCreatedDate() : LocalDateTime.now());
		return app;
	}
	
	/**
	 * 
	 * @param app
	 * @return
	 */
	public static AppDto convertToAppDto(App app) {
		AppDto appDto = new AppDto();
		appDto.setAppId(app.getAppId());
		appDto.setAppSecret(app.getAppSecret());
		appDto.setAppName(app.getAppName());
		appDto.setAuthSource(app.getAuthSource());
		appDto.setAuthType(app.getAuthType());
		//appDto.setRedirectionUrl(app.getRedirectionUrl());
		appDto.setDescription(app.getDescription());
		appDto.setCreatedBy(app.getCreatedBy());
		appDto.setCreatedDate(app.getCreatedDate());
		return appDto;
	}
}
