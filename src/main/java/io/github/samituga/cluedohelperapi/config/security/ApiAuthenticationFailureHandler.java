package io.github.samituga.cluedohelperapi.config.security;

import static java.util.Objects.requireNonNull;

import io.github.samituga.cluedohelperapi.config.errors.ErrorConfig;
import io.github.samituga.cluedohelperapi.config.errors.ErrorDetail;
import io.github.samituga.cluedohelperapi.exception.response.ErrorResponse;
import io.github.samituga.cluedohelperapi.util.ErrorCodeConstants;
import io.github.samituga.cluedohelperapi.util.ThreadContextWrapper;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Handles API key authentication related failures.
 */
@Component
@RequiredArgsConstructor
public class ApiAuthenticationFailureHandler implements AuthenticationEntryPoint {

  private final MappingJackson2HttpMessageConverter messageConverter;
  private final ErrorConfig errorConfig;

  @Override
  public void commence(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AuthenticationException exception) throws IOException {

    ErrorDetail errorDetail = errorConfig.get(ErrorCodeConstants.CH_API_KEY_INVALID);

    ErrorResponse errorResponse = ErrorResponse.builder()
        .internalCode(errorDetail.getCode())
        .message(errorDetail.getMessage())
        .correlationId(ThreadContextWrapper.getCorrelationId())
        .timestamp(LocalDateTime.now())
        .build();

    final ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
    outputMessage.setStatusCode(requireNonNull(HttpStatus.FORBIDDEN));

    messageConverter.write(errorResponse, MediaType.APPLICATION_JSON, outputMessage);
  }
}