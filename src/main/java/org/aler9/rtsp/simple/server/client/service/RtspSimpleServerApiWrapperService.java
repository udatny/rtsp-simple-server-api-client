package org.aler9.rtsp.simple.server.client.service;

import org.aler9.rtsp.simple.server.client.api.V1Api;
import org.aler9.rtsp.simple.server.client.model.Conf;
import org.aler9.rtsp.simple.server.client.model.PathConf;
import org.aler9.rtsp.simple.server.client.model.PathsList;
import org.aler9.rtsp.simple.server.client.model.RtmpConnsList;
import org.aler9.rtsp.simple.server.client.model.RtspSessionsList;
import org.aler9.rtsp.simple.server.client.model.RtspsSessionsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(ServiceProperties.class)
public class RtspSimpleServerApiWrapperService {

    Logger LOGGER = LoggerFactory.getLogger(RtspSimpleServerApiWrapperService.class);

    private final ServiceProperties serviceProperties;

//    @Value("${rtspsimpleserver.baseurl:http://localhost:8999}")
//    private String rtspSimpleServerBaseUrl;

    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<Conf> typeRefConfResponse = new ParameterizedTypeReference<Conf>() {
    };
    private final ParameterizedTypeReference<Void> typeRefVoidResponse = new ParameterizedTypeReference<Void>() {
    };
    private final ParameterizedTypeReference<PathsList> typeRefPathListResponse = new ParameterizedTypeReference<PathsList>() {
    };
    private final ParameterizedTypeReference<RtmpConnsList> typeRefRtmpConnsListResponse = new ParameterizedTypeReference<RtmpConnsList>() {
    };
    private final ParameterizedTypeReference<RtspSessionsList> typeRefRtspSessionsListResponse = new ParameterizedTypeReference<RtspSessionsList>() {
    };
    private final ParameterizedTypeReference<RtspsSessionsList> typeRefRtspsSessionsListResponse = new ParameterizedTypeReference<RtspsSessionsList>() {
    };

    public Conf getConfig() {
	ResponseEntity<Conf> response = restTemplate.exchange(getConfUrl(), HttpMethod.GET, new HttpEntity<>(null),
		typeRefConfResponse, "");
	return response.getBody();
    }

    public Void setConfig(Conf conf) {
	ResponseEntity<Void> response = restTemplate.exchange(setConfUrl(), HttpMethod.POST, new HttpEntity<>(conf),
		typeRefVoidResponse, conf);
	return response.getBody();
    }

    public PathsList getPathsList() {
	ResponseEntity<PathsList> response = restTemplate.exchange(getPathsListUrl(), HttpMethod.GET,
		new HttpEntity<>(null), typeRefPathListResponse, "");
	return response.getBody();
    }

    public Void addPathConfig(String pathName, PathConf pathConfig) {
	ResponseEntity<Void> response = restTemplate.exchange(addPathConfigUrl(pathName), HttpMethod.POST,
		new HttpEntity<>(pathConfig), typeRefVoidResponse, pathConfig);
	return response.getBody();
    }

    public Void changePathConfig(String pathName, PathConf pathConfig) {
	ResponseEntity<Void> response = restTemplate.exchange(editPathConfigUrl(pathName), HttpMethod.POST,
		new HttpEntity<>(pathConfig), typeRefVoidResponse, pathConfig);
	return response.getBody();
    }

    public Void removePathConfig(String pathName) {
	ResponseEntity<Void> response = restTemplate.exchange(removePathConfigUrl(pathName), HttpMethod.POST,
		new HttpEntity<>(null), typeRefVoidResponse);
	return response.getBody();
    }

    public RtmpConnsList getRtmpConnsList() {
	ResponseEntity<RtmpConnsList> response = restTemplate.exchange(getRtmpConnsListUrl(), HttpMethod.GET,
		new HttpEntity<>(null), typeRefRtmpConnsListResponse, "");
	return response.getBody();
    }

    public RtspSessionsList getRtspSessionsList() {
	ResponseEntity<RtspSessionsList> response = restTemplate.exchange(getRtspSessionsListUrl(), HttpMethod.GET,
		new HttpEntity<>(null), typeRefRtspSessionsListResponse, "");
	return response.getBody();
    }

    public RtspsSessionsList getRtspsSessionsList() {
	ResponseEntity<RtspsSessionsList> response = restTemplate.exchange(getRtspsSessionsListUrl(), HttpMethod.GET,
		new HttpEntity<>(null), typeRefRtspsSessionsListResponse, "");
	return response.getBody();
    }

    public Void kickRtmpConn(String id) {
	ResponseEntity<Void> response = restTemplate.exchange(kickRtmpConnUrl(id), HttpMethod.POST,
		new HttpEntity<>(null), typeRefVoidResponse);
	return response.getBody();
    }

    public Void kickRtspConn(String id) {
	ResponseEntity<Void> response = restTemplate.exchange(kickRtspConnUrl(id), HttpMethod.POST,
		new HttpEntity<>(null), typeRefVoidResponse);
	return response.getBody();
    }

    public Void kickRtspsConn(String id) {
	ResponseEntity<Void> response = restTemplate.exchange(kickRtspsConnUrl(id), HttpMethod.POST,
		new HttpEntity<>(null), typeRefVoidResponse);
	return response.getBody();
    }

    private String getConfUrl() {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configGet()).withSelfRel().toUri().getPath();
	LOGGER.info("configGet=" + url);
	return url;
    }

    private String setConfUrl() {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configSet(null)).withSelfRel().toUri().getPath();
	LOGGER.info("configSet=" + url);
	return url;
    }

    private String addPathConfigUrl(String pathName) {
	String url = serviceProperties.getServerBaseurl()
		+ WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configPathsAdd(pathName, null))
			.withSelfRel().toUri().getPath();
	LOGGER.info("configPathsAdd=" + url);
	return url;
    }

    private String editPathConfigUrl(String pathName) {
	String url = serviceProperties.getServerBaseurl()
		+ WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configPathsEdit(pathName, null))
			.withSelfRel().toUri().getPath();
	LOGGER.info("configPathsEdit=" + url);
	return url;
    }

    private String removePathConfigUrl(String pathName) {
	String url = serviceProperties.getServerBaseurl()
		+ WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configPathsRemove(pathName))
			.withSelfRel().toUri().getPath();
	LOGGER.info("configPathsRemove=" + url);
	return url;
    }

    private String getPathsListUrl() {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).pathsList()).withSelfRel().toUri().getPath();
	LOGGER.info("pathsList=" + url);
	return url;
    }

    private String getRtmpConnsListUrl() {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtmpConnsList()).withSelfRel().toUri().getPath();
	LOGGER.info("rtmpConnsList=" + url);
	return url;
    }

    private String getRtspSessionsListUrl() {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspSessionsList()).withSelfRel().toUri().getPath();
	LOGGER.info("rtspSessionsList=" + url);
	return url;
    }

    private String getRtspsSessionsListUrl() {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspsSessionsList()).withSelfRel().toUri().getPath();
	LOGGER.info("rtspsSessionsList=" + url);
	return url;
    }

    private String kickRtmpConnUrl(String id) {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtmpConnsKick(id)).withSelfRel().toUri().getPath();
	LOGGER.info("rtmpConnsKick=" + url);
	return url;
    }

    private String kickRtspConnUrl(String id) {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspSessionsKick(id)).withSelfRel().toUri().getPath();
	LOGGER.info("rtspSessionsKick=" + url);
	return url;
    }

    private String kickRtspsConnUrl(String id) {
	String url = serviceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspsSessionsKick(id)).withSelfRel().toUri().getPath();
	LOGGER.info("rtspsSessionsKick=" + url);
	return url;
    }

}