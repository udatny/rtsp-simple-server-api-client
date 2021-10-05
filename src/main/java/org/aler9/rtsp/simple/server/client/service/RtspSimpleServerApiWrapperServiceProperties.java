package org.aler9.rtsp.simple.server.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rtspsimpleserverclient")
public class RtspSimpleServerApiWrapperServiceProperties {

    final static Logger LOGGER = LoggerFactory.getLogger(RtspSimpleServerApiWrapperServiceProperties.class);

    private final static String DEFAULT_RTSP_SERVER_BASEADDRESS = "http://localhost:9997";
    private String serverBaseurl;

    public String getServerBaseurl() {
	if (serverBaseurl == null) {
	    LOGGER.warn("you didnt specify the rtspsimpleserverclient.serverBaseurl property, using default.");
	    return DEFAULT_RTSP_SERVER_BASEADDRESS;
	}
	return serverBaseurl;
    }

    public void setServerBaseurl(String serverBaseurl) {
	this.serverBaseurl = serverBaseurl;
    }

}
