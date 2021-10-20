package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.exception.ImpossiblePlayException;
import com.perso.cluedohelper.exception.response.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.perso.cluedohelper.controller")
public class ApiExceptionHandler extends AbstractHandler {


	@ExceptionHandler(value = ImpossiblePlayException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The play is impossible")
	public ResponseEntity<ExceptionResponseBody> handleImpossiblePlayException() {

		return new ResponseEntity<>(null);
	}

}
