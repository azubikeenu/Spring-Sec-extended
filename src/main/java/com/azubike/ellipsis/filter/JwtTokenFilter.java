package com.azubike.ellipsis.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.azubike.ellipsis.api.constants.JwtConstant;
import com.azubike.ellipsis.api.service.JwtService;

public class JwtTokenFilter extends OncePerRequestFilter {
	private JwtService jwtService;

	public JwtTokenFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isValidToken(jwtService, request)) {
			doFilter(request, response, filterChain);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().write("Invalid token");
		}

	}

	private boolean isValidToken(JwtService jwtService, HttpServletRequest request) {
		var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (!StringUtils.startsWithIgnoreCase(authorizationHeader, "Bearer")) {
			return false;
		}

		var bearerToken = StringUtils.substring(authorizationHeader, "Bearer".length() + 1);
		var token = jwtService.read(bearerToken);

		if (token.isPresent()) {
			request.setAttribute(JwtConstant.REQUEST_ATTRIBUTE_USERNAME, token.get().getUsername());
			return true;
		} else {
			return false;
		}

	}

}
