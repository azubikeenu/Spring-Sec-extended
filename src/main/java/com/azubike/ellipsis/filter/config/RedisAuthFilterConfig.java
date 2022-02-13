package com.azubike.ellipsis.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azubike.ellipsis.api.service.RedisTokenService;
import com.azubike.ellipsis.filter.RedisAuthFilter;
import com.azubike.ellipsis.filter.RedisTokenFilter;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;

@Configuration
public class RedisAuthFilterConfig {
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;
	@Autowired
	private RedisTokenService tokenService;

	@Bean
	public FilterRegistrationBean<RedisAuthFilter> redisAuthFilter() {
		FilterRegistrationBean<RedisAuthFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new RedisAuthFilter(basicAuthUserRepository));
		registerationBean.addUrlPatterns("/api/auth/redis/v1/login");
		return registerationBean;
	}

	@Bean
	public FilterRegistrationBean<RedisTokenFilter> redisTokenFilter() {
		FilterRegistrationBean<RedisTokenFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new RedisTokenFilter(tokenService));
		registerationBean.addUrlPatterns("/api/auth/redis/v1/time");
		registerationBean.addUrlPatterns("/api/auth/redis/v1/logout");
		return registerationBean;
	}

}
