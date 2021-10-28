package org.aler9.rtsp.simple.server.client.service;

import org.springframework.http.HttpStatus;

public class RtspSimpleServerApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus statusCode;
    private String error;

    public HttpStatus getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
	this.statusCode = statusCode;
    }

    public String getError() {
	return error;
    }

    public void setError(String error) {
	this.error = error;
    }

    public RtspSimpleServerApiException(HttpStatus statusCode, String error) {
	super(error);
	this.statusCode = statusCode;
	this.error = error;
    }

}
