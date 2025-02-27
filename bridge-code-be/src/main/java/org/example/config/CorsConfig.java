package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 모든 엔드포인트에 CORS 적용
                        .allowedOriginPatterns("*")
                        .allowedOrigins("*")// 모든 도메인 허용 (보안상 주의)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 지정
                        .allowedHeaders("*")  // 모든 헤더 허용
                        .allowCredentials(false); // 인증 정보 포함 X
            }
        };
    }
}
