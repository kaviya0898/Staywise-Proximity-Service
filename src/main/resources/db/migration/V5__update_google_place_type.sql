UPDATE amenity_categories
SET google_place_type = CASE category_name
    WHEN 'ATM' THEN 'atm'
    WHEN 'BANK' THEN 'bank'
    WHEN 'BUS_STOP' THEN 'bus_stop'
    WHEN 'COLLEGE' THEN 'university'
    WHEN 'GYM' THEN 'gym'
    WHEN 'HOSPITAL' THEN 'hospital'
    WHEN 'METRO' THEN 'subway_station'
    WHEN 'PARK' THEN 'park'
    WHEN 'PETROL_STATION' THEN 'gas_station'
    WHEN 'PHARMACY' THEN 'pharmacy'
    WHEN 'RAILWAY_STATION' THEN 'train_station'
    WHEN 'RESTAURANT' THEN 'restaurant'
    WHEN 'SCHOOL' THEN 'school'
    WHEN 'SHOPPING_MALL' THEN 'shopping_mall'
    WHEN 'SUPERMARKET' THEN 'supermarket'
END;