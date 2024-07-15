package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.print.attribute.HashPrintJobAttributeSet;
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

    public Driver getDriver(int id) {
        System.out.println("id received: " + id);
        Map<String, Object> paramMap= new HashMap<>();
        paramMap.put("ID", id);

        return namedParameterJdbcTemplate.queryForObject("select ID, LastName, FirstName from DRIVER where ID=:ID", paramMap, new DriverRowMapper());
    }

    public int createDriver(Driver myDriver) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LastName", myDriver.lastName());
        parameters.put("FirstName", myDriver.firstName());
        parameters.put("ID", myDriver.id());

        int rowsAffected = namedParameterJdbcTemplate.update("INSERT INTO DRIVER (ID, LastName, FirstName) VALUES (:ID, :LastName, :FirstName)", parameters);
        System.out.println("rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public int getNewID() {
        int max = namedParameterJdbcTemplate.queryForObject("SELECT MAX(ID) FROM DRIVER", new HashMap<>(), Integer.class);
        return (max + 1);
    }

    public int removeDriver(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("ID", id);

        int rowsAffected = namedParameterJdbcTemplate.update("DELETE FROM DRIVER WHERE ID=:ID", params);
        return rowsAffected;
    }

    public void updateDriver(int id, Driver driver) {
        Map<String, Object> params = new HashMap<>();
        params.put("ID", id);
        params.put("FirstName", driver.firstName());
        params.put("LastName", driver.lastName());

        namedParameterJdbcTemplate.update("UPDATE DRIVER SET FirstName=:FirstName, LastName=:LastName WHERE ID=:ID", params);
    }
}
