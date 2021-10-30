package com.perso.cluedohelper.config.logger;

import com.perso.cluedohelper.annotation.LoggerQualifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;

import static com.perso.cluedohelper.annotation.LoggerQualifier.LoggerName.EXTERNAL_REQUEST;
import static java.util.Objects.nonNull;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * Configuration class to facilitate the injection of a {@link Logger} bean
 */
@Configuration
public class LoggerConfig {

	/**
	 * {@link Logger} bean to be injected
	 *
	 * @param injectionPoint the point in code where the {@link Logger} is being injected
	 * @return A {@link Logger} for the respective class
	 */
	@Bean
	@Scope(SCOPE_PROTOTYPE)
	Logger logger(InjectionPoint injectionPoint) {

		final MethodParameter methodParameter = injectionPoint.getMethodParameter();

		if (nonNull(methodParameter)) {
			return LogManager.getLogger(methodParameter.getContainingClass());
		}

		final Field field = injectionPoint.getField();

		if (nonNull(field)) {
			return LogManager.getLogger(field.getDeclaringClass());
		}

		throw new IllegalArgumentException();
	}

	@Bean
	@LoggerQualifier(loggerName = EXTERNAL_REQUEST)
	public Logger externalRequestLogger() {
		return LogManager.getLogger(EXTERNAL_REQUEST.toString());
	}
}
