package io.github.samituga.cluedohelperapi.interceptor;

import io.github.samituga.cluedohelperapi.util.ApiConstants;
import io.github.samituga.cluedohelperapi.util.ThreadContextWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Will intercept the requests and responses made with
 * {@link org.springframework.web.client.RestTemplate} and log the communication with
 * {@link #logCommunication(HttpRequest, byte[], ClientHttpResponse)}.
 */
@RequiredArgsConstructor
public class HttpInterceptor implements ClientHttpRequestInterceptor {

    private static final String EXTERNAL_REQUEST_MESSAGE_FORMAT =
          "%s [URI: {%s} | HTTP Code: {%s} | Headers: {%s} | Body: {%s}]";
    private static final String HEADER_KEY_VALUE_FORMAT = "'%s' = %s";
    private final Logger logger;

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        request.getHeaders()
              .add(ApiConstants.CORRELATION_ID_KEY, ThreadContextWrapper.getCorrelationId());

        ClientHttpResponse response = execution.execute(request, body);

        try {
            logCommunication(request, body, response);
        } catch (Exception exception) {
            logger.warn("Failed to log the Request/Response - {}", exception.getMessage());
        }

        return response;
    }


    private void logCommunication(HttpRequest request, byte[] body, ClientHttpResponse response)
          throws IOException {

        if (logger.isTraceEnabled() && !response.getStatusCode().isError()) {
            logTrace(request, body, response);
        }

        if (response.getStatusCode().isError()) {
            logError(request, body, response);
        }
    }

    private void logTrace(HttpRequest request, byte[] body, ClientHttpResponse response)
          throws IOException {
        RequestResponseLoggingMessage requestResponseLoggingMessage =
              buildRequestResponseMessage(request, body, response);

        logger.trace(requestResponseLoggingMessage.request());
        logger.trace(requestResponseLoggingMessage.response());
    }

    private void logError(HttpRequest request, byte[] body, ClientHttpResponse response)
          throws IOException {
        RequestResponseLoggingMessage requestResponseLoggingMessage =
              buildRequestResponseMessage(request, body, response);

        logger.error(requestResponseLoggingMessage.request());
        logger.error(requestResponseLoggingMessage.response());
    }

    private RequestResponseLoggingMessage buildRequestResponseMessage(HttpRequest request,
                                                                      byte[] body,
                                                                      ClientHttpResponse response)
          throws IOException {

        final String requestBodyToLog = new String(body, StandardCharsets.UTF_8);

        final String requestHeadersString = headersToString(request.getHeaders());

        final String responseHeadersString = headersToString(response.getHeaders());

        final String uriString = request.getURI().toString();

        final String requestMessage = buildMessage(
              "Request",
              "",
              requestHeadersString,
              uriString,
              requestBodyToLog
        );

        final String responseBodyToLog = getResponseBodyMessage(response);

        final String responseMessage = buildMessage(
              "Response",
              response.getStatusCode().toString(),
              responseHeadersString,
              uriString,
              responseBodyToLog
        );

        return new RequestResponseLoggingMessage(requestMessage, responseMessage);
    }

    private String headersToString(HttpHeaders headers) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String key : headers.keySet()) {
            String format = String.format(HEADER_KEY_VALUE_FORMAT, key, headers.get(key));
            joiner.add(format);
        }
        return joiner.toString();
    }

    private String buildMessage(String prefix,
                                String httpStatus,
                                String headersString,
                                String uriString,
                                String body) {

        return String.format(
              EXTERNAL_REQUEST_MESSAGE_FORMAT,
              prefix,
              uriString,
              httpStatus,
              headersString,
              body);
    }

    private String getResponseBodyMessage(ClientHttpResponse response) throws IOException {

        return new BufferedReader(
              new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
              .lines()
              .collect(Collectors.joining("\n"));
    }

    private record RequestResponseLoggingMessage(String request, String response) {
    }
}
