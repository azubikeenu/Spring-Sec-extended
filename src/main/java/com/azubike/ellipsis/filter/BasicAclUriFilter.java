package com.azubike.ellipsis.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.azubike.ellipsis.api.server.auth.basic.BasicAuthApi;
import com.azubike.ellipsis.entity.BasicAclUri;
import com.azubike.ellipsis.entity.BasicAclUserUriRef;
import com.azubike.ellipsis.entity.BasicAuthUser;
import com.azubike.ellipsis.repository.BasicAclUriRepository;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;
import com.azubike.ellipsis.util.EncodeDecodeUtils;
import com.azubike.ellipsis.util.EncryptDecryptUtil;

//@Configuration
//@Order(1)
public class BasicAclUriFilter extends OncePerRequestFilter {
	@Autowired
	private BasicAuthUserRepository userRepository;

	@Autowired
	private BasicAclUriRepository uriRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String encodedCredentials = StringUtils.substring(authHeader, "Basic".length()).trim();
		String plainCredentials = EncodeDecodeUtils.decodeBase64(encodedCredentials);
		String[] plainAuthorization = plainCredentials.split(":");
		String plainUserName = plainAuthorization[0];
		try {

			String encryptedUserName = EncryptDecryptUtil.encryptAes(plainUserName, BasicAuthApi.SECRET_KEY);
			BasicAuthUser foundUser = userRepository.findByUsername(encryptedUserName).get();
			if (isUrlValid(request.getRequestURI(), request.getMethod(), foundUser)) {
				doFilter(request, response, filterChain);
			} else {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType(MediaType.TEXT_PLAIN_VALUE);
				response.getWriter().write("You are not Authorized to perform this action");
				return;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private boolean isUrlValid(String requestURI, String method, BasicAuthUser foundUser) {
		boolean isValidUrl = false;
		for (BasicAclUserUriRef uri : foundUser.getAllowedUris()) {
			BasicAclUri foundUri = uriRepository.findById(uri.getUriId()).get();
			if (StringUtils.equalsAnyIgnoreCase(foundUri.getMethod(), method)
					&& Pattern.matches(foundUri.getUri(), requestURI)) {
				isValidUrl = true;
			}
		}
		return isValidUrl;
	}

}
