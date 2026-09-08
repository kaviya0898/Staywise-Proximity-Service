package com.staywise.proximity_service.client;

import com.staywise.proximity_service.config.GooglePlacesFeignConfig;
import com.staywise.proximity_service.dto.GooglePlacesRequest;
import com.staywise.proximity_service.dto.GooglePlacesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="google-palces-client",url="https://places.googleapis.com", configuration = GooglePlacesFeignConfig.class)
public interface GooglePlacesClient {

    @PostMapping(value="/v1/places:searchNearby",consumes="application/json")
    GooglePlacesResponse searchNearby(@RequestHeader("X-Goog-Api-Key") String placesApiKey, @RequestHeader("X-Goog-FieldMask")
    String fieldMask, @RequestBody GooglePlacesRequest googlePlacesRequest);
}
