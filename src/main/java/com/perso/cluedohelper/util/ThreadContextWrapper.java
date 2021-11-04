package com.perso.cluedohelper.util;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.ThreadContext;

import static com.perso.cluedohelper.util.ApiConstants.CORRELATION_ID_KEY;

/**
 * Wrapper class for {@link ThreadContext}
 */
@UtilityClass
public class ThreadContextWrapper {

	/**
	 * @return the correlation id for this thread context
	 */
	public static String getCorrelationId() {
		return ThreadContext.get(CORRELATION_ID_KEY);
	}

	public static void putCorrelationId(String value) {
		ThreadContext.put(CORRELATION_ID_KEY, value);
	}

	public static void removeCorrelationId() {
		ThreadContext.remove(CORRELATION_ID_KEY);
	}

	public static void put(String key, String value) {
		ThreadContext.put(key, value);
	}

	public static void remove(String key) {
		ThreadContext.remove(key);
	}

}
