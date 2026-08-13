package com.staywise.proximity_service.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PropertyAmenityId implements Serializable {

    private Long propertyId;
    private Long amenityId;
}
