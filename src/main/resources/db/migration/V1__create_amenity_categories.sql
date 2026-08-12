CREATE TABLE amenity_categories (
    category_id BIGINT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL,

    PRIMARY KEY (category_id),
    CONSTRAINT uk_amenity_category_name UNIQUE (category_name)
);

INSERT INTO amenity_categories (category_name)
VALUES
    ('HOSPITAL'),
    ('SCHOOL'),
    ('COLLEGE'),
    ('SUPERMARKET'),
    ('METRO'),
    ('BUS_STOP'),
    ('RAILWAY_STATION'),
    ('PARK'),
    ('PHARMACY'),
    ('RESTAURANT'),
    ('GYM'),
    ('BANK'),
    ('ATM'),
    ('PETROL_STATION'),
    ('SHOPPING_MALL');