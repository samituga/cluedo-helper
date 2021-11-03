package com.perso.cluedohelper.api.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@JsonPropertyOrder({
	"title",
	"description"
})
@Data
@Builder
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class SubErrorResponse {

	@NotBlank
	@JsonProperty("title")
	private String title;

	@NotBlank
	@JsonProperty("description")
	private String description;
}
