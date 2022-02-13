package com.azubike.ellipsis.api.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azubike.ellipsis.entity.RedisToken;
import com.azubike.ellipsis.util.SecureStringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.api.StatefulRedisConnection;

@Service
public class RedisTokenService {
	@Autowired
	private StatefulRedisConnection<String, String> statefulRedisConnection;
	@Autowired
	private ObjectMapper objectMapper;

	public String store(RedisToken redisToken) {
		try {
			String tokenId = SecureStringUtil.generateRandomString(30);
			String jsonToken = objectMapper.writeValueAsString(redisToken);
			statefulRedisConnection.sync().set(tokenId, jsonToken);
			statefulRedisConnection.sync().expire(tokenId, 15 * 60);
			return tokenId;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
	}

	public Optional<RedisToken> read(String tokenId) {
		String jsonToken = statefulRedisConnection.sync().get(tokenId);
		if (StringUtils.isBlank(jsonToken)) {
			return Optional.empty();
		}
		try {
			RedisToken redisToken = objectMapper.readValue(jsonToken, RedisToken.class);
			return Optional.of(redisToken);
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public void deleteToken(String tokenId) {
		statefulRedisConnection.sync().del(tokenId);
	}

}
