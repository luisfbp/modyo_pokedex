package com.modyo.chanllenge.pokedex.model.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sprites {

    private Other other;

    @Data
    public static class Other {

        @JsonProperty("official-artwork")
        private OfficialArtwork officialArtwork;

    }

    @Data
    public static class OfficialArtwork {

        @JsonProperty("front_default")
        private String frontDefault;

    }

}
