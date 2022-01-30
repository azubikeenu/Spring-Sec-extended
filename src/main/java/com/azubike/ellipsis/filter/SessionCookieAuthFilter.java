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
import com.azubike.ellipsis.api.server.auth.basic.BasicAuthApi;
import com.azubike.ellipsis.entity.BasicAuthUser;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;
import com.azubike.ellipsis.util.EncodeDecodeUtils;
import com.azubike.ellipsis.util.EncryptDecryptUtil;
import com.azubike.ellipsis.util.HashUtil;

public class SessionCookieAuthFilter extends OncePerRequestFilter {
	private BasicAuthUserRepository basicAuthUserRepository;

	public SessionCookieAuthFilter(BasicAuthUserRepository basicAuthUserRepository) {
		this.basicAuthUserRepository = basicAuthUserRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		try {
			if (isBasicAuthValid(authHeader, request)) {
				doFilter(request, response, filterChain);
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.TEXT_PLAIN_VALUE);
				response.getWriter().write("Invalid Credentials");
				return;

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private boolean isBasicAuthValid(String authHeader, HttpServletRequest request) throws Exception {
		if (StringUtils.isBlank(authHeader))
			return false;
		String encodedCredentials = StringUtils.substring(authHeader, "Basic".length()).trim();
		String plainCredentials = EncodeDecodeUtils.decodeBase64(encodedCredentials);
		String[] plainAuthorization = plainCredentials.split(":");
		String plainUserName = plainAuthorization[0];
		String plainPassword = plainAuthorization[1];
		String encryptedUserName = EncryptDecryptUtil.encryptAes(plainUserName, BasicAuthApi.SECRET_KEY);
		Optional<BasicAuthUser> foundUser = basicAuthUserRepository.findByUsername(encryptedUserName);
		if (foundUser.isEmpty()) {
			return false;
		}
		String passwordHash = foundUser.get().getPasswordHash();
		boolean passwordMatch = HashUtil.isBcryptMatch(plainPassword, passwordHash);
		if (passwordMatch) {
			request.setAttribute(SesssionCookieConstant.REQUEST_ATTRIBUTE_USERNAME, encryptedUserName);
			return true;
		} else {
			return false;
		}
	}

}
