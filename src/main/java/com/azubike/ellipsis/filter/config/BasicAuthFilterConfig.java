package com.azubike.ellipsis.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azubike.ellipsis.filter.BasicAuthFilter;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;

@Configuration
public class BasicAuthFilterConfig {
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@Bean
	public FilterRegistrationBean<BasicAuthFilter> basicAuthFilter() {
		FilterRegistrationBean<BasicAuthFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new BasicAuthFilter(basicAuthUserRepository));
		registerationBean.addUrlPatterns("/api/auth/basic/v1/time");
		return registerationBean;
	}

}
