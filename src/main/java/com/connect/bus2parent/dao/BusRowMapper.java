package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Bus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusRowMapper implements RowMapper<Bus> {

    @Override
    public Bus mapRow(ResultSet resultSet, int i) throws SQLException {
        Bus bus = new Bus();
        bus.setBusNumber(resultSet.getInt("BusNumber"));
        bus.setBusPlate(resultSet.getString("BusPlate"));
        return bus;
    }
}
