package com.poixen.java.api.izserver.mapping;

import com.poixen.java.api.izserver.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link ResultSetMapper} for mapping a {@link User}
 */
public class UserMapper implements ResultSetMapper<User> {
    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new User(r.getInt("id"), r.getString("username"), r.getString("password"),
                r.getString("name"), r.getInt("age"), r.getString("logins"), r.getString("roles"));
    }
}
