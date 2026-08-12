package com.staywise.proximity_service.dto;

import java.util.List;

public record OverpassResponse(List<OverpassElement> elements) {
}
