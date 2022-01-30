package com.azubike.ellipsis.api.server.auth.sessioncookie;

import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.constants.SesssionCookieConstant;
import com.azubike.ellipsis.api.service.SessionTokenService;
import com.azubike.ellipsis.entity.SessionCookieToken;
import com.azubike.ellipsis.util.HashUtil;

@RestController
@RequestMapping("/api/auth/session-cookie/v1")
public class SessionCookieApi {
	@Autowired
	private SessionTokenService sessionTokenService;

	@PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request) {
		String encryptedUserName = (String) request.getAttribute(SesssionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);
		SessionCookieToken sessionCookieToken = new SessionCookieToken();
		sessionCookieToken.setUsername(encryptedUserName);
		String sessionId = sessionTokenService.store(request, sessionCookieToken);
		try {
			return HashUtil.sha256(sessionId, encryptedUserName);
		} catch (Exception e) {
			e.printStackTrace();
			return StringUtils.EMPTY;
		}

	}

	@GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
	public String showAcessLogs(HttpServletRequest request) {
		var encryptedUser = request.getAttribute(SesssionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);
		return "The time is " + LocalTime.now() + " accessed by " + encryptedUser;
	}

	@DeleteMapping(value = "/logout", produces = MediaType.TEXT_PLAIN_VALUE)
	public String logout(HttpServletRequest request) {
		sessionTokenService.delete(request);
		return "Logged out";
	}
}
