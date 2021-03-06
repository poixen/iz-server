package com.poixen.java.api.izserver.model.dao;

import com.poixen.java.api.izserver.mapping.UserMapper;
import com.poixen.java.api.izserver.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * DAO interface for the user table
 */
@RegisterMapper(UserMapper.class)
public interface UserDAO {

    /**
     * Insert a new user into the table
     */
    @SqlUpdate("INSERT INTO users (username, password, name, age, successful_logins, failed_logins, roles) values (:username, :password, :name, :age, null, null, '{\"USER\"}')")
    void insert(@Bind("username") String username, @Bind("password") String password, @Bind("name") String name, @Bind("age") int age);


    /**
     * Append the current timestamp to the {@code successful_login} table
     *
     * @param username the username of the user to append
     */
    @SqlUpdate("UPDATE users SET successful_logins=ARRAY_APPEND((SELECT successful_logins FROM users WHERE username=:username),:timestamp) WHERE username=:username")
    void addSuccessfulLogin(@Bind("timestamp") String timestamp, @Bind("username") String username);


    /**
     * Append the current timestamp to the {@code failed_logins} table
     *
     * @param username the username of the user to append
     */
    @SqlUpdate("UPDATE users SET failed_logins=ARRAY_APPEND((SELECT failed_logins FROM users WHERE username=:username),:timestamp) WHERE username=:username")
    void addFailedLogin(@Bind("timestamp") String timestamp, @Bind("username") String username);


    /**
     * Returns the {@link User} by their {@code username}
     *
     * @param username username of the user to fetch {@link User} information for
     * @return the {@link User} by thier {@code username}
     */
    @SqlQuery("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUser(@Bind("username") String username);

    /**
     * Returns a {@link User} by their {@code username} and {@code password}
     *
     * @param username username of the user
     * @param password password of the user
     * @return a {@link User} by their {@code username} and {@code password}
     */
    @SqlQuery("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User getUser(@Bind("username") String username, @Bind("password") String password);

    /**
     * Close with no args is used to close the connection
     */
    void close();
}
