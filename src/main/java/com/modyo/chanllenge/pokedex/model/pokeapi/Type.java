package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.Data;

@Data
public class Type {

    private SubType type;

    @Data
    public static class SubType {

        private String name;

    }

}
