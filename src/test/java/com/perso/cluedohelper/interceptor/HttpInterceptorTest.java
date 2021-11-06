package com.perso.cluedohelper.interceptor;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;


@ExtendWith(MockitoExtension.class)
class HttpInterceptorTest {

  private static final String REQUEST_BODY = "{\"REQUEST_DUMMY_KEY\": \"REQUEST_DUMMY_BODY\"}";
  private static final String RESPONSE_BODY = "{\"RESPONSE_DUMMY_KEY\": \"RESPONSE_DUMMY_BODY\"}";

  private static final String REQUEST_HEADER_1 = "REQUEST_HEADER_1";
  private static final String REQUEST_HEADER_2 = "REQUEST_HEADER_2";
  private static final String REQUEST_VALUE_1 = "REQUEST_VALUE_1";
  private static final String REQUEST_VALUE_2 = "REQUEST_VALUE_2";

  private static final String RESPONSE_HEADER_1 = "RESPONSE_HEADER_1";
  private static final String RESPONSE_HEADER_2 = "RESPONSE_HEADER_2";
  private static final String RESPONSE_VALUE_1 = "RESPONSE_VALUE_1";
  private static final String RESPONSE_VALUE_2 = "RESPONSE_VALUE_2";

  private static final String MOCKED_URI = "https://foo.bar/baz";

  private static final String REQUEST_BODY_FORMAT = "Request [URI: {%s} | "
      + "HTTP Code: {%s} | "
      + "Headers: {%s} | "
      + "Body: {%s}]";

  private static final String RESPONSE_BODY_FORMAT = "Response [URI: {%s} | "
      + "HTTP Code: {%s} | "
      + "Headers: {%s} | "
      + "Body: {%s}]";

  private static final String HEADERS_FORMAT = "'%s' = [%s]";

  private HttpInterceptor httpInterceptor;

  @Mock
  private Logger logger;

  @Mock
  private HttpRequest httpRequest;

  @Mock
  private ClientHttpResponse clientHttpResponse;

  @Mock
  private ClientHttpRequestExecution clientHttpRequestExecution;


  private HttpHeaders requestHeaders;
  private HttpHeaders responseHeaders;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    httpInterceptor = new HttpInterceptor(logger);
  }

  private void setUpRequestHeaders(boolean toSingleMapValue) {
    final HttpHeaders requestHttpHeaders = mock(HttpHeaders.class);

    Set<String> requestHeadersKeySet = Stream.of(REQUEST_HEADER_1, REQUEST_HEADER_2)
        .collect(Collectors.toCollection(HashSet::new));
    when(requestHttpHeaders.keySet()).thenReturn(requestHeadersKeySet);
    when(requestHttpHeaders.get(REQUEST_HEADER_1))
        .thenReturn(Collections.singletonList(REQUEST_VALUE_1));
    when(requestHttpHeaders.get(REQUEST_HEADER_2))
        .thenReturn(Collections.singletonList(REQUEST_VALUE_2));

    if (toSingleMapValue) {
      Map<String, String> headersMap = new HashMap<>();
      headersMap.put(REQUEST_HEADER_1, REQUEST_VALUE_1);
      headersMap.put(REQUEST_HEADER_2, REQUEST_VALUE_2);

      when(requestHttpHeaders.toSingleValueMap()).thenReturn(headersMap);
    }

    this.requestHeaders = requestHttpHeaders;
  }

  private void setUpResponseHeaders(boolean toSingleMapValue) {
    final HttpHeaders responseHttpHeaders = mock(HttpHeaders.class);

    Set<String> responseHeadersKeySet = Stream.of(RESPONSE_HEADER_1, RESPONSE_HEADER_2)
        .collect(Collectors.toCollection(HashSet::new));
    when(responseHttpHeaders.keySet()).thenReturn(responseHeadersKeySet);
    when(responseHttpHeaders.get(RESPONSE_HEADER_1))
        .thenReturn(Collections.singletonList(RESPONSE_VALUE_1));
    when(responseHttpHeaders.get(RESPONSE_HEADER_2))
        .thenReturn(Collections.singletonList(RESPONSE_VALUE_2));

    if (toSingleMapValue) {
      Map<String, String> headersMap = new HashMap<>();
      headersMap.put(RESPONSE_HEADER_1, RESPONSE_VALUE_1);
      headersMap.put(RESPONSE_HEADER_2, RESPONSE_VALUE_2);

      when(responseHttpHeaders.toSingleValueMap()).thenReturn(headersMap);
    }

    this.responseHeaders = responseHttpHeaders;
  }


  @Test
  void verifyLogTraceRequestAndResponseWhenTheCommunicationIsSuccessful() throws IOException {

    HttpStatus responseStatus = HttpStatus.OK;

    setUpRequestHeaders(true);
    setUpResponseHeaders(true);

    when(httpRequest.getHeaders()).thenReturn(requestHeaders);
    when(clientHttpResponse.getHeaders()).thenReturn(responseHeaders);

    final URI uri = URI.create(MOCKED_URI);
    when(httpRequest.getURI()).thenReturn(uri);
    when(clientHttpResponse.getStatusCode()).thenReturn(responseStatus);
    when(clientHttpResponse.getBody())
        .thenReturn(new ByteArrayInputStream(RESPONSE_BODY.getBytes()));

    when(clientHttpRequestExecution.execute(httpRequest, REQUEST_BODY.getBytes(UTF_8)))
        .thenReturn(clientHttpResponse);

    when(logger.isTraceEnabled()).thenReturn(true);

    httpInterceptor
        .intercept(httpRequest, REQUEST_BODY.getBytes(UTF_8), clientHttpRequestExecution);

    String expectedRequestMessage = buildBody(
        REQUEST_BODY_FORMAT,
        MOCKED_URI,
        null,
        requestHeaders.toSingleValueMap(),
        REQUEST_BODY);

    String expectedResponseMessage = buildBody(
        RESPONSE_BODY_FORMAT,
        MOCKED_URI,
        responseStatus,
        responseHeaders.toSingleValueMap(),
        RESPONSE_BODY
    );

    verify(logger, times(1)).trace(expectedRequestMessage);
    verify(logger, times(1)).trace(expectedResponseMessage);

    verify(logger, never()).error(any());
    verify(logger, never()).info(any());
    verify(logger, never()).debug(any());
  }


  @Test
  void verifyLogTraceRequestAndResponseWhenTheCommunicationIsFailure() throws IOException {

    HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

    setUpRequestHeaders(true);
    setUpResponseHeaders(true);

    when(httpRequest.getHeaders()).thenReturn(requestHeaders);
    when(clientHttpResponse.getHeaders()).thenReturn(responseHeaders);

    final URI uri = URI.create(MOCKED_URI);
    when(httpRequest.getURI()).thenReturn(uri);
    when(clientHttpResponse.getStatusCode()).thenReturn(responseStatus);
    when(clientHttpResponse.getBody())
        .thenReturn(new ByteArrayInputStream(RESPONSE_BODY.getBytes()));

    when(clientHttpRequestExecution.execute(httpRequest, REQUEST_BODY.getBytes(UTF_8)))
        .thenReturn(clientHttpResponse);

    when(logger.isTraceEnabled()).thenReturn(true);

    httpInterceptor
        .intercept(httpRequest, REQUEST_BODY.getBytes(UTF_8), clientHttpRequestExecution);

    String expectedRequestMessage = buildBody(
        REQUEST_BODY_FORMAT,
        MOCKED_URI,
        null,
        requestHeaders.toSingleValueMap(),
        REQUEST_BODY);

    String expectedResponseMessage = buildBody(
        RESPONSE_BODY_FORMAT,
        MOCKED_URI,
        responseStatus,
        responseHeaders.toSingleValueMap(),
        RESPONSE_BODY
    );

    verify(logger, times(1)).error(expectedRequestMessage);
    verify(logger, times(1)).error(expectedResponseMessage);

    verify(logger, never()).trace(any());
    verify(logger, never()).info(any());
    verify(logger, never()).debug(any());
  }

  @Test
  void verifyLogWarnWhenExceptionIsCaught() throws IOException {
    HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

    setUpRequestHeaders(false);
    setUpResponseHeaders(false);

    when(httpRequest.getHeaders()).thenReturn(requestHeaders);
    when(clientHttpResponse.getHeaders()).thenReturn(responseHeaders);

    final URI uri = URI.create(MOCKED_URI);
    when(httpRequest.getURI()).thenReturn(uri);
    when(clientHttpResponse.getStatusCode()).thenReturn(responseStatus);

    IOException exception = new IOException("mocked");

    when(clientHttpResponse.getBody()).thenThrow(exception);

    when(clientHttpRequestExecution.execute(httpRequest, REQUEST_BODY.getBytes(UTF_8)))
        .thenReturn(clientHttpResponse);

    when(logger.isTraceEnabled()).thenReturn(true);

    httpInterceptor
        .intercept(httpRequest, REQUEST_BODY.getBytes(UTF_8), clientHttpRequestExecution);


    verify(logger, times(1)).warn(
        "Failed to log the Request/Response - {}",
        exception.getMessage());

    verify(logger, never()).trace(any());
    verify(logger, never()).error(any());
    verify(logger, never()).info(any());
    verify(logger, never()).debug(any());
  }


  private String buildBody(String format,
                           String uri,
                           HttpStatus httpCode,
                           Map<String, String> headers,
                           String body) {

    StringJoiner headersJoiner = new StringJoiner(", ");
    if (!headers.isEmpty()) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        headersJoiner.add(String.format(HEADERS_FORMAT, entry.getKey(), entry.getValue()));
      }
    }
    String httpCodeString = isNull(httpCode) ? "" : httpCode.toString();
    String headersString = headersJoiner.toString();

    return String.format(format, uri, httpCodeString, headersString, body);
  }
}