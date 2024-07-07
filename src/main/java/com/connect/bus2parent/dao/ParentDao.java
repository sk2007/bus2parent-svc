package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ParentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Parent> getParents() {
        return namedParameterJdbcTemplate.query("SELECT FirstName, LastName, EmailAddress FROM PARENT", new HashMap<>(), new ParentRowMapper());
    }
}
