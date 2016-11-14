package com.poixen.java.api.izserver.mapping;

import com.poixen.java.api.izserver.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link ResultSetMapper} for mapping a {@link User}
 */
public class UserMapper implements ResultSetMapper<User> {
    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        List<String> sucessfulLogins = r.getString("successful_logins") == null ?
                new ArrayList<>() : Arrays.asList(r.getString("successful_logins").split(","));
        List<String> failedLogins = r.getString("failed_logins") == null ?
                new ArrayList<>() : Arrays.asList(r.getString("failed_logins").split(","));
        List<String> roles = r.getString("roles") == null ?
                new ArrayList<>() : Arrays.asList(r.getString("roles").split(","));

        return new User(r.getInt("id"),
                r.getString("username"),
                r.getString("password"),
                r.getString("name"), r.getInt("age"),
                sucessfulLogins,
                failedLogins,
                roles);
    }
}
