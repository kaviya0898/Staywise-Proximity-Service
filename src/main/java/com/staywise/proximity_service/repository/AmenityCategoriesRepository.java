package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.model.AmenityCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityCategoriesRepository extends JpaRepository<AmenityCategories,Long> {
}
