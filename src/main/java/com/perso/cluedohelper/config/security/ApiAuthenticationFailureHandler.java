package com.perso.cluedohelper.config.security;

import com.perso.cluedohelper.config.errors.ErrorConfig;
import com.perso.cluedohelper.config.errors.ErrorDetail;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import com.perso.cluedohelper.util.ThreadContextWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.perso.cluedohelper.util.ErrorCodeConstants.CH_API_KEY_INVALID;
import static java.util.Objects.requireNonNull;

/**
 * Handles API key authentication related failures
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

		ErrorDetail errorDetail = errorConfig.get(CH_API_KEY_INVALID);

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