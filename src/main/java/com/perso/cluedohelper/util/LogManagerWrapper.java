package com.perso.cluedohelper.util;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.core.MethodParameter;

/**
 * Wrapper class for the {@link LogManager}.
 */
@UtilityClass
public class LogManagerWrapper {

  /**
   * Util method to get {@link Logger}.
   *
   * @param name logger name
   * @return a {@link Logger} for the respective name
   */
  public static Logger getLogger(String name) {
    return LogManager.getLogger(name);
  }


  /**
   * Util method to get {@link Logger}.
   *
   * @param injectionPoint point in code where the logger is being injected
   * @return a {@link Logger} for the respective name
   */
  public static Logger getLogger(InjectionPoint injectionPoint) {
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

  /**
   * Util method to get {@link Logger}.
   *
   * @param joinPoint point in code where the logger is being injected
   * @return a {@link Logger} for the respective name
   */
  public static Logger getLogger(JoinPoint joinPoint) {

    Signature signature = joinPoint.getSignature();

    if (isNull(signature) || isNull(signature.getDeclaringType())) {
      throw new IllegalArgumentException();
    }

    return LogManager.getLogger(signature.getDeclaringType().getName());
  }
}
