
package com.azubike.ellipsis.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.bouncycastle.util.encoders.Hex;

public class HashUtil {
	public static String sha256(String orginalString, String salt) throws Exception {
		String orginalWithSalt = StringUtils.join(orginalString, salt);
		var digest = MessageDigest.getInstance("SHA-256");
		byte[] hashedByte = digest.digest(orginalWithSalt.getBytes(StandardCharsets.UTF_8));
		return new String(Hex.encode(hashedByte));
	}

	public static boolean isSha256Match(String orginalString, String salt, String hashValue) throws Exception {
		String hashedOrginal = sha256(orginalString, salt);
		return StringUtils.equals(hashValue, hashedOrginal);
	}

	public static String bcrypt(String orginalString, String salt) {
		return OpenBSDBCrypt.generate(orginalString.getBytes(StandardCharsets.UTF_8),
				salt.getBytes(StandardCharsets.UTF_8), 5);
	}

	public static boolean isBcryptMatch(String original, String hashValue) {
		return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
	}

}
