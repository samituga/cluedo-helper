package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.exception.ImpossibleMoveException;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class BaseHandlerTest {


	private BaseHandler baseHandler;

	@BeforeEach
	void setUp() {
		baseHandler = new BaseHandler();
	}


	@Test
	void verifyBuildErrorResponseEntity() {

		ImpossibleMoveException impossibleMoveException = new ImpossibleMoveException("mock");

		ErrorResponse responseEntity = baseHandler.buildErrorResponseEntity(impossibleMoveException);

		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getTraceId()); // TODO: 22/10/2021 get trace id
		assertEquals(impossibleMoveException.getMessage(), responseEntity.getMessage());
		assertEquals(impossibleMoveException.internalCode(), responseEntity.getInternalCode());
	}
}