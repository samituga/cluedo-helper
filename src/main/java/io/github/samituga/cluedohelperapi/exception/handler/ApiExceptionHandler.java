package io.github.samituga.cluedohelperapi.exception.handler;

import io.github.samituga.cluedohelperapi.config.errors.ErrorConfig;
import io.github.samituga.cluedohelperapi.config.errors.ErrorDetail;
import io.github.samituga.cluedohelperapi.exception.response.ErrorResponse;
import io.github.samituga.cluedohelperapi.util.ErrorCodeConstants;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Handler for API related exceptions.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends BaseHandler {

  public ApiExceptionHandler(ErrorConfig errorConfig) {
    super(errorConfig);
  }

  /**
   * Handles {@link NoHandlerFoundException}.
   *
   * @param ex the exception to handle
   * @return An {@link ResponseEntity} with the information about the error
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> noHandlerFoundHandler(final NoHandlerFoundException ex) {
    final ErrorDetail errorDetail = errorConfig.get(ErrorCodeConstants.CH_RESOURCE_NOT_FOUND);
    final String message = errorDetail.getMessage().concat(": ").concat(ex.getRequestURL());
    final ErrorResponse error = buildErrorResponse(errorDetail, message);
    return ResponseEntity.status(errorDetail.getHttpCode()).body(error);
  }

  /**
   * Handles {@link MissingServletRequestParameterException}.
   *
   * @return An {@link ResponseEntity} with the information about the error
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      final MissingServletRequestParameterException ex) {
    final ErrorDetail errorDetail = errorConfig.get(ErrorCodeConstants.CH_REQUEST_PARAM_FAILURE);
    final ErrorResponse error = buildErrorResponse(errorDetail, ex.getLocalizedMessage());
    return ResponseEntity.status(errorDetail.getHttpCode()).body(error);
  }
}
