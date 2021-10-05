package org.aler9.rtsp.simple.server.client.conf;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.aler9.rtsp.simple.server.client.model.OneOfPathReadersItems;
import org.aler9.rtsp.simple.server.client.model.OneOfPathSource;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfig {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
	return builder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
	Map<Class<?>, Class<?>> mixins = new HashMap<>();
	mixins.put(OneOfPathSource.class, OneOfPathSourceMixin.class);
	mixins.put(OneOfPathReadersItems.class, OneOfPathReadersItemsMixin.class);

	return builder -> {
	    builder.simpleDateFormat(dateTimeFormat);
	    builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
	    builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
	    builder.mixIns(mixins);
	};
    }

}