package io.github.samituga.cluedohelperapi.filter;

import static io.github.samituga.cluedohelperapi.annotation.LoggerQualifier.LoggerName.EXTERNAL_REQUEST;

import io.github.samituga.cluedohelperapi.annotation.LoggerQualifier;
import io.github.samituga.cluedohelperapi.util.ApiConstants;
import io.github.samituga.cluedohelperapi.util.ThreadContextWrapper;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter to handle the correlation header, uses the request correlation id if it exists
 * else it will create a new one and dispose it at the end.
 */
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
      final FilterChain chain)
      throws java.io.IOException, ServletException {
    try {
      final String correlationId;
      if (!StringUtils.isEmpty(request.getHeader(ApiConstants.CORRELATION_ID_KEY))) {
        correlationId = request.getHeader(ApiConstants.CORRELATION_ID_KEY);
        logger.info("Using request correlation id: " + correlationId);
      } else {
        correlationId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
      }
      ThreadContextWrapper.putCorrelationId(correlationId);
      response.addHeader(ApiConstants.CORRELATION_ID_KEY, correlationId);
      chain.doFilter(request, response);
    } finally {
      ThreadContextWrapper.removeCorrelationId();
    }
  }
}
