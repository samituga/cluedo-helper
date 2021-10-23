package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.exception.ImpossibleMoveException;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BusinessExceptionHandler extends BaseHandler {


	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ImpossibleMoveException.class)
	public ResponseEntity<ErrorResponse> handleImpossibleMoveException(ImpossibleMoveException exception) {
		ErrorResponse response = buildErrorResponseEntity(exception);
		return ResponseEntity
			.badRequest()
			.body(response);
	}
}
