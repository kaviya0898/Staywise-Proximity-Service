package com.staywise.proximity_service.service;

import com.staywise.proximity_service.client.GooglePlacesClient;
import com.staywise.proximity_service.dto.AmenityDto;
import com.staywise.proximity_service.dto.GooglePlacesRequest;
import com.staywise.proximity_service.dto.GooglePlacesResponse;
import com.staywise.proximity_service.model.AmenityCategories;
import com.staywise.proximity_service.repository.AmenityCategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class GooglePlacesProvider implements AmenityProvider{
    private final AmenityCategoriesRepository amenityCategoriesRepository;
    private final ExecutorService placesExecutor;

    @Value("${GOOGLE_MAPS_GECODING_API_KEY}")
    String apiKey;

    private final GooglePlacesClient googlePlacesClient;
    private static final String FIELD_MASK = "places.displayName,places.location";

    long start = System.nanoTime();
    @Override
    public List<AmenityDto> getAmenities(double latitude, double longitude, int radiusMeters) {
       //sequential(latitude,longitude,radiusMeters);
      return parallelAmenities(latitude,longitude,radiusMeters);

    }

    private List<AmenityDto> parallelAmenities(double latitude, double longitude, int radiusMeters) {
        long totalStart = System.nanoTime();
        log.info("Before fetching categories");
        List<AmenityCategories> amenityCategoriesList=amenityCategoriesRepository.findAll();
        log.info("Categories fetched: {}", amenityCategoriesList.size());

        List<CompletableFuture<List<AmenityDto>>> futures=amenityCategoriesList.stream()
                .map(category->CompletableFuture.supplyAsync(()->fetchCategory(category.getGooglePlaceType(), latitude,longitude,radiusMeters),placesExecutor)
                ).toList();

        List<AmenityDto> amenities =
                futures.stream()
                        .map(future ->
                                future.exceptionally(ex -> {
                                    log.error("Category failed", ex);
                                    return List.<AmenityDto>of();
                                })
                        )
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .toList();
        long latency = (System.nanoTime() - totalStart) / 1_000_000;
        log.info("Parallel Google Places latency: {} ms", latency);

        return amenities;

    }

    private List<AmenityDto> fetchCategory(String categoryName, double latitude, double longitude, int radiusMeters) {

        GooglePlacesRequest request =
                new GooglePlacesRequest(
                        new GooglePlacesRequest.LocationRestriction(
                                new GooglePlacesRequest.Circle(
                                        new GooglePlacesRequest.Center(
                                                latitude,
                                                longitude
                                        ),
                                        radiusMeters
                                )
                        ),
                        List.of(categoryName),
                        5
                );

        long start = System.nanoTime();
        GooglePlacesResponse response=googlePlacesClient.searchNearby(apiKey,FIELD_MASK,request);
        long latency = (System.nanoTime() - start) / 1_000_000;

        log.info("{} API latency: {} ms", categoryName, latency);
        if(response.places()==null)
            return List.of();
        return response.places()
                .stream().map(place->new AmenityDto(
                        categoryName.toUpperCase(),
                        place.displayName().text(),
                        place.location().latitude(),
                        place.location().longitude()
                ))
                .toList();

    }
    List<AmenityDto> sequential(Double latitude,Double longitude,int radiusMeters)
    {
        long totalStart = System.nanoTime();
        log.info("Before fetching categories");
        List<AmenityCategories> amenityCategoriesList=amenityCategoriesRepository.findAll();
        log.info("Categories fetched: {}", amenityCategoriesList.size());

        List<AmenityDto> amenities=new ArrayList<>();
        long apiStart = System.nanoTime();
        for(AmenityCategories category:amenityCategoriesList)
        {
            log.info("Calling Google for: {}", category.getGooglePlaceType());
            amenities.addAll(fetchCategory(category.getGooglePlaceType(),latitude,longitude,radiusMeters));
        }
        long apiTotal = (System.nanoTime() - apiStart) / 1_000_000;

        log.info("All Google API calls total: {} ms", apiTotal);
        long totalLatency = (System.nanoTime() - totalStart) / 1_000_000;

        log.info("TOTAL sequential latency: {} ms", totalLatency);
        return amenities;
    }
}
