package com.azubike.ellipsis.exception.handler;

import java.sql.SQLException;

import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
	private static final Logger LOG = Logger.getLogger(ApiExceptionHandler.class);
	private static final ApiErrorMessage GENERIC_MESSAGE = new ApiErrorMessage("Sorry something went wrong");

	@ExceptionHandler({ SQLException.class })
	public ResponseEntity<ApiErrorMessage> sqlExceptionHandler(SQLException ex) {
		LOG.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(GENERIC_MESSAGE);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<ApiErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
		LOG.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(GENERIC_MESSAGE);

	}

}
