package com.azubike.ellipsis.api.server.xss;

import java.time.LocalTime;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xss/danger/v1")
@CrossOrigin(origins = "*")
public class XSSSimpleDangerApi {
	@GetMapping(value = "/greeting")
	public String greetUser(@RequestParam(name = "name", required = true) String name) {
		int hour = LocalTime.now().getHour();
		return (hour >= 6 && hour < 18) ? "Good morning " + name : "Good night " + name;
	}

	@GetMapping("/file")
	public Resource downloadFile() {
		ClassPathResource resource = new ClassPathResource("static/fileWithXss.csv");
		return resource;
	}
}
