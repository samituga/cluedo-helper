package com.perso.cluedohelper.api.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A qualifier used to create a bean of type {@link org.apache.logging.log4j.Logger}
 * when we don't want it to be linked with a specific class
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface LoggerQualifier {

	LoggerName loggerName();

	enum LoggerName {
		EXTERNAL_REQUEST("ExternalRequest");

		private final String toStringValue;

		LoggerName(String toStringValue) {
			this.toStringValue = toStringValue;
		}

		@Override
		public String toString() {
			return toStringValue;
		}
	}
}