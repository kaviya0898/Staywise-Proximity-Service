package com.staywise.proximity_service.service;

import com.staywise.common.dto.PropertyEventDto;
import com.staywise.proximity_service.dto.AmenityDto;
import com.staywise.proximity_service.model.Amenity;
import com.staywise.proximity_service.model.AmenityCategories;
import com.staywise.proximity_service.model.PropertyAmenities;
import com.staywise.proximity_service.model.PropertyAmenityId;
import com.staywise.proximity_service.repository.AmenityCategoriesRepository;
import com.staywise.proximity_service.repository.AmenityRepository;
import com.staywise.proximity_service.repository.PropertyAmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityService {
    private final AmenityCategoriesRepository amenityCategoriesRepository;
    private final AmenityRepository amenityRepository;
    private final PropertyAmenityRepository propertyAmenityRepository;

    public void saveAmenities(List<AmenityDto> propertyAmenities, PropertyEventDto propertyEventDto)
    {
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
            PropertyAmenityId propertyAmenityId=new PropertyAmenityId(propertyEventDto.propertyId(), amenity.getAmenityId());

            PropertyAmenities propertyAmenity=new PropertyAmenities();
            propertyAmenity.setId(propertyAmenityId);
            propertyAmenity.setDistanceMeters(distance);

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

}
