package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusLocation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusLocationRowMapper implements RowMapper<BusLocation> {

    @Override
    public BusLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
        BusLocation loc = new BusLocation();
        loc.setBusNumber(rs.getInt("BusNumber"));
        loc.setLatitude(rs.getDouble("Latitude"));
        loc.setLongitude(rs.getDouble("Longitude"));
        return loc;
    }
}
