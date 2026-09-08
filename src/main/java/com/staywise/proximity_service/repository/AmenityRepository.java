package com.staywise.proximity_service.repository;

import com.staywise.proximity_service.model.Amenity;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {


    Optional<Amenity> findByAmenityNameAndCategory_CategoryId(String s, Long categoryId);
}
