package com.staywise.proximity_service.service;

import com.staywise.proximity_service.dto.AmenityDto;
import com.staywise.common.dto.PropertyEventDto;
import java.util.List;

public interface AmenityProvider {

    public void  getAmenities(PropertyEventDto propertyEventDto,int radiusInMeter);
}
