package org.aler9.rtsp.simple.server.client.conf;

import org.aler9.rtsp.simple.server.client.model.PathReaderHLSMuxer;
import org.aler9.rtsp.simple.server.client.model.PathReaderRTMPConn;
import org.aler9.rtsp.simple.server.client.model.PathReaderRTSPSSession;
import org.aler9.rtsp.simple.server.client.model.PathReaderRTSPSession;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = PathReaderHLSMuxer.class, name = "hlsMuxer"),
	@Type(value = PathReaderRTMPConn.class, name = "rtmpConn"),
	@Type(value = PathReaderRTSPSession.class, name = "rtspSession"),
	@Type(value = PathReaderRTSPSSession.class, name = "rtspsSession"), })

public abstract class OneOfPathReadersItemsMixin {

}
