CREATE TABLE property_locations (
    property_id BIGINT NOT NULL,
    location POINT NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_property_locations
        PRIMARY KEY (property_id),

    SPATIAL INDEX idx_property_locations_location (location)
);