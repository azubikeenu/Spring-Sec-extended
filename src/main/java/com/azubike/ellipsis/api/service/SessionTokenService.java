package com.azubike.ellipsis.api.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.azubike.ellipsis.api.constants.SesssionCookieConstant;
import com.azubike.ellipsis.entity.SessionCookieToken;
import com.azubike.ellipsis.util.HashUtil;
import com.azubike.ellipsis.util.SecureStringUtil;

@Service
public class SessionTokenService {
	// this persists the encrypted user details in the session object on successful
	// login
	public String store(HttpServletRequest request, SessionCookieToken sessionCookieToken) {
		HttpSession session = request.getSession(false);
		// destroy currently stored sessions
		if (session != null) {
			session.invalidate();
		}
		session = request.getSession(true);
		session.setAttribute(SesssionCookieConstant.SESSION_ATTRIBUTE_USERNAME, sessionCookieToken.getUsername());
		return session.getId();
	}

	// this is used to validate request token headers and session token
	public Optional<SessionCookieToken> read(HttpServletRequest request, String provideToken) {
		var session = request.getSession(false);
		if (session == null) {
			// there is no session
			return Optional.empty();
		} else {
			// get the user credentials from session object
			String username = (String) session.getAttribute(SesssionCookieConstant.SESSION_ATTRIBUTE_USERNAME);
			try {
				// hash the token
				String computedToken = HashUtil.sha256(session.getId(), username);
				if (!SecureStringUtil.compareStrings(provideToken, computedToken)) {
					// if the token provided is different from the stored token , invalidate
					return Optional.empty();
				}
				SessionCookieToken token = new SessionCookieToken();
				token.setUsername(username);
				return Optional.of(token);
			} catch (Exception e) {
				e.printStackTrace();
				return Optional.empty();
			}

		}
	}

	public void delete(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
	}
}
