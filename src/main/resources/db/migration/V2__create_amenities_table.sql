CREATE TABLE amenities (
    amenity_id BIGINT NOT NULL AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    amenity_name VARCHAR(200) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_amenities
        PRIMARY KEY (amenity_id),

    CONSTRAINT fk_amenities_category
        FOREIGN KEY (category_id)
        REFERENCES amenity_categories(category_id)
);