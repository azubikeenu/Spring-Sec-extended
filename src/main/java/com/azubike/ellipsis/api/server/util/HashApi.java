package com.azubike.ellipsis.api.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.request.util.OriginalRequestString;
import com.azubike.ellipsis.util.HashUtil;
import com.azubike.ellipsis.util.SecureStringUtil;

@RestController
@RequestMapping("/api")
public class HashApi {
	private static final int SALT_LENGTH = 16;
	// store the salt for sha256 in memory
	Map<String, String> salts = new HashMap<>();

	@GetMapping(value = "/hash/sha256", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hashSha256(@RequestBody(required = true) OriginalRequestString originalString) throws Exception {
		String salt = SecureStringUtil.generateRandomString(SALT_LENGTH);
		salts.put(originalString.getText(), salt);
		return HashUtil.sha256(originalString.getText(), salt);
	}

	@GetMapping(value = "/hash/sha256/match", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String compareSha256(@RequestHeader(name = "X-hash", required = true) String hashValue,
			@RequestBody(required = true) OriginalRequestString originalRequestString) throws Exception {
		String salt = Optional.ofNullable(salts.get(originalRequestString.getText())).orElse(StringUtils.EMPTY);
		return (HashUtil.isSha256Match(originalRequestString.getText(), salt, hashValue)) ? "true" : "false";

	}

	@GetMapping(value = "/hash/bcrypt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String hashBcrypt(@RequestBody(required = true) OriginalRequestString originalString) throws Exception {
		String salt = SecureStringUtil.generateRandomString(SALT_LENGTH);
		salts.put(originalString.getText(), salt);
		return HashUtil.bcrypt(originalString.getText(), salt);
	}

	@GetMapping(value = "/hash/bcrypt/match", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String compareBcrypt(@RequestHeader(name = "X-hash", required = true) String hashValue,
			@RequestBody(required = true) OriginalRequestString originalRequestString) throws Exception {
		return (HashUtil.isBcryptMatch(originalRequestString.getText(), hashValue)) ? "true" : "false";

	}

}
