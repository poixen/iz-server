package com.poixen.java.api.izserver.resource;

import com.poixen.java.api.izserver.authentication.BasicAuthenticator;
import com.poixen.java.api.izserver.authentication.BasicAuthorizer;
import com.poixen.java.api.izserver.model.User;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import com.poixen.java.api.izserver.model.request.validators.LoginRequestValidator;
import com.poixen.java.api.izserver.model.request.validators.RequestValidator;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Custom unit test for {@link }
 */
public class ServerResourceTest {

    private UserDAO userDAO;
    private RequestValidator validator;

    @Rule
    public ResourceTestRule rule = ResourceTestRule
            .builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(new BasicAuthenticator(userDAO))
                    .setAuthorizer(new BasicAuthorizer())
                    .setRealm("Basic Auth Realm")
                    .setPrefix("Basic")
                    .buildAuthFilter()))
            .addProvider(RolesAllowedDynamicFeature.class)
            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
            .addResource(new RegisterResource(validator, userDAO))
            .build();

    @Before
    public void before() {
        userDAO = mock(UserDAO.class);
    }


    @Ignore
    @Test
    public void testProtected() throws Exception {

        // given
        User user = mock(User.class);
        validator = new LoginRequestValidator();
        when(userDAO.getUser("poixen")).thenReturn(user);
        // TODO: 14/11/16 finish test

        // when
        final Response response = rule.getJerseyTest().target("/register")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Basic cG9peGVuOkV3cWRzYTMyMQ==")
                .get();

        // then
        assertEquals("Incorrect Response Status!", 200, response.getStatus());
    }

}