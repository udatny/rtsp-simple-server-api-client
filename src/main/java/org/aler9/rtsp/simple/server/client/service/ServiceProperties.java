package org.aler9.rtsp.simple.server.client.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rtspsimpleserverclient")
public class ServiceProperties {

    private String serverBaseurl;

    public String getServerBaseurl() {
	return serverBaseurl;
    }

    public void setServerBaseurl(String serverBaseurl) {
	this.serverBaseurl = serverBaseurl;
    }

}
