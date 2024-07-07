package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Driver;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRowMapper implements RowMapper<Driver> {

    @Override
    public Driver mapRow(ResultSet resultSet, int i) throws SQLException{
        Driver driver = new Driver();
        driver.setId(resultSet.getString("ID"));
        driver.setFirstName(resultSet.getString(("FirstName")));
        driver.setLastName(resultSet.getString(("LastName")));
        return driver;
    }
}
