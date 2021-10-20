package com.perso.cluedohelper.exception.response;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@JsonPropertyOrder({

})
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class ExceptionResponseBody {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	@NotBlank
	@JsonProperty("trace_id")
	private String traceId;

	@NotBlank
	@JsonProperty("status_code")
	private Integer statusCode;

	@NotBlank
	@JsonProperty("message")
	private String message;

	@NotBlank
	@JsonProperty("internal_code")
	private String internalCode;




}
