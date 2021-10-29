package com.perso.cluedohelper.config.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

public class LoggerConfig {


	@Bean
	@Scope(SCOPE_PROTOTYPE)
	Logger logger(InjectionPoint injectionPoint) {

		final MethodParameter methodParameter = injectionPoint.getMethodParameter();

		if (methodParameter != null) {
			return LogManager.getLogger(methodParameter.getContainingClass());
		}

		final Field field = injectionPoint.getField();

		if (field != null) {
			return LogManager.getLogger(field.getDeclaringClass());
		}

		throw new IllegalArgumentException();
	}

}
