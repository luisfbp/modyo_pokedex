package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.Data;

import java.util.Map;

@Data
public class Sprites {

    private Map<String, Map<String, String>> other;

}
