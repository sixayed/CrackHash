package com.example.crackhash.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Setter
@ConfigurationProperties(prefix = "server")
@Slf4j
public class WebClientConfig {

    private String managerUrl;

    private String workerUrl;

    @Bean
    public WebClient managerWebClient() {
        return WebClient.builder()
                .baseUrl(managerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient workerWebClient() {
        return WebClient.builder()
                .baseUrl(workerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
