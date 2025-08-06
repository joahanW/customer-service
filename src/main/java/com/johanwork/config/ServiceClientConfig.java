package com.johanwork.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class ServiceClientConfig {

    @Bean
    public RestClient restClient(@Value("${movie.service.url}") String baseUrl) {
        log.info("Movie service url: {}", baseUrl);
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

}
