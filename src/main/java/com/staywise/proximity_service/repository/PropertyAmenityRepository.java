package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.model.PropertyAmenities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyAmenityRepository extends JpaRepository<PropertyAmenities,Long> {
}
