package com.perso.cluedohelper.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ImpossibleMoveException extends BaseException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String INTERNAL_CODE = "CH_1";

	public ImpossibleMoveException(String message) {
		super(message);
	}

	public ImpossibleMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImpossibleMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public HttpStatus httpStatus() {
		return HTTP_STATUS;
	}

	@Override
	public String internalCode() {
		return INTERNAL_CODE;
	}
}
