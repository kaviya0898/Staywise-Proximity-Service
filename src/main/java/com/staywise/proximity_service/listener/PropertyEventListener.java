package com.staywise.proximity_service.listener;

import com.staywise.common.dto.PropertyEventDto;
import com.staywise.proximity_service.service.AmenityProvider;
import com.staywise.proximity_service.service.AmenityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
@Component
@RequiredArgsConstructor
@Slf4j
public class PropertyEventListener {


    private final AmenityService amenityService;

    @RabbitListener(queues="staywise.property.published.queue")
    public void handlePropertyEvent(PropertyEventDto propertyEventDto)
    {
        log.info("Received property event: {}", propertyEventDto);
        amenityService.getAmenities(propertyEventDto,5000);

    }
}
