package com.perso.cluedohelper.api.exception.handler;

import com.perso.cluedohelper.api.config.errors.ErrorConfig;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler extends BaseHandler {

	public BusinessExceptionHandler(ErrorConfig errorConfig) {
		super(errorConfig);
	}
}
