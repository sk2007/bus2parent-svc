package com.connect.bus2parent.dao;

import com.connect.bus2parent.domain.Parent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParentRowMapper implements RowMapper<Parent> {

    @Override
    public Parent mapRow(ResultSet resultSet, int i) throws SQLException {
        Parent parent = new Parent();
        parent.setFirstName(resultSet.getString("FirstName"));
        parent.setLastName(resultSet.getString("LastName"));
        parent.setEmailAddress(resultSet.getString("EmailAddress"));
        return parent;
    }

}
