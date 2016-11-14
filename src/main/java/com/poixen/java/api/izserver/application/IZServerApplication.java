package com.poixen.java.api.izserver.application;

import com.poixen.java.api.izserver.authentication.BasicAuthenticator;
import com.poixen.java.api.izserver.authentication.BasicAuthorizer;
import com.poixen.java.api.izserver.configuration.IZServerConfiguration;
import com.poixen.java.api.izserver.model.User;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import com.poixen.java.api.izserver.model.request.validators.LoginRequestValidator;
import com.poixen.java.api.izserver.model.request.validators.RegisterRequestValidator;
import com.poixen.java.api.izserver.model.request.validators.RequestValidator;
import com.poixen.java.api.izserver.resource.LoginLogsResource;
import com.poixen.java.api.izserver.resource.LoginResource;
import com.poixen.java.api.izserver.resource.RegisterResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

/**
 * Entry point for application. Initializes and runs the {@link Application}.
 * Will register components for use, such as resources, auth, health checks...
 */
public class IZServerApplication extends Application<IZServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new IZServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "iz-server";
    }

    @Override
    public void initialize(Bootstrap<IZServerConfiguration> bootstrap) {
    }

    @Override
    public void run(IZServerConfiguration config, Environment env) throws Exception {

        // create a new managed connection pool, health check for connectivity, and a new DBI instance.
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, config.getDataSourceFactory(), "postgresql");
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        env.jersey().register(new DBIExceptionsBundle());

        // create validator for RegisterResource, create RegisterResource, register RegisterResource
        RequestValidator registerRequestValidator = new RegisterRequestValidator(config.getMinPasswordLen(), config.getMaxPasswordLen(),
                config.getMinUsernameLen(), config.getMaxUsernameLen());
        final RegisterResource registerResource = new RegisterResource(registerRequestValidator, userDAO);
        env.jersey().register(registerResource);

        // create validator for LoginResource, create LoginResource, register LoginResource
        RequestValidator loginRequestValidator = new LoginRequestValidator();
        final LoginResource loginResource = new LoginResource(loginRequestValidator, userDAO);
        env.jersey().register(loginResource);

        // create LoginResource, register LoginResource
        final LoginLogsResource loginLogsResource = new LoginLogsResource();
        env.jersey().register(loginLogsResource);

        // TODO: 12/11/16 given more time would add healh checks
        // register a health check
        // final TemplateHealthCheck healthCheck = new TemplateHealthCheck(config.getTemplate());
        // env.healthChecks().register("template", healthCheck);

        // wrap BasicAuthenticator in a CachingAuthenticator
        CachingAuthenticator<BasicCredentials, User> cachingAuthenticator =
                new CachingAuthenticator<>(env.metrics(), new BasicAuthenticator(userDAO),
                        config.getAuthenticationCachePolicy());

        // register basic authentication
        // TODO: 12/11/16 more time set up {@link ChainedFactory} for using OAuth2
        env.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(cachingAuthenticator)
                        .setAuthorizer(new BasicAuthorizer())
                        .setRealm("Basic Auth Realm")
                        .buildAuthFilter()
        ));

        // allow the use of roles
        env.jersey().register(RolesAllowedDynamicFeature.class);
    }

}
