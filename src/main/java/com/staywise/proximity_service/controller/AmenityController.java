package com.staywise.proximity_service.controller;

import com.staywise.proximity_service.dto.AmenityDto;
import com.staywise.proximity_service.service.AmenityProvider;
import com.staywise.proximity_service.service.OpenStreetMapProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/amenities")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityProvider amenityProvider;

    @PostMapping("/nearby")
    public List<AmenityDto> getNearBy( @RequestParam double latitude,
                                       @RequestParam double longitude,
                                       @RequestParam int radiusMeters)
    {
        return amenityProvider.getAmenities(latitude,longitude,radiusMeters);
    }

}
