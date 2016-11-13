package com.poixen.java.api.izserver.model;

import java.security.Principal;

public class User implements Principal {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final int age;
    private final String[] successfulLogins;
    private final String[] failedLogins;
    private final String[] roles;

    public User(final int id, final String username, final String password,
                final String name, final int age, final String[] successfulLogins,
                final String[] failedLogins, final String[] roles) {
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

    public String[] getRoles() {
        return roles;
    }

    public String getUsername() { return username; }

    public String[] getSuccessfulLogins() {return successfulLogins;}

    public String[] getFailedLogins() {return failedLogins;}
}
