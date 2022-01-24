package com.azubike.ellipsis.api.server.util;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.request.util.OriginalRequestString;
import com.azubike.ellipsis.util.EncryptDecryptUtil;

@RestController
@RequestMapping("/api")
public class EncryptDecryptApi {
	// this must be 128(16 chars wide) , 192(24 chars-wide) , 256(32 chars -wide)
	// bits
	public static final String SECRET_KEY = "jW2gH8Ri63KsQuh1";

	@GetMapping(value = "/encrypt/aes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String encrypt(@RequestBody(required = true) OriginalRequestString originalString) throws Exception {
		return EncryptDecryptUtil.encryptAes(originalString.getText(), SECRET_KEY);
	}

	@GetMapping(value = "/decrypt/aes", produces = MediaType.TEXT_PLAIN_VALUE)
	public String decrypt(@RequestParam(required = true, name = "encrypted") String encrypted) throws Exception {
		return EncryptDecryptUtil.decryptAes(encrypted, SECRET_KEY);
	}

}
