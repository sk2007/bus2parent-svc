package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BusLocationDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BusLocation> getAllLocations() {
        return namedParameterJdbcTemplate.query(
            "SELECT BusNumber, Latitude, Longitude FROM BUS_LOCATION",
            new HashMap<>(),
            new BusLocationRowMapper()
        );
    }

    public BusLocation getLocation(int busNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        try {
            return namedParameterJdbcTemplate.queryForObject(
                "SELECT BusNumber, Latitude, Longitude FROM BUS_LOCATION WHERE BusNumber=:BusNumber",
                params,
                new BusLocationRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int upsertLocation(BusLocation location) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", location.busNumber());
        params.put("Latitude", location.latitude());
        params.put("Longitude", location.longitude());
        return namedParameterJdbcTemplate.update(
            "INSERT INTO BUS_LOCATION (BusNumber, Latitude, Longitude) VALUES (:BusNumber, :Latitude, :Longitude) " +
            "ON DUPLICATE KEY UPDATE Latitude=:Latitude, Longitude=:Longitude",
            params
        );
    }
}
