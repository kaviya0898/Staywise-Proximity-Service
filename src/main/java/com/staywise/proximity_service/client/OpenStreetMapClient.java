package com.staywise.proximity_service.client;

import com.staywise.proximity_service.dto.OverpassResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="openstreetmap-client",url="${osm.overpass.url}")
public interface OpenStreetMapClient {

    @PostMapping(value="/interpreter",consumes="test/plain")
    OverpassResponse query(@RequestBody String query);
}
