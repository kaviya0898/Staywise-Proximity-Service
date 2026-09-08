package com.staywise.proximity_service.dto;

public record AmenityDto(String categoryName,
                         String amenityName,
                         Double latitude,
                         Double longitude) {
}
