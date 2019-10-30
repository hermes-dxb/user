package com.org.hermes.user.server.service;

import org.springframework.cache.annotation.EnableCaching;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@EnableCaching
public class OtpService {
	
	public Cache otpCache;
	public OtpService() {
		otpCache = CacheManager.getInstance().getCache("otpCache");
	}

}
