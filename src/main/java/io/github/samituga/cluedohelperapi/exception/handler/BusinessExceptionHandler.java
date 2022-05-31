package io.github.samituga.cluedohelperapi.exception.handler;

import io.github.samituga.cluedohelperapi.config.errors.ErrorConfig;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler for business related exceptions.
 */
@RestControllerAdvice
public class BusinessExceptionHandler extends BaseHandler {

    public BusinessExceptionHandler(ErrorConfig errorConfig) {
        super(errorConfig);
    }
}
