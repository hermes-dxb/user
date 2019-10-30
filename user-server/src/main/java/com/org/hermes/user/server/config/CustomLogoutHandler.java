/**
 * 
 */
package com.org.hermes.user.server.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @author v.nayanar
 *
 */
public final class CustomLogoutHandler implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		handleLogOutResponse(response, request);
	}

	private void handleLogOutResponse(HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				Cookie newCookie = new Cookie(cookie.getName(), null);
				newCookie.setMaxAge(0);
				newCookie.setValue(null);
				newCookie.setPath("/");
				response.addCookie(newCookie);
			}
		}
	}

}
