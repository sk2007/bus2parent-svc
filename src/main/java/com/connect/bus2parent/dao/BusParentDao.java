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
        return namedParameterJdbcTemplate.query("SELECT BusNumber, ParentEmail FROM BUS_PARENT", new HashMap<>(), new BusParentRowMapper());
    }
}
