package com.azubike.ellipsis.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.azubike.ellipsis.api.constants.SesssionCookieConstant;
import com.azubike.ellipsis.api.service.RedisTokenService;
import com.azubike.ellipsis.entity.RedisToken;

public class RedisTokenFilter extends OncePerRequestFilter {
	private RedisTokenService redisTokenService;

	public RedisTokenFilter(RedisTokenService redisTokenService) {
		this.redisTokenService = redisTokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isValidToken(redisTokenService, request)) {
			doFilter(request, response, filterChain);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().write("Forbidden resource");
		}

	}

	private boolean isValidToken(RedisTokenService tokenService, HttpServletRequest request) {
		var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.startsWithIgnoreCase(authorizationHeader, "Bearer")) {
			return false;
		}
		var bearerToken = StringUtils.substring(authorizationHeader, "Bearer".length() + 1);
		Optional<RedisToken> token = tokenService.read(bearerToken);
		if (token.isEmpty()) {
			return false;
		} else {
			RedisToken redisToken = token.get();
			// store in the request object for subsequent calls
			request.setAttribute(SesssionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, redisToken.getUsername());
			return true;
		}

	}

}
