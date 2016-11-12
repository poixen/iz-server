package com.poixen.java.api.izserver.resource;

/**
 * Custom unit test for {@link HelloWorldResource}
 */
public class HelloWorldResourceTest {

//    @Rule
//    public ResourceTestRule rule = ResourceTestRule
//            .builder()
//            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
//            .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
//                    .setAuthenticator(new BasicAuthenticator())
//                    .setAuthorizer(new BasicAuthorizer())
//                    .setRealm("Basic Auth Realm")
//                    .setPrefix("Basic")
//                    .buildAuthFilter()))
//            .addProvider(RolesAllowedDynamicFeature.class)
//            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
//            .addResource(new HelloWorldResource("Hello %s", "Stranger"))
//            .build();
//
//
//    @Test
//    public void testProtected() throws Exception {
//        final Response response = rule.getJerseyTest().target("/hello-world")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .header("Authorization", "Basic cG9peGVuOkV3cWRzYTMyMQ==")
//                .get();
//
//        assertEquals("Incorrect Response Status!", 200, response.getStatus());
//    }

}