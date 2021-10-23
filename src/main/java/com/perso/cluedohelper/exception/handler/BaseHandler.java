package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.exception.BaseException;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * Abstract class to handle exceptions
 */
class BaseHandler {


	protected ErrorResponse buildErrorResponseEntity(BaseException exception) {

		return ErrorResponse.builder()
			.traceId(null) // TODO: 22/10/2021 Get trace id
			.timestamp(LocalDateTime.now())
			.message(exception.getMessage())
			.internalCode(exception.internalCode())
			.build();
	}
}
