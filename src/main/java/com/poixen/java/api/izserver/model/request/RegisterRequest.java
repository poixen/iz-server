package com.poixen.java.api.izserver.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Model for requesting registration
 */
public class RegisterRequest implements Request {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String name;
    private int age;

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public int getAge() {
        return age;
    }

    @JsonProperty
    public void setAge(int age) {
        this.age = age;
    }
}
