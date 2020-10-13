package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.Data;

import java.util.List;

@Data
public class Pokemon {

    private String name;
    private int weight;
    private List<Type> types;
    private List<Ability> abilities;
    private Sprites sprites;

}
