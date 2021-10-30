package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.exception.BaseException;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseHandlerTest {


	private BaseHandler baseHandler;

	@BeforeEach
	void setUp() {
		baseHandler = new BaseHandler();
	}

	@Test
	void verifyBuildErrorResponseEntity() {

		BaseException baseException = new BaseException("mock") {
			@Override
			public HttpStatus httpStatus() {
				return HttpStatus.OK;
			}

			@Override
			public String internalCode() {
				return "internalCode - mock";
			}
		};

		ErrorResponse responseEntity = baseHandler.buildErrorResponseEntity(baseException);

		assertNotNull(responseEntity);
//		assertNotNull(responseEntity.getTraceId()); // TODO: 29/10/2021 Assert this?
		assertEquals(baseException.getMessage(), responseEntity.getMessage());
		assertEquals(baseException.internalCode(), responseEntity.getInternalCode());
	}
}