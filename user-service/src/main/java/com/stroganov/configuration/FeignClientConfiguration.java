package com.stroganov.configuration;

import com.stroganov.service.InternalJWTManager;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    private final InternalJWTManager jwtManager;

    @Autowired
    public FeignClientConfiguration(InternalJWTManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + jwtManager.getInterServiceToken());
        };
    }
}
