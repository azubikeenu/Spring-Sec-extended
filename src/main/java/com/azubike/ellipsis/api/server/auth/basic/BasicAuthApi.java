package com.azubike.ellipsis.api.server.auth.basic;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.request.auth.basic.BasicAuthRequest;
import com.azubike.ellipsis.entity.BasicAuthUser;
import com.azubike.ellipsis.repository.BasicAuthUserRepository;
import com.azubike.ellipsis.util.EncryptDecryptUtil;
import com.azubike.ellipsis.util.HashUtil;
import com.azubike.ellipsis.util.SecureStringUtil;

@RestController
@RequestMapping("/api/auth/basic/v1")
public class BasicAuthApi {
	public static final String SECRET_KEY = "TheSecretKey2468";
	@Autowired
	private BasicAuthUserRepository basicAuthUserRepository;

	@GetMapping(value = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getCurrentTime() {
		return "The current time is " + LocalTime.now().toString();
	}

	@PostMapping(value = "/user", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<String> createUser(@RequestBody(required = true) BasicAuthRequest basicAuthRequest)
			throws Exception {
		BasicAuthUser user = new BasicAuthUser();
		user.setDisplayName(basicAuthRequest.getDisplayName());
		String encryptedUserName = EncryptDecryptUtil.encryptAes(basicAuthRequest.getUsername(), SECRET_KEY);
		String randomSalt = SecureStringUtil.generateRandomString(16);
		user.setSalt(randomSalt);
		String hashedPassword = HashUtil.bcrypt(basicAuthRequest.getPassword(), randomSalt);
		user.setPasswordHash(hashedPassword);
		user.setUsername(encryptedUserName);
		BasicAuthUser savedUser = basicAuthUserRepository.save(user);
		return (savedUser != null) ? ResponseEntity.status(HttpStatus.CREATED).body("user successfully created")
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
	}

}
