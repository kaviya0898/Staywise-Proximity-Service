package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.model.Amenity;
import com.staywise.proximity_service.model.AmenityCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmenityCategoriesRepository extends JpaRepository<AmenityCategories,Long> {
    Optional<AmenityCategories> findByCategoryName(String categoryName);

   Optional<AmenityCategories> findByGooglePlaceType(String lowerCase);
}
