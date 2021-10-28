package org.aler9.rtsp.simple.server.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

public class RtspSimpleServerApiHttpErrorHandler implements ResponseErrorHandler {

    final static Logger LOGGER = LoggerFactory.getLogger(RtspSimpleServerApiHttpErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

	LOGGER.info("testing error for http response:" + httpResponse.getRawStatusCode());

	return (httpResponse.getStatusCode().series() == Series.CLIENT_ERROR
		|| httpResponse.getStatusCode().series() == Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

	LOGGER.debug("handling http response error code: " + httpResponse.getStatusCode());

	if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR
		|| httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {

	    String body = null;
	    try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getBody()));
		body = reader.lines().collect(Collectors.joining(""));
	    } catch (IOException e) {
		LOGGER.debug("cannot extract body from response");
	    }
	    throw new RestClientException("rtsp server call failed- ",
		    new RtspSimpleServerApiException(httpResponse.getStatusCode(), "received "
			    + httpResponse.getStatusCode() + " from server. body in response from server: " + body));

	}
    }
}