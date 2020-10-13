package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.Data;

@Data
public class Ability {

    private SubAbility ability;

    @Data
    public class SubAbility {

        private String name;

    }

}
