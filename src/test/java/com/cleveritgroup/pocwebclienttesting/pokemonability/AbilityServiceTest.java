package com.cleveritgroup.pocwebclienttesting.pokemonability;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test class uses several JUnit 5 annotations:
 *
 * @BeforeEach: Defines methods that will run before each test.
 * @AfterEach: Defines methods that will run after each test.
 * @Test: Each method with this annotation represents a single test case.
 * @DisplayName: Defines a custom display name for the test method or test class.
 * @Nested: Enables the organization of tests into nested, hierarchical structures.
 */
@DisplayName("When testing the AbilityService  class")
public class AbilityServiceTest {
    private static final Integer ABILITY_ID = 1;  // Test data that represents the ability ID.
    private AbilityService abilityService;  // The service class that is under test.

    // MockWebServer is used here to create a test web server that can help with testing HTTP clients,
    // by providing canned answers to such clients.
    private MockWebServer mockWebServer;


    /**
     * Always executed before every test case. Here, it starts up the mock server and sets up the AbilityService.
     * It uses @BeforeEach annotation.
     */
    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();  // Initializes the mock server.
        mockWebServer.start();  // Starts the server.
        WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        abilityService = new AbilityService(webClient);  // Initializes the service using the mock server's URL.
    }

    /**
     * Always executed after every test case, it is used to stop the mock server.
     * It uses @AfterEach annotation.
     */
    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();  // Shutdown the mock server.
    }

    // The @Nested annotation allows to group several test cases that share the same context.
    // Here, tests that mock a successful server response are grouped.
    @Nested
    @DisplayName("Given a successful response")
    class SuccessfulResponse {
        private Ability ability;  // The Ability object that the service should return.

        // Here, we set up our successful response for each test in this context.
        @BeforeEach
        void setUp() {
            // Define the JSON response body
            String abilityBody = """
                {
                    "id": 1,
                    "name": "someAbility",
                    "is_main_series": true
                }
                """;

            // Add the response to the mock server's queue.
            mockWebServer.enqueue(new MockResponse().setBody(abilityBody).addHeader("Content-Type", "application/json"));

            // Call the service method and block until it returns.
            ability = abilityService.getAbility(ABILITY_ID).block();
        }

        // These are actual test cases.
        @Test
        @DisplayName("Then it should return the correct Id")
        void shouldReturnCorrectId() {
            assertEquals(ABILITY_ID, ability.id());  // Checks if the returned ability ID matches the expected one.
        }

        @Test
        @DisplayName("Then it should return the correct Name")
        void shouldReturnCorrectName() {
            assertEquals("someAbility", ability.name());  // Checks if the returned ability name matches the expected one.
        }

        @Test
        @DisplayName("Then it should return the correct IsMainSeries")
        void shouldReturnCorrectIsMainSeries() {
            assertTrue(ability.mainSeries());  // Checks if the returned ability's main series status is true.
        }

        @Test
        @DisplayName("Then it should call the correct Path")
        void shouldCallCorrectPath() throws InterruptedException {
            assertEquals("/ability/" + ABILITY_ID, mockWebServer.takeRequest().getPath());  // Checks if the server's path was constructed correctly.
        }
    }

    // Here, tests that mock a server error are grouped.
    @Nested
    @DisplayName("Given an error response")
    class ErrorResponse {
        @Test
        @DisplayName("Then it should return Error on 400")
        void shouldReturnErrorOn400() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(400));  // Add a 400 error to the mock server's queue.
            assertThrows(Exception.class, () -> abilityService.getAbility(ABILITY_ID).block());  // Checks if the service throws an exception.
        }

        @Test
        @DisplayName("Then it should return Error on 500")
        void shouldReturnErrorOn500() {
            mockWebServer.enqueue(new MockResponse().setResponseCode(500));  // Add a 500 error to the mock server's queue.
            assertThrows(Exception.class, () -> abilityService.getAbility(ABILITY_ID).block());  // Checks if the service throws an exception.
        }
    }
}