package io.github.samituga.cluedohelperapi.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Represents an error, will provide the information
 * necessary to the consumer.
 */
@JsonPropertyOrder({
      "timestamp",
      "correlation_id",
      "status_code",
      "message",
      "internal_code",
      "sub_errors"
})
@Data
@Builder
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class ErrorResponse {

    @JsonProperty("sub_errors")
    List<SubErrorResponse> subErrors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @NotBlank
    @JsonProperty("correlation_id")
    private String correlationId;
    @NotBlank
    @JsonProperty("message")
    private String message;
    @NotBlank
    @JsonProperty("internal_code")
    private String internalCode;
}
