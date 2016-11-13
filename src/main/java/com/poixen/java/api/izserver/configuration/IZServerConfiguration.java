package com.poixen.java.api.izserver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Custom {@code DropWizard} {@link Configuration}.
 */
public class IZServerConfiguration extends Configuration {


    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @NotEmpty
    private String authenticationCachePolicy = "maximumSize=10000, expireAfterAccess=10m";

    @NotNull
    private Integer minUsernameLen;

    @NotNull
    private Integer minPasswordLen;

    @NotNull
    private Integer maxUsernameLen;

    @NotNull
    private Integer maxPasswordLen;

    @JsonProperty
    public CacheBuilderSpec getAuthenticationCachePolicy() {
        return CacheBuilderSpec.parse(authenticationCachePolicy);
    }

    @JsonProperty
    public void setAuthenticationCachePolicy(String authenticationCachePolicy) {
        this.authenticationCachePolicy = authenticationCachePolicy;
    }

    @JsonProperty
    public int getMinUsernameLen() {
        return minUsernameLen;
    }

    @JsonProperty
    public void setMinUsernameLen(int minUsernameLen) {
        this.minUsernameLen = minUsernameLen;
    }

    @JsonProperty
    public int getMinPasswordLen() {
        return minPasswordLen;
    }

    @JsonProperty
    public void setMinPasswordLen(int minPasswordLen) {
        this.minPasswordLen = minPasswordLen;
    }

    @JsonProperty
    public int getMaxUsernameLen() {
        return maxUsernameLen;
    }

    @JsonProperty
    public void setMaxUsernameLen(int maxUsernameLen) {
        this.maxUsernameLen = maxUsernameLen;
    }

    @JsonProperty
    public int getMaxPasswordLen() {
        return maxPasswordLen;
    }

    @JsonProperty
    public void setMaxPasswordLen(int maxPasswordLen) {
        this.maxPasswordLen = maxPasswordLen;
    }
}
