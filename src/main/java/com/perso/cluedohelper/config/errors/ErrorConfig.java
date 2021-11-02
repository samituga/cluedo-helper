package com.perso.cluedohelper.config.errors;

import com.perso.cluedohelper.config.YamlPropertySourceFactory;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Configuration class to load errors details from a resource file
 */
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cluedohelper")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:errors.yml")
public class ErrorConfig {

	private Map<String, ErrorDetail> errors;

	/**
	 * Getter for the {@link ErrorDetail error}
	 *
	 * @param errorCode The code of the error
	 * @return the {@link ErrorDetail}
	 */
	public ErrorDetail get(final String errorCode) {
		ErrorDetail errorDetail = errors.get(errorCode);
		if (isNull(errorDetail)) {
			throw new IllegalArgumentException();
		}
		return errorDetail;
	}
}
