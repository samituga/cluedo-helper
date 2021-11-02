package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.config.errors.ErrorConfig;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.perso.cluedohelper.util.ErrorCodeConstants.CH_RESOURCE_NOT_FOUND;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends BaseHandler {

	public ApiExceptionHandler(ErrorConfig errorConfig) {
		super(errorConfig);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> noHandlerFoundHandler(final NoHandlerFoundException e) {
		final String message = errorConfig.get(CH_RESOURCE_NOT_FOUND).getMessage().concat(": ").concat(e.getRequestURL());
		final ErrorResponse error = buildErrorResponseEntity(errorConfig.get(CH_RESOURCE_NOT_FOUND), message);
		return ResponseEntity.status(errorConfig.get(CH_RESOURCE_NOT_FOUND).getHttpCode()).body(error);
	}
}
