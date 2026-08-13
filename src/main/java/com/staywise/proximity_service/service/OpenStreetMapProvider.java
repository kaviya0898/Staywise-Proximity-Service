//package com.staywise.proximity_service.service;
//
//import com.staywise.proximity_service.client.OpenStreetMapClient;
//import com.staywise.proximity_service.dto.AmenityDto;
//import com.staywise.proximity_service.dto.OverpassElement;
//import com.staywise.proximity_service.dto.OverpassResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class OpenStreetMapProvider implements AmenityProvider{
//
//    private final OpenStreetMapClient openStreetMapClient;
//
//    @Override
//    public List<AmenityDto> getAmenities(double latitude, double longitude, int radiusMeters) {
//
//        String query= """
//                [out:json];
//                  (
//                                     nwr["amenity"="hospital"](around:%d,%f,%f);
//                                     nwr["amenity"="school"](around:%d,%f,%f);
//                                     nwr["amenity"="college"](around:%d,%f,%f);
//                                     nwr["shop"="supermarket"](around:%d,%f,%f);
//                                 );
//
//                                 out center;
//                """.formatted( radiusMeters, latitude, longitude,
//                radiusMeters, latitude, longitude,
//                radiusMeters, latitude, longitude,
//                radiusMeters, latitude, longitude
//        );
//        OverpassResponse response=openStreetMapClient.query(query);
//        return response.elements().stream()
//                .map(this::toAmenityDto)
//                .limit(2)
//                .toList();
//    }
//
//    private AmenityDto toAmenityDto(OverpassElement overpassElement) {
//        String category=resolveCategory(overpassElement);
//        String amenityName=overpassElement.tags().get("name");
//        Double latitude=overpassElement.lat()!=null? overpassElement.lat() : overpassElement.center().lat();
//        Double longitude= overpassElement.lon()!=null?overpassElement.lon(): overpassElement.center().lon();
//        return new AmenityDto(category,
//                              amenityName,
//                              latitude,
//                              longitude);
//    }
//
//    private String resolveCategory(OverpassElement element) {
//
//        if ("hospital".equals(element.tags().get("amenity")))
//            return "HOSPITAL";
//
//        if ("school".equals(element.tags().get("amenity")))
//            return "SCHOOL";
//
//        if ("college".equals(element.tags().get("amenity")))
//            return "COLLEGE";
//
//        if ("supermarket".equals(element.tags().get("shop")))
//            return "SUPERMARKET";
//
//        return "OTHER";
//    }
//}
