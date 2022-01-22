package com.azubike.ellipsis.api.server.xss;

import java.io.IOException;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.owasp.encoder.Encode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xss/safe/v1")
@CrossOrigin(origins = "*")
@Validated
public class XSSSimpleSafeApi {
	private Tika tika = new Tika();

	@GetMapping(value = "/greeting", produces = MediaType.TEXT_PLAIN_VALUE)
	public String greetUser(
			// @Valid @Pattern(regexp = "[A-Za-z]{4,12}")
			@RequestParam(name = "name", required = true) String name) {
		int hour = LocalTime.now().getHour();
		String greeting = (hour >= 6 && hour < 18) ? "Good morning " + name : "Good night " + name;
		return Encode.forHtml(greeting);
	}

	@GetMapping("/file")
	public ResponseEntity<Resource> downloadFile() throws IOException {
		ClassPathResource resource = new ClassPathResource("static/fileWithXss.csv");
		String contentType = tika.detect(resource.getInputStream());
		if (StringUtils.isBlank(contentType) || StringUtils.equals(contentType, MediaType.TEXT_HTML_VALUE)) {
			contentType = MediaType.TEXT_PLAIN_VALUE;
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
}
