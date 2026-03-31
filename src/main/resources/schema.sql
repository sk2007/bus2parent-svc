-- Run this against the BUS2PARENT MySQL database to create required tables.
-- Existing tables (BUS, DRIVER, PARENT, BUS_PARENT) assumed to already exist.

CREATE TABLE IF NOT EXISTS BUS_LOCATION (
    BusNumber INT NOT NULL PRIMARY KEY,
    Latitude  DOUBLE NOT NULL,
    Longitude DOUBLE NOT NULL,
    UpdatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bus_location_bus FOREIGN KEY (BusNumber) REFERENCES BUS(BusNumber)
);
