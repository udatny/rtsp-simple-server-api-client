package org.aler9.rtsp.simple.server.client.service;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map.Entry;
import java.util.Optional;

import org.aler9.rtsp.simple.server.client.model.Conf;
import org.aler9.rtsp.simple.server.client.model.PathConf;
import org.aler9.rtsp.simple.server.client.model.PathsList;
import org.aler9.rtsp.simple.server.client.model.RTMPConn;
import org.aler9.rtsp.simple.server.client.model.RTSPSSession;
import org.aler9.rtsp.simple.server.client.model.RTSPSession;
import org.aler9.rtsp.simple.server.client.model.RtmpConnsList;
import org.aler9.rtsp.simple.server.client.model.RtspSessionsList;
import org.aler9.rtsp.simple.server.client.model.RtspsSessionsList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class RtspSimpleServerApiWrapperServiceTest {

    @Autowired
    RtspSimpleServerApiWrapperService rtspSimpleServerApiWrapperService;

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
	conf.setHlsSegmentDuration(null);
	conf.setReadTimeout(null);
	conf.setWriteTimeout(null);
	conf.setRtspDisable(false);
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
//	BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
//	try {
//	    notNull.copyProperties(newPathConf, pathConf);
//	} catch (IllegalAccessException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	} catch (InvocationTargetException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
	newPathConf.setPublishUser(changedPublishUsername);
	newPathConf.setPublishPass("pass");
//	newPathConf.setReadIPs(new ArrayList<>());
//	newPathConf.setPublishIPs(new ArrayList<>());
	newPathConf.sourceOnDemandStartTimeout(null);
	newPathConf.sourceOnDemandCloseAfter(null);
	newPathConf.setRunOnDemandStartTimeout(null);
	newPathConf.setRunOnDemandCloseAfter(null);

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

    @Test
    void testGetRtspsSessionsList() {
	rtspSimpleServerApiWrapperService.getRtspsSessionsList();
    }

    @Test
    void testKickRtmpConn() {
	RtmpConnsList session = rtspSimpleServerApiWrapperService.getRtmpConnsList();
	Optional<Entry<String, RTMPConn>> firstOptional = session.getItems().entrySet().stream().findFirst();

	if (firstOptional.isPresent()) {
	    rtspSimpleServerApiWrapperService.kickRtmpConn(firstOptional.get().getKey());
	}
    }

    @Test
    void testKickRtspConn() {
	RtspSessionsList session = rtspSimpleServerApiWrapperService.getRtspSessionsList();
	Optional<Entry<String, RTSPSession>> firstOptional = session.getItems().entrySet().stream().findFirst();

	if (firstOptional.isPresent()) {
	    rtspSimpleServerApiWrapperService.kickRtspConn(firstOptional.get().getKey());
	}

    }

    @Test
    void testKickRtspsConn() {
	RtspsSessionsList session = rtspSimpleServerApiWrapperService.getRtspsSessionsList();
	Optional<Entry<String, RTSPSSession>> firstOptional = session.getItems().entrySet().stream().findFirst();

	if (firstOptional.isPresent()) {
	    rtspSimpleServerApiWrapperService.kickRtspsConn(firstOptional.get().getKey());
	}

    }

}
