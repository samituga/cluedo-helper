package com.perso.cluedohelper.exception.response;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

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
