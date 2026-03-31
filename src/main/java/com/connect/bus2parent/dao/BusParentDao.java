package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusParent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BusParentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BusParent> getBusParents() {
        return namedParameterJdbcTemplate.query(
            "SELECT BusNumber, ParentEmail FROM BUS_PARENT",
            new HashMap<>(),
            new BusParentRowMapper()
        );
    }

    public List<BusParent> getBusParentsByBus(int busNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        return namedParameterJdbcTemplate.query(
            "SELECT BusNumber, ParentEmail FROM BUS_PARENT WHERE BusNumber=:BusNumber",
            params,
            new BusParentRowMapper()
        );
    }

    public int createBusParent(BusParent busParent) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busParent.busID());
        params.put("ParentEmail", busParent.parentEmail());
        return namedParameterJdbcTemplate.update(
            "INSERT INTO BUS_PARENT (BusNumber, ParentEmail) VALUES (:BusNumber, :ParentEmail)",
            params
        );
    }

    public int removeBusParent(int busNumber, String parentEmail) {
        Map<String, Object> params = new HashMap<>();
        params.put("BusNumber", busNumber);
        params.put("ParentEmail", parentEmail);
        return namedParameterJdbcTemplate.update(
            "DELETE FROM BUS_PARENT WHERE BusNumber=:BusNumber AND ParentEmail=:ParentEmail",
            params
        );
    }
}
