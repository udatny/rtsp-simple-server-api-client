# Simple RTSP Server Java Client

 this code provides a base how you could access the [rtsp-simple-server](https://github.com/aler9/rtsp-simple-server) api via java.

## how to run
`mvn generate-sources` to generate sources from the api doc spec

`mvn clean install` to generate and install jar in local repository

depending on your IDE you may need to add the generated sources to the build path. 
the generated sources could be found in the target/generated-sources/openapi folder

## how to use it in your application

see the Example.java

	RtspSimpleServerApiWrapperService rtspSimpleServerApiWrapperService = new RtspSimpleServerApiWrapperService(
		new RtspSimpleServerApiWrapperServiceProperties("http://localhost:9997"));

	PathsList pathsList = rtspSimpleServerApiWrapperService.getPathsList();
	// output configured paths
	pathsList.getItems().entrySet().forEach(pathEntry -> {
	    LOGGER.info(pathEntry.getKey() + ":" + pathEntry.getValue());
	});

in Spring you may set up a bean holding the RtspSimpleServerApiWrapperService

# get maven dependency via jitpack

https://jitpack.io/#udatny/rtsp-simple-server-api-client/0.5
