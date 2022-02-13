package com.azubike.ellipsis.api.server.auth.redis;

import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.constants.RedisConstant;
import com.azubike.ellipsis.api.constants.SesssionCookieConstant;
import com.azubike.ellipsis.api.service.RedisTokenService;
import com.azubike.ellipsis.entity.RedisToken;

@RestController
@RequestMapping("/api/auth/redis/v1")
public class RedisApi {
	@Autowired
	private RedisTokenService redisTokenService;

	@GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
	public String showAcessLogs(HttpServletRequest request) {
		var encryptedUser = request.getAttribute(SesssionCookieConstant.REQUEST_ATTRIBUTE_USERNAME);
		return "The time is " + LocalTime.now() + " accessed by " + encryptedUser;
	}

	@PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request) {
		String encryptedUserName = (String) request.getAttribute(RedisConstant.REQUEST_ATTRIBUTE_USERNAME);
		RedisToken redisToken = new RedisToken();
		redisToken.setUsername(encryptedUserName);
		String tokenId = redisTokenService.store(redisToken);
		return tokenId;
	}

}
