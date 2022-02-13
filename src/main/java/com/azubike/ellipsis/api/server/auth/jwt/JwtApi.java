package com.azubike.ellipsis.api.server.auth.jwt;

import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.constants.JwtConstant;
import com.azubike.ellipsis.api.service.JwtService;
import com.azubike.ellipsis.entity.JwtData;

@RestController
@RequestMapping("/api/auth/jwt/v1")
public class JwtApi {
	@Autowired
	private JwtService jwtService;

	@GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
	public String time(HttpServletRequest request) {
		var encryptedUsername = request.getAttribute(JwtConstant.REQUEST_ATTRIBUTE_USERNAME);

		return "Now is " + LocalTime.now() + ", accessed by " + encryptedUsername;
	}

	@PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
	public String login(HttpServletRequest request) {
		var encryptedUsername = (String) request.getAttribute(JwtConstant.REQUEST_ATTRIBUTE_USERNAME);
		var jwtData = new JwtData();
		jwtData.setUsername(encryptedUsername);
		String token = jwtService.store(jwtData);
		return token;

	}

}
