package com.perso.cluedohelper.filter;

import com.perso.cluedohelper.annotation.LoggerQualifier;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.perso.cluedohelper.annotation.LoggerQualifier.LoggerName.EXTERNAL_REQUEST;
import static com.perso.cluedohelper.util.ApiConstants.CORRELATION_ID_KEY;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationFilter extends OncePerRequestFilter {

	private final Logger logger;

	public CorrelationFilter(@LoggerQualifier(loggerName = EXTERNAL_REQUEST) Logger logger) {
		this.logger = logger;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request,
									final HttpServletResponse response,
									final FilterChain chain) throws java.io.IOException, ServletException {
		try {
			final String correlationId;
			if (!StringUtils.isEmpty(request.getHeader(CORRELATION_ID_KEY))) {
				correlationId = request.getHeader(CORRELATION_ID_KEY);
				logger.info("Using request correlation id: " + correlationId);
			} else {
				correlationId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
			}
			ThreadContext.put(CORRELATION_ID_KEY, correlationId);
			response.addHeader(CORRELATION_ID_KEY, correlationId);
			chain.doFilter(request, response);
		} finally {
			ThreadContext.remove(CORRELATION_ID_KEY);
		}
	}
}
