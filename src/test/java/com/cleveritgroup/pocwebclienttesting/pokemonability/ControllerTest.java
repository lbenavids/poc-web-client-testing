package com.cleveritgroup.pocwebclienttesting.pokemonability;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

/**
 * This class represents the unit test for AbilityService controller.
 * <p>
 * It uses WebFluxTest for narrowing the tests to only web flux components (controller in this case).
 * </p>
 */
@WebFluxTest
public class ControllerTest {

    @Autowired // It denotes that the annotated field should be populated with a value automatically by Spring Framework.
    private WebTestClient webTestClient; // This is used to test the web endpoints.

    @MockBean // It replaces the existing bean with a mock in the application context, used here to mock the AbilityService.
    private AbilityService abilityService;

    /**
     * This test verifies that the correct Ability object is returned given a valid ability number.
     * The mock AbilityService is made to return a particular Ability object when 'getAbility()' is called with a particular ability number.
     * Then a GET request is made using 'webTestClient' to the '/ability/{abilityNumber}' endpoint with the same ability number.
     * The returned object is then validated to match the mock Ability object.
     * <p>
     * Number 1 is used as a stand-in for all ability numbers.
     * </p>
     */
    @Test
    public void getPokemon_AbilityExisting_ReturnsAbility() {

        Integer abilityNumber = 1; // The mock ability number.

        Ability ability = new Ability("example", abilityNumber, true); // The mock Ability object which is returned by the mock AbilityService.

        // Mock the getAbility function of the AbilityService to return the above Ability object when called with the above ability number.
        when(abilityService.getAbility(abilityNumber)).thenReturn(Mono.just(ability));

        // Make a GET request to the '/ability/{abilityNumber}' endpoint with the above ability number.
        // Then validate the returned Ability object to match the above Ability object.
        webTestClient.get().uri("/ability/{abilityNumber}", abilityNumber)
                .exchange() // Exchanges the request/response.
                .expectStatus().isOk() // Expects the response status to be 200 OK.
                .expectBody() // Allows assertion of the response body's content.
                .jsonPath("$.is_main_series").isEqualTo(ability.mainSeries()) // Verification of the 'mainSeries' property.
                .jsonPath("$.name").isEqualTo(ability.name()) // Verification of the 'name' property.
                .jsonPath("$.id").isEqualTo(ability.id()); // Verification of the 'id' property.
    }
}