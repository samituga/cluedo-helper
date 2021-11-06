package io.github.samituga.cluedohelperapi.util;

import lombok.experimental.UtilityClass;

/**
 * Provides the error codes, must be replicated with the errors.yml resources file
 */
@UtilityClass
public class ErrorCodeConstants {

  // 400
  public static final String CH_REQUEST_PARAM_FAILURE = "CH_REQUEST_PARAM_FAILURE";
  public static final String CH_API_KEY_INVALID = "CH_API_KEY_INVALID";
  public static final String CH_RESOURCE_NOT_FOUND = "CH_RESOURCE_NOT_FOUND";

  // 500
  public static final String CH_BASE_ERROR = "CH_BASE_ERROR";
  public static final String CH_ERROR_EXAMPLE = "CH_ERROR_EXAMPLE";
}
