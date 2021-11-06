package io.github.samituga.cluedohelperapi.exception;

import javax.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

/**
 * An abstract exception to represent all custom exceptions related to this project.
 */
@Validated
public abstract class BaseException extends RuntimeException {

  @NotEmpty
  private final String correlationId;

  public BaseException(String message, String correlationId) {
    super(message);
    this.correlationId = correlationId;
  }

  public BaseException(String message, Throwable cause, String correlationId) {
    super(message, cause);
    this.correlationId = correlationId;
  }

  public BaseException(String message, Throwable cause, boolean enableSuppression,
                       boolean writableStackTrace, String correlationId) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.correlationId = correlationId;
  }


  public String getCorrelationId() {
    return correlationId;
  }

  public abstract HttpStatus httpStatus();

  public abstract String internalCode();
}
