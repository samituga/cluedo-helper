package com.perso.cluedohelper.exception.handler;

import com.perso.cluedohelper.config.errors.ErrorDetail;
import com.perso.cluedohelper.exception.response.ErrorResponse;
import com.perso.cluedohelper.util.ThreadContextWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseHandlerTest {


	private BaseHandler baseHandler;

	@BeforeEach
	void setUp() {
		baseHandler = new BaseHandler(null);
	}

	@Test
	void verifyBuildErrorResponseEntity() {

		ErrorDetail errorDetail = new ErrorDetail("code", "message", HttpStatus.I_AM_A_TEAPOT);
		ThreadContextWrapper.putCorrelationId("mock");

		ErrorResponse responseEntity = baseHandler.buildErrorResponse(errorDetail);

		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getCorrelationId());
		assertFalse(responseEntity.getCorrelationId().isEmpty());
		assertThat(responseEntity.getMessage(), equalTo(errorDetail.getMessage()));
		assertThat(responseEntity.getInternalCode(), equalTo(errorDetail.getCode()));
	}

	@Test
	void verifyBuildErrorResponseEntityWithCustomMessage() {

		ErrorDetail errorDetail = new ErrorDetail("code", "message", HttpStatus.I_AM_A_TEAPOT);
		String customMessage = "Custom message";

		ThreadContextWrapper.putCorrelationId("mock");

		ErrorResponse responseEntity = baseHandler.buildErrorResponse(errorDetail, customMessage);

		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getCorrelationId());
		assertFalse(responseEntity.getCorrelationId().isEmpty());
		assertThat(responseEntity.getMessage(), equalTo(customMessage));
		assertThat(responseEntity.getInternalCode(), equalTo(errorDetail.getCode()));
	}
}