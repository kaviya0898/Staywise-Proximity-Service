package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.dto.AmenityDto;
import com.staywise.proximity_service.model.Amenity;
import com.staywise.proximity_service.model.PropertyAmenities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenities,Long> {
    List<Amenity> findByGeoHash(String propertyGeohash);

    List<PropertyAmenities> findByGeoHashIn(List<String> candidates);
}
