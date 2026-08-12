package com.staywise.proximity_service.dto;

import java.util.List;

public record GooglePlacesRequest(
        LocationRestriction locationRestriction,
        List<String> includedTypes,
        int maxResultCount
) {

    public record LocationRestriction(
            Circle circle
    ) {}

    public record Circle(
            Center center,
            double radius
    ) {}

    public record Center(
            double latitude,
            double longitude
    ) {}
}