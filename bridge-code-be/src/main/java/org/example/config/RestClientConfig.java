package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
//                .baseUrl("https://c848-211-244-225-164.ngrok-free.app")
                .baseUrl("http://ai:5000")
                .build();
    }
}
