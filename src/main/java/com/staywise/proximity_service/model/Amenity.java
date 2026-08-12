package com.staywise.proximity_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Long amenityId;

    @Column(name="amenity_name",nullable = false)
    private String amenityName;

    @Column(name="latitude",nullable = false)
    private Double latitude;

    @Column(name="longitude",nullable = false)
    private Double longitude;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated-at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    private AmenityCategories category;
}
