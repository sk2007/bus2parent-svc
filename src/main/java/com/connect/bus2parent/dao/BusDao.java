package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BusDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Bus> getBuses() {
        return namedParameterJdbcTemplate.query("SELECT BusNumber, BusPlate FROM BUS", new HashMap<>(), new BusRowMapper());
    }

    /*public List<Bus> getBuses2() {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);
        return namedParameterJdbcTemplate.queryForList(
                "SELECT BusNumber, BusPlate FROM BUS", new HashMap<>(), Bus.class);


    }*/


}
