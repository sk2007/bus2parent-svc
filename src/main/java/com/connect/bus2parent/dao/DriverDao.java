package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DriverDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Driver> getDrivers() {
        return namedParameterJdbcTemplate.query("SELECT ID, LastName, FirstName FROM DRIVER", new HashMap<>(), new DriverRowMapper());
    }

    public Driver getDriver(String id) {
        System.out.println("id received: " + id);
        Map<String, String> paramMap= new HashMap<>();
        paramMap.put("ID", id);

        return namedParameterJdbcTemplate.queryForObject("select ID, LastName, FirstName from DRIVER where ID=:ID", paramMap, new DriverRowMapper());
    }

    public int createDriver(Driver myDriver) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("LastName", myDriver.lastName());
        parameters.put("FirstName", myDriver.firstName());
        parameters.put("ID", myDriver.id());

        int rowsAffected = namedParameterJdbcTemplate.update("INSERT INTO DRIVER (ID, LastName, FirstName) VALUES (:ID, :LastName, :FirstName)", parameters);
        System.out.println("rows affected: " + rowsAffected);
        return rowsAffected;
    }
}
