package org.aler9.rtsp.simple.server.client.mixins;

import org.aler9.rtsp.simple.server.client.model.PathSourceRTMPConn;
import org.aler9.rtsp.simple.server.client.model.PathSourceRTMPSource;
import org.aler9.rtsp.simple.server.client.model.PathSourceRTSPSSession;
import org.aler9.rtsp.simple.server.client.model.PathSourceRTSPSession;
import org.aler9.rtsp.simple.server.client.model.PathSourceRTSPSource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = PathSourceRTMPConn.class, name = "rtmpConn"),
	@Type(value = PathSourceRTMPSource.class, name = "rtmpSource"),
	@Type(value = PathSourceRTSPSession.class, name = "rtspSession"),
	@Type(value = PathSourceRTSPSource.class, name = "rtspSource"),
	@Type(value = PathSourceRTSPSSession.class, name = "rtspsSession"), })

public abstract class OneOfPathSourceMixin {

}
