package com.azubike.ellipsis.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azubike.ellipsis.api.service.SessionTokenService;
import com.azubike.ellipsis.filter.SessionCookieAuthFilter;
import com.azubike.ellipsis.filter.SessionCookieTokenFilter;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;

@Configuration
public class SessionCookieAuthFilterConfig {
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;
	@Autowired
	private SessionTokenService tokenService;

	@Bean
	public FilterRegistrationBean<SessionCookieAuthFilter> sessionCookieAuthFilter() {
		FilterRegistrationBean<SessionCookieAuthFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new SessionCookieAuthFilter(basicAuthUserRepository));
		registerationBean.addUrlPatterns("/api/auth/session-cookie/v1/login");
		return registerationBean;
	}

	@Bean
	public FilterRegistrationBean<SessionCookieTokenFilter> sessionCookieTokenFilter() {
		FilterRegistrationBean<SessionCookieTokenFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new SessionCookieTokenFilter(tokenService));
		registerationBean.addUrlPatterns("/api/auth/session-cookie/v1/time");
		registerationBean.addUrlPatterns("/api/auth/session-cookie/v1/logout");
		return registerationBean;
	}

}
