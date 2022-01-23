package com.azubike.ellipsis.filter.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azubike.ellipsis.filter.RateLimitFilter;

@Configuration
public class RateLimtFilterConfig {
	@Bean
	FilterRegistrationBean<RateLimitFilter> rateLimitFilterRed() {
		FilterRegistrationBean<RateLimitFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new RateLimitFilter(3));
		registerationBean.addUrlPatterns("/api/dos/v1/red");
		registerationBean.setName("RedRateLimitFilter");
		return registerationBean;

	}

	@Bean
	FilterRegistrationBean<RateLimitFilter> rateLimitFilterBlue() {
		FilterRegistrationBean<RateLimitFilter> registerationBean = new FilterRegistrationBean<>();
		registerationBean.setFilter(new RateLimitFilter(2));
		registerationBean.addUrlPatterns("/api/dos/v1/blue");
		registerationBean.setName("BlueRateLimitFilter");
		return registerationBean;

	}

}
