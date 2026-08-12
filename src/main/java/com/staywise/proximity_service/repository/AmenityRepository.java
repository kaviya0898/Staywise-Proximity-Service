package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
}
