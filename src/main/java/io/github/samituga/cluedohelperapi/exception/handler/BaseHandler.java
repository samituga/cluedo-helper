package io.github.samituga.cluedohelperapi.exception.handler;

import io.github.samituga.cluedohelperapi.config.errors.ErrorConfig;
import io.github.samituga.cluedohelperapi.config.errors.ErrorDetail;
import io.github.samituga.cluedohelperapi.exception.response.ErrorResponse;
import io.github.samituga.cluedohelperapi.util.ErrorCodeConstants;
import io.github.samituga.cluedohelperapi.util.ThreadContextWrapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Abstract class to handle exceptions.
 */
@RequiredArgsConstructor
class BaseHandler {

  protected final ErrorConfig errorConfig;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> exceptionHandler(final Exception e) {
    final ErrorResponse error =
        buildErrorResponse(errorConfig.get(ErrorCodeConstants.CH_BASE_ERROR));
    return ResponseEntity.status(errorConfig.get(ErrorCodeConstants.CH_BASE_ERROR).getHttpCode())
        .body(error);
  }

  /**
   * Builds a {@link ErrorResponse} from a {@link ErrorDetail}.
   *
   * @param errorDetail The error detail to be mapped
   * @return an {@link ErrorResponse} built from the given exception
   */
  protected ErrorResponse buildErrorResponse(ErrorDetail errorDetail) {
    return buildErrorResponse(errorDetail, errorDetail.getMessage());
  }

  /**
   * Builds a {@link ErrorResponse} from a {@link ErrorDetail}.
   *
   * @param errorDetail The error detail to be mapped
   * @param message     Custom message
   * @return an {@link ErrorResponse} built from the given exception
   */
  protected ErrorResponse buildErrorResponse(ErrorDetail errorDetail, String message) {

    return ErrorResponse.builder()
        .correlationId(ThreadContextWrapper.getCorrelationId())
        .timestamp(LocalDateTime.now())
        .message(message)
        .internalCode(errorDetail.getCode())
        .build();
  }
}
