package com.poixen.java.api.izserver.model;

import java.security.Principal;
import java.util.List;

/**
 * Data model for representing a user
 */
public class User implements Principal {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final int age;
    private final List<String> successfulLogins;
    private final List<String> failedLogins;
    private final List<String> roles;

    public User(final int id, final String username, final String password,
                final String name, final int age, final List<String> successfulLogins,
                final List<String> failedLogins, final List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.successfulLogins = successfulLogins;
        this.failedLogins = failedLogins;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getPassword() { return password;}

    public List<String> getRoles() {
        return roles;
    }

    public String getUsername() { return username; }

    public List<String> getSuccessfulLogins() {return successfulLogins;}

    public List<String> getFailedLogins() {return failedLogins;}
}
