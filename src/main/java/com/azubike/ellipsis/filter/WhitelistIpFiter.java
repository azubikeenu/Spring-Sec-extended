package com.azubike.ellipsis.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class WhitelistIpFiter extends OncePerRequestFilter {
	private final static String[] IP_LIST = { "0:0:0:0:0:0:0:1", "132.189.8.101" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!ArrayUtils.contains(IP_LIST, request.getRemoteAddr())) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().print("Detected black listed ip " + request.getRemoteAddr());
			return;
		}
		filterChain.doFilter(request, response);

	}

}
