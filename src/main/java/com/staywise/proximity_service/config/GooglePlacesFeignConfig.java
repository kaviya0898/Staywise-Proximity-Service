package com.staywise.proximity_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.jackson.JacksonEncoder;
import feign.codec.Encoder;

@Configuration
public class GooglePlacesFeignConfig {

    @Bean
    public Encoder feignEncoder(ObjectMapper objectMapper) {
        return new JacksonEncoder(objectMapper);
    }
}
