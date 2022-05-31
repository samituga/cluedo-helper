package io.github.samituga.cluedohelperapi.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import io.github.samituga.cluedohelperapi.config.logger.LoggerConfig;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureMockMvc
@SpringBootTest(classes = {CorrelationFilter.class, LoggerConfig.class})
class CorrelationFilterTest {

    private static final String CORRELATION_ID_KEY = "X-Correlation-ID";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void verifyResponseCorrelationHeaderIsNotNull() throws Exception {

        final URI mockURI = new URI("http://localhost:8080/test");
        final MockHttpServletRequestBuilder request = get(mockURI);

        final MockHttpServletResponse response = mockMvc.perform(request)
              .andReturn()
              .getResponse();

        String responseCorrelationId = response.getHeader(CORRELATION_ID_KEY);
        assertNotNull(responseCorrelationId);
    }

    @Test
    void verifyResponseCorrelationHeaderEqualsToRequestCorrelationHeader() throws Exception {

        final URI mockURI = new URI("http://localhost:8080/test");
        final MockHttpServletRequestBuilder request = get(mockURI);

        final String mockedCorrelationId = "mocked_correlation_id";

        request.header(CORRELATION_ID_KEY, mockedCorrelationId);

        final MockHttpServletResponse response = mockMvc.perform(request)
              .andReturn()
              .getResponse();

        String responseCorrelationId = response.getHeader(CORRELATION_ID_KEY);
        assertThat(mockedCorrelationId, equalTo(responseCorrelationId));
    }

}