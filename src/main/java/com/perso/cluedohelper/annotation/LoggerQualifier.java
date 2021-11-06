package com.perso.cluedohelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * A qualifier used to create a bean of type {@link org.apache.logging.log4j.Logger}
 * when we don't want it to be linked with a specific class.
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface LoggerQualifier {

  /**
   * Name to be givven to the {@link org.apache.logging.log4j.Logger}.
   *
   * @return a {@link LoggerName LoggerName} value
   */
  LoggerName loggerName();

  /**
   * Enum to store the possible names for the logger.
   */
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