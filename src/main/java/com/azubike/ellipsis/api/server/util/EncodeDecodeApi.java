package com.azubike.ellipsis.api.server.util;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.request.util.OriginalRequestString;
import com.azubike.ellipsis.util.EncodeDecodeUtils;

@RestController
@RequestMapping("/api")
@Validated
public class EncodeDecodeApi {

	@PostMapping(value = "/encode/base64", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String base64encode(@RequestBody(required = true) OriginalRequestString originalRequestString) {
		return EncodeDecodeUtils.encodeBase64(originalRequestString.getText());
	}

	@GetMapping(value = "/decode/base64", produces = MediaType.TEXT_PLAIN_VALUE)
	public String base64decode(@RequestParam(required = true, name = "encoded") String encoded) {
		return EncodeDecodeUtils.decodeBase64(encoded);
	}

	@PostMapping(value = "/encode/url", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String encodeUrl(@RequestBody OriginalRequestString originalRequestString) {
		return EncodeDecodeUtils.encodeUrl(originalRequestString.getText());
	}

	@GetMapping(value = "/decode/url", produces = MediaType.TEXT_PLAIN_VALUE)
	public String decodeUrl(@RequestParam(required = true, name = "encoded") String encoded) {
		return EncodeDecodeUtils.decodeUrl(encoded);
	}

}
