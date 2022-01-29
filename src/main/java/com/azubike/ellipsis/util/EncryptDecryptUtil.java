package com.azubike.ellipsis.util;

import java.nio.charset.StandardCharsets;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class EncryptDecryptUtil {
	private static final String ALGORITHM = "AES";
	private static final String AES_TRANSFORMATION = "AES/CBC/PKCS7Padding";
	// IV and secret credentials in production should be placed in secure storage
	// services like hashicorp vault(It should be 16 characters)
	private static final String IV = "zu1Lx26DMDn81BFi";

	private static IvParameterSpec ivParameterSpec;
	private static Cipher cipher;

	static {
		Security.addProvider(new BouncyCastleProvider());
		ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
		try {
			cipher = Cipher.getInstance(AES_TRANSFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encryptAes(String originalText, String secretKey) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(originalText.getBytes(StandardCharsets.UTF_8));
		// encode the encrypted bytes in Hex format
		return new String(Hex.encode(encryptedBytes));

	}

	public static String decryptAes(String encryptedText, String secretKey) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encryptedBytes = Hex.decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes);
	}
}
