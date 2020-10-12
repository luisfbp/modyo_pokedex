package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.Data;

import java.util.List;

@Data
public class MultiPokeApiResponseDTO {

    private int count;
    private List<Result> results;

}
