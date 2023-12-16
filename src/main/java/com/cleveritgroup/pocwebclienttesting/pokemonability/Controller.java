package com.cleveritgroup.pocwebclienttesting.pokemonability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    private final AbilityService abilityService;

    public Controller(AbilityService abilityService) {
        this.abilityService = abilityService;
    }


    @GetMapping("/ability/{abilityNumber}")
    public Mono<Ability> getPokemon(@PathVariable Integer abilityNumber) {
        return abilityService.getAbility(abilityNumber);
    }
}
