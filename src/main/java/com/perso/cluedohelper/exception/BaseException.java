package com.perso.cluedohelper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

/**
 * An abstract exception to represent all custom exceptions related to this project
 */
@Validated
public abstract class BaseException extends RuntimeException {

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public abstract HttpStatus httpStatus();

	public abstract String internalCode();
}
