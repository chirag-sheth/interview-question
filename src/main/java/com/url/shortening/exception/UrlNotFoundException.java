package com.url.shortening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrlNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4734769413243169951L;

	public UrlNotFoundException(String message) {
		super(message);
	}
}