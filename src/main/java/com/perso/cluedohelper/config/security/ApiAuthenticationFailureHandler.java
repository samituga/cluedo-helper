package com.perso.cluedohelper.config.security;

import com.perso.cluedohelper.exception.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
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

import static com.perso.cluedohelper.util.ApiConstants.CORRELATION_ID_KEY;
import static java.util.Objects.requireNonNull;

/**
 * Handles API key authentication related failures
 */
@Component
@RequiredArgsConstructor
public class ApiAuthenticationFailureHandler implements AuthenticationEntryPoint {

	private final MappingJackson2HttpMessageConverter messageConverter;

	@Override
	public void commence(final HttpServletRequest request,
						 final HttpServletResponse response,
						 final AuthenticationException exception) throws IOException {

		ErrorResponse errorResponse = ErrorResponse.builder() // TODO: 01/11/2021 Get the information for this fields
			.internalCode("internalCode")
			.message("message")
			.correlationId(ThreadContext.get(CORRELATION_ID_KEY))
			.timestamp(LocalDateTime.now())
			.build();

		final ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
		outputMessage.setStatusCode(requireNonNull(HttpStatus.FORBIDDEN));

		messageConverter.write(errorResponse, MediaType.APPLICATION_JSON, outputMessage);
	}
}