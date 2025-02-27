package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("ALB-python-1567497534.ap-northeast-2.elb.amazonaws.com:5000") // AI 서버 기본 URL
                .build();
    }
}
