package org.aler9.rtsp.simple.server.client.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.aler9.rtsp.simple.server.client.api.V1Api;
import org.aler9.rtsp.simple.server.client.mixins.OneOfPathReadersItemsMixin;
import org.aler9.rtsp.simple.server.client.mixins.OneOfPathSourceMixin;
import org.aler9.rtsp.simple.server.client.model.Conf;
import org.aler9.rtsp.simple.server.client.model.OneOfPathReadersItems;
import org.aler9.rtsp.simple.server.client.model.OneOfPathSource;
import org.aler9.rtsp.simple.server.client.model.PathConf;
import org.aler9.rtsp.simple.server.client.model.PathsList;
import org.aler9.rtsp.simple.server.client.model.RTMPConnsList;
import org.aler9.rtsp.simple.server.client.model.RTSPSSessionsList;
import org.aler9.rtsp.simple.server.client.model.RTSPSessionsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RtspSimpleServerApiWrapperService {

    final static Logger LOGGER = LoggerFactory.getLogger(RtspSimpleServerApiWrapperService.class);

    private final RtspSimpleServerApiWrapperServiceProperties rtspSimpleServerApiWrapperServiceProperties;

    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<Conf> typeRefConfResponse = new ParameterizedTypeReference<Conf>() {
    };
    private final ParameterizedTypeReference<Void> typeRefVoidResponse = new ParameterizedTypeReference<Void>() {
    };
    private final ParameterizedTypeReference<PathsList> typeRefPathListResponse = new ParameterizedTypeReference<PathsList>() {
    };
    private final ParameterizedTypeReference<RTMPConnsList> typeRefRtmpConnsListResponse = new ParameterizedTypeReference<RTMPConnsList>() {
    };
    private final ParameterizedTypeReference<RTSPSessionsList> typeRefRtspSessionsListResponse = new ParameterizedTypeReference<RTSPSessionsList>() {
    };
    private final ParameterizedTypeReference<RTSPSSessionsList> typeRefRtspsSessionsListResponse = new ParameterizedTypeReference<RTSPSSessionsList>() {
    };

    public RtspSimpleServerApiWrapperService(
	    RtspSimpleServerApiWrapperServiceProperties rtspSimpleServerApiWrapperServiceProperties) {
	super();
	this.rtspSimpleServerApiWrapperServiceProperties = rtspSimpleServerApiWrapperServiceProperties;
	this.restTemplate = createRestTemplate();
    }

    private static final String dateFormat = "yyyy-MM-dd";

    private RestTemplate createRestTemplate() {
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.setErrorHandler(new RtspSimpleServerApiHttpErrorHandler());
	restTemplate.getMessageConverters().add(0, createMappingJacksonHttpMessageConverter());
	return restTemplate;
    }

    private ObjectMapper createObjectMapper() {
	ObjectMapper om = new ObjectMapper();

	Map<Class<?>, Class<?>> mixins = new HashMap<>();
	mixins.put(OneOfPathSource.class, OneOfPathSourceMixin.class);
	mixins.put(OneOfPathReadersItems.class, OneOfPathReadersItemsMixin.class);

	om.setDateFormat(new SimpleDateFormat(dateFormat));
	om.setMixIns(mixins);
	return om;
    }

    private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter() {
	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	converter.setObjectMapper(createObjectMapper());
	return converter;
    }

    public Conf getConfig() {
	try {
	    ResponseEntity<Conf> response = restTemplate.exchange(getConfUrl(), HttpMethod.GET, new HttpEntity<>(null),
		    typeRefConfResponse, "");
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public Void setConfig(Conf conf) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(setConfUrl(), HttpMethod.POST, new HttpEntity<>(conf),
		    typeRefVoidResponse, conf);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public PathsList getPathsList() {
	try {
	    ResponseEntity<PathsList> response = restTemplate.exchange(getPathsListUrl(), HttpMethod.GET,
		    new HttpEntity<>(null), typeRefPathListResponse, "");
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public Void addPathConfig(String pathName, PathConf pathConfig) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(addPathConfigUrl(pathName), HttpMethod.POST,
		    new HttpEntity<>(pathConfig), typeRefVoidResponse, pathConfig);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}

    }

    public Void changePathConfig(String pathName, PathConf pathConfig) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(editPathConfigUrl(pathName), HttpMethod.POST,
		    new HttpEntity<>(pathConfig), typeRefVoidResponse, pathConfig);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public Void removePathConfig(String pathName) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(removePathConfigUrl(pathName), HttpMethod.POST,
		    new HttpEntity<>(null), typeRefVoidResponse);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public RTMPConnsList getRtmpConnsList() {
	try {
	    ResponseEntity<RTMPConnsList> response = restTemplate.exchange(getRtmpConnsListUrl(), HttpMethod.GET,
		    new HttpEntity<>(null), typeRefRtmpConnsListResponse, "");
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public RTSPSessionsList getRtspSessionsList() {
	try {
	    ResponseEntity<RTSPSessionsList> response = restTemplate.exchange(getRtspSessionsListUrl(), HttpMethod.GET,
		    new HttpEntity<>(null), typeRefRtspSessionsListResponse, "");
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public RTSPSSessionsList getRtspsSessionsList() {
	try {
	    ResponseEntity<RTSPSSessionsList> response = restTemplate.exchange(getRtspsSessionsListUrl(),
		    HttpMethod.GET, new HttpEntity<>(null), typeRefRtspsSessionsListResponse, "");
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public Void kickRtmpConn(String id) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(kickRtmpConnUrl(id), HttpMethod.POST,
		    new HttpEntity<>(null), typeRefVoidResponse);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public Void kickRtspConn(String id) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(kickRtspConnUrl(id), HttpMethod.POST,
		    new HttpEntity<>(null), typeRefVoidResponse);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    public Void kickRtspsConn(String id) {
	try {
	    ResponseEntity<Void> response = restTemplate.exchange(kickRtspsConnUrl(id), HttpMethod.POST,
		    new HttpEntity<>(null), typeRefVoidResponse);
	    return response.getBody();
	} catch (RestClientException restClientException) {
	    throw extractAndRethrowException(restClientException);
	}
    }

    private String getConfUrl() {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configGet()).withSelfRel().toUri().getPath();
	LOGGER.debug("configGet=" + url);
	return url;
    }

    private String setConfUrl() {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configSet(null)).withSelfRel().toUri().getPath();
	LOGGER.debug("configSet=" + url);
	return url;
    }

    private String addPathConfigUrl(String pathName) {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl()
		+ WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configPathsAdd(pathName, null))
			.withSelfRel().toUri().getPath();
	LOGGER.debug("configPathsAdd=" + url);
	return url;
    }

    private String editPathConfigUrl(String pathName) {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl()
		+ WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configPathsEdit(pathName, null))
			.withSelfRel().toUri().getPath();
	LOGGER.debug("configPathsEdit=" + url);
	return url;
    }

    private String removePathConfigUrl(String pathName) {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl()
		+ WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).configPathsRemove(pathName))
			.withSelfRel().toUri().getPath();
	LOGGER.debug("configPathsRemove=" + url);
	return url;
    }

    private String getPathsListUrl() {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).pathsList()).withSelfRel().toUri().getPath();
	LOGGER.debug("pathsList=" + url);
	return url;
    }

    private String getRtmpConnsListUrl() {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtmpConnsList()).withSelfRel().toUri().getPath();
	LOGGER.debug("rtmpConnsList=" + url);
	return url;
    }

    private String getRtspSessionsListUrl() {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspSessionsList()).withSelfRel().toUri().getPath();
	LOGGER.debug("rtspSessionsList=" + url);
	return url;
    }

    private String getRtspsSessionsListUrl() {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspsSessionsList()).withSelfRel().toUri().getPath();
	LOGGER.debug("rtspsSessionsList=" + url);
	return url;
    }

    private String kickRtmpConnUrl(String id) {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtmpConnsKick(id)).withSelfRel().toUri().getPath();
	LOGGER.debug("rtmpConnsKick=" + url);
	return url;
    }

    private String kickRtspConnUrl(String id) {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspSessionsKick(id)).withSelfRel().toUri().getPath();
	LOGGER.debug("rtspSessionsKick=" + url);
	return url;
    }

    private String kickRtspsConnUrl(String id) {
	String url = rtspSimpleServerApiWrapperServiceProperties.getServerBaseurl() + WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder.methodOn(V1Api.class).rtspsSessionsKick(id)).withSelfRel().toUri().getPath();
	LOGGER.debug("rtspsSessionsKick=" + url);
	return url;
    }

    private RtspSimpleServerApiException extractAndRethrowException(RestClientException restClientException) {
	if (restClientException.contains(RtspSimpleServerApiException.class)) {
	    throw (RtspSimpleServerApiException) restClientException.getMostSpecificCause();
	} else {
	    throw new RtspSimpleServerApiException(null, restClientException.getMessage());
	}
    }

}