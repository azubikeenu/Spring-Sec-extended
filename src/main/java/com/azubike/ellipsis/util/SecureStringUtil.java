package com.azubike.ellipsis.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class SecureStringUtil {
	private static final String SEED_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static SecureRandom SECURE_RANDOM;

	static {
		try {
			SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static final String generateRandomString(int length) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = SECURE_RANDOM.nextInt(SEED_STRING.length());
			output.append(SEED_STRING.charAt(index));
		}
		return output.toString();
	}

	public static final boolean compareStrings(String s1, String s2) {
		return MessageDigest.isEqual(s1.getBytes(StandardCharsets.UTF_8), s2.getBytes(StandardCharsets.UTF_8));
	}

}
