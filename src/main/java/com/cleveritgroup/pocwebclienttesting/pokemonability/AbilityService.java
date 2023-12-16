package com.cleveritgroup.pocwebclienttesting.pokemonability;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AbilityService {

    private final WebClient webClient;

    public AbilityService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Retrieves the Ability with the given ID from the poke api.
     * This is executed by making a GET request to the following URL: <a href="https://pokeapi.co/api/v2/ability/1">poke api ability 1</a>
     *
     * @param abilityId the ID of the Ability to retrieve
     * @return a Mono that emits the retrieved Ability
     */
    public Mono<Ability> getAbility(Integer abilityId) {
        return webClient.get().uri("ability/{name}", abilityId).retrieve().bodyToMono(Ability.class);
    }

}