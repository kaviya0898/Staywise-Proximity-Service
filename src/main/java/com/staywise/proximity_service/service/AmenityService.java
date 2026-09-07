package com.staywise.proximity_service.service;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.util.BoundingBoxGeoHashIterator;
import ch.hsr.geohash.util.TwoGeoHashBoundingBox;
import com.staywise.common.dto.PropertyEventDto;
import com.staywise.proximity_service.dto.AmenityDto;
import com.staywise.proximity_service.dto.NearbyAmenityDto;
import com.staywise.proximity_service.model.*;
import com.staywise.proximity_service.repository.AmenityCategoriesRepository;
import com.staywise.proximity_service.repository.AmenityRepository;
import com.staywise.proximity_service.repository.PropertyAmenityRepository;
import com.staywise.proximity_service.repository.PropertyLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmenityService {
    private static final int GEOHASH_PRECISION = 5;
    private final AmenityCategoriesRepository amenityCategoriesRepository;
    private final AmenityRepository amenityRepository;
    private final PropertyAmenityRepository propertyAmenityRepository;
    private final AmenityProvider amenityProvider;
    private final PropertyLocationRepository propertyLocationRepository;

    public void saveAmenities(List<AmenityDto> propertyAmenities, PropertyEventDto propertyEventDto)
    {
        String propertyGeohash= GeoHash.withCharacterPrecision(propertyEventDto.latitude(),propertyEventDto.longitude(),GEOHASH_PRECISION)
                .toBase32();
        for(AmenityDto dto:propertyAmenities)
        {
            AmenityCategories category=amenityCategoriesRepository.findByGooglePlaceType(dto.categoryName())
                    .orElseThrow(()->new RuntimeException("Category not found:"+ dto.categoryName()));

            Amenity amenity=amenityRepository.findByAmenityNameAndCategory_CategoryId(dto.amenityName(),category.getCategoryId())
                    .orElseGet(()->{
                        Amenity newAmenity=new Amenity();
                        newAmenity.setAmenityName(dto.amenityName());
                        newAmenity.setCategory(category);
                        newAmenity.setLatitude(dto.latitude());
                        newAmenity.setLongitude(dto.longitude());
                        newAmenity.setCreatedAt(LocalDateTime.now());
                        newAmenity.setUpdatedAt(LocalDateTime.now());
                        return amenityRepository.save(newAmenity);
                    });
            double distance=calculateDistance(propertyEventDto.latitude(),propertyEventDto.longitude(), dto.latitude(),dto.longitude());
            PropertyLocation propertyLocation=new PropertyLocation();
            propertyLocation.setPropertyId(propertyEventDto.propertyId());
            GeometryFactory geometryFactory=new GeometryFactory();
            Point point=geometryFactory.createPoint(new Coordinate(propertyEventDto.longitude(),propertyEventDto.latitude()));
            propertyLocation.setLocation(point);
            propertyLocationRepository.save(propertyLocation);
            PropertyAmenityId propertyAmenityId=new PropertyAmenityId(propertyEventDto.propertyId(), amenity.getAmenityId());

            PropertyAmenities propertyAmenity=new PropertyAmenities();
            propertyAmenity.setId(propertyAmenityId);
            propertyAmenity.setDistanceMeters(distance);
            propertyAmenity.setGeoHash(propertyGeohash);

            propertyAmenityRepository.save(propertyAmenity);

        }
    }
    private double calculateDistance(Double propertyLatitude, Double propertyLongitude, Double amenityLatitude, Double amenityLongitude) {
        final double EARTH_RADIUS = 6371000;

        double latitudeDistance = Math.toRadians(amenityLatitude - propertyLatitude);

        double longitudeDistance = Math.toRadians(amenityLongitude - propertyLongitude);

        double a = Math.sin(latitudeDistance / 2)
                * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(propertyLatitude))
                * Math.cos(Math.toRadians(amenityLatitude))
                * Math.sin(longitudeDistance / 2)
                * Math.sin(longitudeDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public void getAmenities(PropertyEventDto propertyEventDto, int radius)
    {
        String propertyGeohash= GeoHash.withCharacterPrecision(propertyEventDto.latitude(),propertyEventDto.longitude(),GEOHASH_PRECISION)
                                .toBase32();
        List<String> candidates=getCandidates(propertyEventDto.latitude(),propertyEventDto.longitude(),radius);
        log.info("Property Geohash: {}", propertyGeohash);
        log.info("Checking database for geohash:{}",propertyGeohash);

        List<PropertyAmenities> amenities=propertyAmenityRepository.findByGeoHashIn(candidates);
        if(amenities.isEmpty())
        {
            log.info("No amenities found for this property.Calling places API");
            List<AmenityDto> finalAmenities=amenityProvider.fetchPlaces(propertyEventDto,radius);
            saveAmenities(finalAmenities,propertyEventDto);
        }
        else {
            List<Long> amenityIds = amenities.stream()
                    .map(propertyAmenities -> propertyAmenities.getId().getAmenityId())
                    .toList();
            List<Amenity> propertyAmenities = amenityRepository.findAllById(amenityIds);

            log.info("Calculating which amenity IDs within the geohash");
            List<NearbyAmenityDto> amenityDtoList = propertyAmenities.stream()
                    .map(amenity -> {
                        double distance = calculateDistance(
                                propertyEventDto.latitude(),
                                propertyEventDto.longitude(),
                                amenity.getLatitude(),
                                amenity.getLongitude()
                        );
                        return new NearbyAmenityDto(
                                amenity.getAmenityId(),
                                distance
                        );
                    })
                    .filter(dto->dto.dixtanceMeters()<=radius)
                    .toList();

            for(NearbyAmenityDto dto:amenityDtoList) {
                PropertyAmenities propertyAmenity = new PropertyAmenities();
                propertyAmenity.setId(new PropertyAmenityId(propertyEventDto.propertyId(), dto.amenityId() ));
                propertyAmenity.setGeoHash(propertyGeohash);
                propertyAmenity.setDistanceMeters(dto.dixtanceMeters());
                propertyAmenityRepository.save(propertyAmenity);
            }

        }

    }

    private List<String> getCandidates(Double latitude, Double longitude, int radiusMeters) {

        double latDelta = radiusMeters / 111000.0;
        double lngDelta = radiusMeters / (111000.0 * Math.cos(Math.toRadians(latitude)));
        BoundingBox bbox = new BoundingBox(
                latitude - latDelta,
                latitude + latDelta,
                longitude - lngDelta,
                longitude + lngDelta
        );
        TwoGeoHashBoundingBox twoHashes = TwoGeoHashBoundingBox.withCharacterPrecision(bbox, GEOHASH_PRECISION);
        Set<String> candidateGeohashes = new HashSet<>();

        BoundingBoxGeoHashIterator iterator = new BoundingBoxGeoHashIterator(twoHashes);

        while (iterator.hasNext())
        {
            candidateGeohashes.add(iterator.next().toBase32());
        }

        return new ArrayList<>(candidateGeohashes);



    }
}
