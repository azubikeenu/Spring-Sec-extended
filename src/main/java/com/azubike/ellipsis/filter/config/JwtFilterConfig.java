package com.azubike.ellipsis.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azubike.ellipsis.api.service.JwtService;
import com.azubike.ellipsis.filter.JwtAuthFilter;
import com.azubike.ellipsis.filter.JwtTokenFilter;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;

@Configuration
public class JwtFilterConfig {
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;
	@Autowired
	private JwtService jwtService;

	@Bean
	public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
		FilterRegistrationBean<JwtAuthFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new JwtAuthFilter(basicAuthUserRepository));
		registerationBean.addUrlPatterns("/api/auth/jwt/v1/login");
		return registerationBean;
	}

	@Bean
	public FilterRegistrationBean<JwtTokenFilter> jwtTokenFilter() {
		FilterRegistrationBean<JwtTokenFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new JwtTokenFilter(jwtService));
		registerationBean.addUrlPatterns("/api/auth/jwt/v1/time");
		registerationBean.addUrlPatterns("/api/auth/jwt/v1/logout");
		return registerationBean;
	}

}
