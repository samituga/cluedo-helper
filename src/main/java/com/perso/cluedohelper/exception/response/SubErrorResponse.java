package com.perso.cluedohelper.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Contains additional info about an error.
 */
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
