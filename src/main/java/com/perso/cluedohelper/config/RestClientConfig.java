package com.perso.cluedohelper.config;

import com.perso.cluedohelper.annotation.LoggerQualifier;
import com.perso.cluedohelper.interceptor.HttpInterceptor;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.perso.cluedohelper.annotation.LoggerQualifier.LoggerName.EXTERNAL_REQUEST;

/**
 * Configuration class for the {@link RestTemplate}
 */
@Configuration
public class RestClientConfig {

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
