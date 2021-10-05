# Simple RTSP Server Java Client

this code provides a base how you could access the [rtsp-simple-server](https://github.com/aler9/rtsp-simple-server) api via java / spring service.

it also show some issues using the api. 

## how to run
`mvn generate-sources` to generate sources from the api doc spec

`mvn clean install` to generate and install jar in local repository

depending on your IDE you may need to add the generated sources to the build path. 
the generated sources could be found in the target/generated-sources/openapi folder

## how to use in your spring application

in your application.properties add the server base url property

      rtspsimpleserverclient.serverBaseurl=http://localhost:9997

add package scanning for the library  

     @SpringBootApplication(scanBasePackages = { "your.application.basepath", "org.aler9.rtsp.simple.server.client" })

then you may autowire the api wrapper service with

    @Autowired
    RtspSimpleServerApiWrapperService rtspSimpleServerApiWrapperService;

# get maven dependency via jitpack

https://jitpack.io/#udatny/rtsp-simple-server-api-client/v0.2
