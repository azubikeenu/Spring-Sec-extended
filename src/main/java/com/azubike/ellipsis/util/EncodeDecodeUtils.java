package com.azubike.ellipsis.util;

import java.nio.charset.StandardCharsets;

import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriUtils;

public class EncodeDecodeUtils {
	public static String encodeBase64(String string) {
		return Base64Utils.encodeToString(string.getBytes(StandardCharsets.UTF_8));
	}

	public static String decodeBase64(String encoded) {
		return new String(Base64Utils.decodeFromString(encoded));
	}

	public static String encodeUrl(String url) {
		return UriUtils.encode(url, StandardCharsets.UTF_8);
	}

	public static String decodeUrl(String encoded) {
		return UriUtils.decode(encoded, StandardCharsets.UTF_8);
	}

}
