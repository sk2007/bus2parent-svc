package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BusDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Bus> getBuses() {
        return namedParameterJdbcTemplate.query("SELECT BusNumber, BusPlate FROM BUS", new HashMap<>(), new BusRowMapper());
    }

    public int createBus(Bus b) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", b.busNumber());
        params.put("BusPlate", b.busPlate());

        int rowsAffected = namedParameterJdbcTemplate.update("INSERT INTO Bus (BusPlate, BusNumber) VALUES (:BusPlate, :BusNumber)", params);
        return rowsAffected;
    }

    public Bus getBus(int BusNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", BusNumber);

        return namedParameterJdbcTemplate.queryForObject("select BusPlate, BusNumber from BUS where BusNumber=:BusNumber", params, new BusRowMapper());
    }

    public int getNewBusNumber() {
        int max = namedParameterJdbcTemplate.queryForObject("SELECT MAX(BusNumber) FROM BUS", new HashMap<>(), Integer.class);
        return (max + 1);
    }

    public int removeBus(int number) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", number);

        int rowsAffected = namedParameterJdbcTemplate.update("DELETE FROM BUS WHERE BusNumber=:BusNumber", params);
        return rowsAffected;
    }

    public void updateBusPlate(int busNumber, String busPlate) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        params.put("BusPlate", busPlate);
        namedParameterJdbcTemplate.update("UPDATE BUS SET BusPlate=:BusPlate WHERE BusNumber=:BusNumber", params);
    }
}
