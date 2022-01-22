package com.azubike.ellipsis.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class XssRequestFilter extends OncePerRequestFilter {
	private static final String[] XSS_REGEX = {
			"onclick|onkeypress|onkeydown|onkeyup|onerror|onchange|onmouseover|onmouseout|onblur|onselect|onfocus",
			"<\s*script\b[^>]*>(.*?)<\s*/script\b[^>]*>", "script\s+src\s*=", "<\s*script\b[^>]*>",
			"<\s*/script\b[^>]*>", "javascript.*:" };

	private List<Pattern> xssValidationPattern = new ArrayList<>();

	public XssRequestFilter() {
		for (String regex : XSS_REGEX) {
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			xssValidationPattern.add(pattern);
		}
	}

	public boolean isStringSafe(String inputString) {
		if (StringUtils.isBlank(inputString))
			return true;
		for (Pattern pattern : xssValidationPattern) {
			if (pattern.matcher(inputString).find())
				return false;
		}

		return true;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
		String queryString = URLDecoder.decode(
				Optional.ofNullable(cachedRequest.getQueryString()).orElse(StringUtils.EMPTY), StandardCharsets.UTF_8);
		String pathVariable = URLDecoder.decode(
				Optional.ofNullable(cachedRequest.getRequestURI()).orElse(StringUtils.EMPTY), StandardCharsets.UTF_8);
		String requestBody = IOUtils.toString(cachedRequest.getReader()).replaceAll("\\r\\n|\\r|\\n",
				StringUtils.EMPTY);
		if (isStringSafe(queryString) && isStringSafe(requestBody) && isStringSafe(pathVariable))
			filterChain.doFilter(cachedRequest, response);
		else {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("Bad Request XSS Script detected");
			return;
		}

	}

}
