ALTER TABLE property_amenities
ADD COLUMN geohash VARCHAR(12);

CREATE INDEX idx_property_amenities_geohash
ON property_amenities(geohash);