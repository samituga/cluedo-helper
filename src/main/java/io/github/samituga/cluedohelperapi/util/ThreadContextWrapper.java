package io.github.samituga.cluedohelperapi.util;

import static io.github.samituga.cluedohelperapi.util.ApiConstants.CORRELATION_ID_KEY;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.ThreadContext;

/**
 * Wrapper class for {@link ThreadContext}.
 */
@UtilityClass
public class ThreadContextWrapper {

  /**
   * Util method to get the correlation id from the {@link ThreadContext}.
   *
   * @return the correlation id for this thread context
   */
  public static String getCorrelationId() {
    return ThreadContext.get(CORRELATION_ID_KEY);
  }


  /**
   * Util method to set the correlation id to the {@link ThreadContext}.
   *
   * @param correlationId the correlation id
   */
  public static void putCorrelationId(String correlationId) {
    ThreadContext.put(CORRELATION_ID_KEY, correlationId);
  }

  /**
   * Util method to remove the correlation id from the {@link ThreadContext}.
   */
  public static void removeCorrelationId() {
    ThreadContext.remove(CORRELATION_ID_KEY);
  }

  /**
   * Util method to set a value to the {@link ThreadContext}.
   *
   * @param key   identifies the value
   * @param value to be stored in the {@link ThreadContext}
   */
  public static void put(String key, String value) {
    ThreadContext.put(key, value);
  }

  /**
   * Util method to remove a value from the {@link ThreadContext}.
   *
   * @param key the key to be removed
   */
  public static void remove(String key) {
    ThreadContext.remove(key);
  }

}
