package com.staywise.proximity_service.service;

import com.staywise.proximity_service.dto.AmenityDto;

import java.util.List;

public interface AmenityProvider {

    public List<AmenityDto> getAmenities(double latitude,double longitude,int radiusMeters);
}
