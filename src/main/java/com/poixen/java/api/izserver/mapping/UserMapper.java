package com.poixen.java.api.izserver.mapping;

import com.poixen.java.api.izserver.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * {@link ResultSetMapper} for mapping a {@link User}
 */
public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        // TODO: 14/11/16 There are probably a lot better ways of handling this, would refactor with more time
        Array successfulLogins = r.getArray("successful_logins");
        Array failedLogins = r.getArray("failed_logins");
        Array roles = r.getArray("roles");
        String[] successfulLoginsArray =  successfulLogins == null ?
                new String[]{} : (String[])successfulLogins.getArray();
        String[] failedLoginsArray =  failedLogins == null ?
                new String[]{} : (String[])failedLogins.getArray();
        String[] rolesArray =  roles == null ?
                new String[]{} : (String[])roles.getArray();

        return new User(r.getInt("id"),
                r.getString("username"),
                r.getString("password"),
                r.getString("name"),
                r.getInt("age"),
                Arrays.asList(successfulLoginsArray),
                Arrays.asList(failedLoginsArray),
                Arrays.asList(rolesArray));
    }
}
