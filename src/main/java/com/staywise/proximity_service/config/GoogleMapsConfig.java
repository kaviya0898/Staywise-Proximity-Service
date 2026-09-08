package com.staywise.proximity_service.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {

    @Bean
    public GeoApiContext geoApiContext(
            @Value("${GOOGLE_MAPS_GECODING_API_KEY}")
            String apiKey) {

        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }
}
