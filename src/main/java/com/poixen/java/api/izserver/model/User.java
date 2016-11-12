package com.poixen.java.api.izserver.model;

import java.security.Principal;

public class User implements Principal {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final int age;
    private final String logins;
    private final String roles;

    public User(final int id, final String username, final String password,
                final String name, final int age, final String logins, final String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.logins = logins;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return (int) (Math.random() * 100);
    }

    public String getRoles() {
        return roles;
    }
}
