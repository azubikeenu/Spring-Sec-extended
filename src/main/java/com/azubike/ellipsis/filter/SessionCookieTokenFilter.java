package com.azubike.ellipsis.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.azubike.ellipsis.api.constants.SesssionCookieConstant;
import com.azubike.ellipsis.api.service.SessionTokenService;
import com.azubike.ellipsis.entity.SessionCookieToken;

public class SessionCookieTokenFilter extends OncePerRequestFilter {
	private SessionTokenService sessionTokenService;

	public SessionCookieTokenFilter(SessionTokenService sessionTokenService) {
		this.sessionTokenService = sessionTokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isValidToken(sessionTokenService, request)) {
			doFilter(request, response, filterChain);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().write("Forbidden resource");
		}

	}

	private boolean isValidToken(SessionTokenService tokenService, HttpServletRequest request) {
		String providedToken = request.getHeader("X-CSRF");
		Optional<SessionCookieToken> token = tokenService.read(request, providedToken);
		if (token.isEmpty()) {
			return false;
		} else {
			SessionCookieToken sessionCookieToken = token.get();
			// store in the request object for subsequent calls
			request.setAttribute(SesssionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, sessionCookieToken.getUsername());
			return true;
		}

	}

}
