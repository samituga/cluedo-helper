package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.exception.BaseException;
import com.perso.cluedohelper.exception.response.ErrorResponse;

import java.time.LocalDateTime;

/**
 * Abstract class to handle exceptions
 */
class BaseHandler {


	/**
	 * Builds a {@link ErrorResponse} from a {@link BaseException}
	 *
	 * @param exception The exception to be transformed
	 * @return an {@link ErrorResponse} built from the given exception
	 */
	protected ErrorResponse buildErrorResponseEntity(BaseException exception) {

		return ErrorResponse.builder()
			.correlationId(exception.getCorrelationId())
			.timestamp(LocalDateTime.now())
			.message(exception.getMessage())
			.internalCode(exception.internalCode())
			.build();
	}
}
