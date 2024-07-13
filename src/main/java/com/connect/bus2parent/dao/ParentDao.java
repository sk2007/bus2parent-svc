package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ParentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Parent> getParents() {
        return namedParameterJdbcTemplate.query("SELECT FirstName, LastName, EmailAddress FROM PARENT", new HashMap<>(), new ParentRowMapper());
    }

    public Parent getParent(String emailAddress) {
        Map<String, Object> params= new HashMap<>();
        params.put("EmailAddress", emailAddress);

        return namedParameterJdbcTemplate.queryForObject("select FirstName, LastName, EmailAddress from PARENT where EmailAddress=:EmailAddress", params, new ParentRowMapper());
    }
    public int createParent(Parent parent) {
        Map<String, Object> params = new HashMap<>();
        params.put("FirstName", parent.firstName());
        params.put("LastName", parent.lastName());
        params.put("EmailAddress", parent.emailAddress());

        int rowsAffected = namedParameterJdbcTemplate.update("INSERT INTO PARENT (FirstName, LastName, EmailAddress) VALUES (:FirstName, :LastName, :EmailAddress)", params);
        return rowsAffected;
    }

    public int removeParent(String emailAddress) {
        Map<String, Object> params = new HashMap<>();
        params.put("EmailAddress", emailAddress);

        int rowsAffected = namedParameterJdbcTemplate.update("DELETE FROM PARENT WHERE EmailAddress=:EmailAddress", params);
        return rowsAffected;
    }
}
