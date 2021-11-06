package io.github.samituga.cluedohelperapi.config.errors;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Representation of an error.
 */
@JsonPropertyOrder({
    "code",
    "message",
    "http_code"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private HttpStatus httpCode;

  /**
   * Converts the {@code httpCode} to {@link HttpStatus}.
   *
   * @param httpCode HTTP status code related to this error
   */
  @JsonProperty("http_code")
  public void setHttpCode(final Integer httpCode) {
    HttpStatus httpStatus = HttpStatus.resolve(httpCode);
    this.httpCode = requireNonNull(httpStatus);
  }
}
