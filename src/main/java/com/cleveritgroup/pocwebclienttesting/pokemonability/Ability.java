package com.cleveritgroup.pocwebclienttesting.pokemonability;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Ability(String name, Integer id, @JsonProperty("is_main_series") Boolean mainSeries) {
}
