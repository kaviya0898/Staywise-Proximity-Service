package com.staywise.proximity_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="amenity_categories")
public class AmenityCategories {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name="google_place_type")
    private String googlePlaceType;

    @OneToMany(mappedBy = "category")
    private List<Amenity> amenities;

}
