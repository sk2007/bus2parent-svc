package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.BusParent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusParentRowMapper implements  RowMapper<BusParent> {

    @Override
    public BusParent mapRow(ResultSet resultSet, int i) throws SQLException {
        BusParent busParent = new BusParent();
        busParent.setBusID(resultSet.getInt("BusNumber"));
        busParent.setParentEmail(resultSet.getString("ParentEmail"));
        return busParent;
    }
}
