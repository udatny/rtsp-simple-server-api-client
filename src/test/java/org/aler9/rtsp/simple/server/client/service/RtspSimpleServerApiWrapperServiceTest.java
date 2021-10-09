package org.aler9.rtsp.simple.server.client.service;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map.Entry;
import java.util.Optional;

import org.aler9.rtsp.simple.server.client.model.Conf;
import org.aler9.rtsp.simple.server.client.model.PathConf;
import org.aler9.rtsp.simple.server.client.model.PathsList;
import org.aler9.rtsp.simple.server.client.model.RTMPConn;
import org.aler9.rtsp.simple.server.client.model.RTMPConnsList;
import org.aler9.rtsp.simple.server.client.model.RTSPSSession;
import org.aler9.rtsp.simple.server.client.model.RTSPSSessionsList;
import org.aler9.rtsp.simple.server.client.model.RTSPSession;
import org.aler9.rtsp.simple.server.client.model.RTSPSessionsList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RtspSimpleServerApiWrapperServiceTest {

    RtspSimpleServerApiWrapperService rtspSimpleServerApiWrapperService;

    @BeforeEach
    public void init() {
	rtspSimpleServerApiWrapperService = new RtspSimpleServerApiWrapperService(
		new RtspSimpleServerApiWrapperServiceProperties("http://localhost:9997"));
    }

    @Test
    void testGetConfig() {
	Conf conf = rtspSimpleServerApiWrapperService.getConfig();
    }

    @Test
    void testSetConfig() {
	Conf conf = rtspSimpleServerApiWrapperService.getConfig();
	conf.getPaths().values().forEach(pathConf -> {
	    pathConf.sourceOnDemandStartTimeout(null);
	    pathConf.sourceOnDemandCloseAfter(null);
	    pathConf.setRunOnDemandStartTimeout(null);
	    pathConf.setRunOnDemandCloseAfter(null);

	});
	// TODO fix api
	rtspSimpleServerApiWrapperService.setConfig(conf);
    }

    @Test
    void testGetPathsList() {
	PathsList pathsList = rtspSimpleServerApiWrapperService.getPathsList();
    }

    @Test
    void testAddPathConfig() {
	String addpathName = "addpath";
	PathConf pathConf = new PathConf();
	PathsList pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	if (pathsList.getItems().containsKey(addpathName)) {
	    rtspSimpleServerApiWrapperService.removePathConfig(addpathName);
	}
	rtspSimpleServerApiWrapperService.addPathConfig(addpathName, pathConf);
    }

    @Test
    void testChangePathConfig() {
	String pathChangeName = "testpath";
	String changedPublishUsername = "testuser";

	// see if pathcionfig with this name already exists
	// the remove it
	PathsList pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	if (pathsList.getItems().containsKey(pathChangeName)) {
	    rtspSimpleServerApiWrapperService.removePathConfig(pathChangeName);
	}
	PathConf pathConf = new PathConf();
	rtspSimpleServerApiWrapperService.addPathConfig(pathChangeName, pathConf);
	pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	pathConf = pathsList.getItems().get(pathChangeName).getConf();

	PathConf newPathConf = pathConf;
	newPathConf.setPublishUser(changedPublishUsername);
	newPathConf.setPublishPass("pass");

	rtspSimpleServerApiWrapperService.changePathConfig(pathChangeName, newPathConf);

	pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	pathConf = pathsList.getItems().get(pathChangeName).getConf();
	assertThat("publish username matches", pathConf.getPublishUser().equals(changedPublishUsername));

    }

    @Test
    void testRemovePathConfig() {
	String addpathName = "removepath";
	PathConf pathConf = new PathConf();
	PathsList pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	if (!pathsList.getItems().containsKey(addpathName)) {
	    rtspSimpleServerApiWrapperService.addPathConfig(addpathName, pathConf);
	}
	rtspSimpleServerApiWrapperService.removePathConfig(addpathName);
    }

    @Test
    void testGetRtmpConnsList() {
	rtspSimpleServerApiWrapperService.getRtmpConnsList();
    }

    @Test
    void testGetRtspSessionsList() {
	rtspSimpleServerApiWrapperService.getRtspSessionsList();
    }

    @Disabled
    @Test
    void testGetRtspsSessionsList() {
	rtspSimpleServerApiWrapperService.getRtspsSessionsList();
    }

    @Test
    void testKickRtmpConn() {
	RTMPConnsList session = rtspSimpleServerApiWrapperService.getRtmpConnsList();
	Optional<Entry<String, RTMPConn>> firstOptional = session.getItems().entrySet().stream().findFirst();

	if (firstOptional.isPresent()) {
	    rtspSimpleServerApiWrapperService.kickRtmpConn(firstOptional.get().getKey());
	}
    }

    @Test
    void testKickRtspConn() {
	RTSPSessionsList session = rtspSimpleServerApiWrapperService.getRtspSessionsList();
	Optional<Entry<String, RTSPSession>> firstOptional = session.getItems().entrySet().stream().findFirst();

	if (firstOptional.isPresent()) {
	    rtspSimpleServerApiWrapperService.kickRtspConn(firstOptional.get().getKey());
	}

    }

    @Disabled
    @Test
    void testKickRtspsConn() {
	RTSPSSessionsList session = rtspSimpleServerApiWrapperService.getRtspsSessionsList();
	Optional<Entry<String, RTSPSSession>> firstOptional = session.getItems().entrySet().stream().findFirst();

	if (firstOptional.isPresent()) {
	    rtspSimpleServerApiWrapperService.kickRtspsConn(firstOptional.get().getKey());
	}

    }

}
