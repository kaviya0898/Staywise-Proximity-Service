package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.model.PropertyLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyLocationRepository extends JpaRepository<PropertyLocation,Long> {
}
