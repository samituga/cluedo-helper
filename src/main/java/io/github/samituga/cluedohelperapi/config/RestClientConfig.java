package io.github.samituga.cluedohelperapi.config;

import static io.github.samituga.cluedohelperapi.annotation.LoggerQualifier.LoggerName.EXTERNAL_REQUEST;

import io.github.samituga.cluedohelperapi.annotation.LoggerQualifier;
import io.github.samituga.cluedohelperapi.interceptor.HttpInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for the {@link RestTemplate}.
 */
@Configuration
public class RestClientConfig {

  /**
   * Construct the {@link RestTemplate} with custom interceptors.
   *
   * @param logger to be used in the interceptor
   * @return the newly constructed {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate(@LoggerQualifier(loggerName = EXTERNAL_REQUEST) Logger logger) {
    RestTemplate restTemplate = new RestTemplate();

    List<ClientHttpRequestInterceptor> interceptors
        = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(new HttpInterceptor(logger));
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }
}
