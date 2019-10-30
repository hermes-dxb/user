/**
 * 
 */
package com.org.hermes.user.server.repository.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.org.hermes.user.server.model.AppDto;
import com.org.hermes.user.server.repository.sql.MasterQuery;

/**
 * @author v.nayanar
 *
 */
public class ObjectFactory {

	public static AppDto createAppDto(SqlRowSet rs) {
		AppDto appDto = new AppDto();
		appDto.setAppId(rs.getString(MasterQuery.COLUMN_NAME_APP_ID));
		appDto.setAppSecret(rs.getString(MasterQuery.COLUMN_NAME_APP_SECRET));
		appDto.setAppName(rs.getString(MasterQuery.COLUMN_NAME_APP_NAME));
		appDto.setDescription(rs.getString(MasterQuery.COLUMN_NAME_DESCRIPTION));
		appDto.setStatus(rs.getInt(MasterQuery.COLUMN_NAME_IS_ACTIVE));
		appDto.setAccessTokenValidity(rs.getInt(MasterQuery.COLUMN_NAME_TOKEN_VALIDITY));
		appDto.setAuthSource(rs.getString(MasterQuery.COLUMN_NAME_AUTH_SOURCE));
		appDto.setAuthType(rs.getString(MasterQuery.COLUMN_NAME_AUTH_TYPE));
		appDto.setTokenType(rs.getString(MasterQuery.COLUMN_NAME_TOKEN_TYPE));
		//appDto.setRedirectionUrl(rs.getString(MasterQuery.COLUMN_NAME_REDIRECTION_URL));
		appDto.setCookieDomain(rs.getString(MasterQuery.COLUMN_NAME_COOKIE_DOMAIN));
		String cookieName = rs.getString(MasterQuery.COLUMN_NAME_COOKIE_NAME);
		appDto.setCookieName(
				StringUtils.isBlank(cookieName) ? rs.getString(MasterQuery.COLUMN_NAME_APP_ID).toUpperCase()
						: cookieName);
		appDto.setCookiePath(rs.getString(MasterQuery.COLUMN_NAME_COOKIE_PATH));
		int hideFromScript = rs.getInt(MasterQuery.COLUMN_NAME_COOKIE_HIDE_FROM_SCRIPT);
		appDto.setCookieHideFromScript(hideFromScript == 1 ? true : false);
		int hideSecureOnly = rs.getInt(MasterQuery.COLUMN_NAME_COOKIE_SECURE_ONLY);
		appDto.setCookieSecureOnly(hideSecureOnly == 1 ? true : false);
		return appDto;
	}
}
