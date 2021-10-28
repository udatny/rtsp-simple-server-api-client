package org.aler9.rtsp.simple.server.client.example;

import org.aler9.rtsp.simple.server.client.model.PathsList;
import org.aler9.rtsp.simple.server.client.service.RtspSimpleServerApiWrapperService;
import org.aler9.rtsp.simple.server.client.service.RtspSimpleServerApiWrapperServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example {

    final static Logger LOGGER = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) {
	useService();
    }

    private static void useService() {

	RtspSimpleServerApiWrapperService rtspSimpleServerApiWrapperService = new RtspSimpleServerApiWrapperService(
		new RtspSimpleServerApiWrapperServiceProperties("http://localhost:9997"));

	PathsList pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	pathsList.getItems().entrySet().forEach(pathEntry -> {
	    LOGGER.info(pathEntry.getKey() + ":" + pathEntry.getValue());
	});

    }

}
