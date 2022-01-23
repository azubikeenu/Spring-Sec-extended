package com.azubike.ellipsis.api.server.dos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dos/v1")
public class RedGreenBlueApi {
	private final static Logger LOG = LoggerFactory.getLogger(RedGreenBlueApi.class);

	@GetMapping(value = "/green", produces = MediaType.TEXT_PLAIN_VALUE)
	public String showGreen() {
		LOG.info("green");
		return "green";
	}

	@GetMapping(value = "/blue", produces = MediaType.TEXT_PLAIN_VALUE)
	public String showBlue() {
		LOG.info("blue");
		return "blue";
	}

	@GetMapping(value = "/red", produces = MediaType.TEXT_PLAIN_VALUE)
	public String showRed() {
		LOG.info("red");
		for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
			// simulate a process consuming work
		}
		return "red";
	}

}
