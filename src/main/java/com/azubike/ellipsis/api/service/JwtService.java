package com.azubike.ellipsis.api.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.azubike.ellipsis.entity.JwtData;

@Service
public class JwtService {
	private static final String JWT_SECRET = "myJwtSecretKey";
	private static final String ISSUER = "apisecurity.com";
	private static final String[] AUDIENCE = { "www.apisecurity.com", "www.apisecurity.net" };
	private static final String SOME_PRIVATE_CLIAM = "just-some-claim-to-be-validated";

	public String store(JwtData jwtData) {
		Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
		LocalDateTime expiresAt = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(15);
		return JWT.create().withSubject(jwtData.getUsername()).withIssuer(ISSUER).withAudience(AUDIENCE)
				.withExpiresAt(Date.from(expiresAt.toInstant(ZoneOffset.UTC)))
				.withClaim("otherDetails", jwtData.getOtherDetails()).withClaim("privateClaim", SOME_PRIVATE_CLIAM)
				.sign(algorithm);
	}

	public Optional<JwtData> read(String jwtToken) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
			JWTVerifier verifier = JWT.require(algorithm).withAnyOfAudience(AUDIENCE).withIssuer(ISSUER)
					.acceptExpiresAt(60).withClaim("privateClaim", SOME_PRIVATE_CLIAM).build();
			DecodedJWT decodedJWT = verifier.verify(jwtToken);
			JwtData jwtData = new JwtData();
			jwtData.setUsername(decodedJWT.getSubject());
			jwtData.setOtherDetails(decodedJWT.getClaim("otherDetails").asString());
			return Optional.of(jwtData);
		} catch (JWTVerificationException ex) {
			ex.printStackTrace();
			return Optional.empty();
		}

	}

}
