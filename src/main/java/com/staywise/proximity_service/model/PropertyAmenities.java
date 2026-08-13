package com.staywise.proximity_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="property_amenities")
public class PropertyAmenities {

   @EmbeddedId
    private PropertyAmenityId id;

   @Column(nullable = false,name = "distance_meters")
    private Double distanceMeters;
}
