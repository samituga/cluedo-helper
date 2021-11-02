package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.config.errors.ErrorConfig;
import com.perso.cluedohelper.config.errors.ErrorDetail;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import com.perso.cluedohelper.util.ThreadContextWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static com.perso.cluedohelper.util.ErrorCodeConstants.CH_BASE_ERROR;

/**
 * Abstract class to handle exceptions
 */
@RequiredArgsConstructor
class BaseHandler {

	protected final ErrorConfig errorConfig;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(final Exception e) {
		final ErrorResponse error = buildErrorResponseEntity(errorConfig.get(CH_BASE_ERROR));
		return ResponseEntity.status(errorConfig.get(CH_BASE_ERROR).getHttpCode()).body(error);
	}

	/**
	 * Builds a {@link ErrorResponse} from a {@link ErrorDetail}
	 *
	 * @param errorDetail The error detail to be mapped
	 * @return an {@link ErrorResponse} built from the given exception
	 */
	protected ErrorResponse buildErrorResponseEntity(ErrorDetail errorDetail) {
		return buildErrorResponseEntity(errorDetail, errorDetail.getMessage());
	}

	/**
	 * Builds a {@link ErrorResponse} from a {@link ErrorDetail}
	 *
	 * @param errorDetail The error detail to be mapped
	 * @param message     Custom message
	 * @return an {@link ErrorResponse} built from the given exception
	 */
	protected ErrorResponse buildErrorResponseEntity(ErrorDetail errorDetail, String message) {

		return ErrorResponse.builder()
			.correlationId(ThreadContextWrapper.getCorrelationId())
			.timestamp(LocalDateTime.now())
			.message(message)
			.internalCode(errorDetail.getCode())
			.build();
	}
}
