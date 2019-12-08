package ru.vood.restclient.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfiguration {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder, messageConverters: List<HttpMessageConverter<*>>): RestTemplate {
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
        return builder
                .additionalMessageConverters(messageConverters)
                .build()
    }
}