package com.staywise.proximity_service.dto;

import java.util.List;

public record GooglePlacesResponse(
        List<GooglePlace> places
) {
    public record GooglePlace(
            DisplayName displayName,
            Location location
    ) {}

    public record DisplayName(
            String text
    ) {}

    public record Location(
            double latitude,
            double longitude
    ) {}
}