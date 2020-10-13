package com.modyo.chanllenge.pokedex.model.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PokemonResponseDTO {

    private String name;
    private int weight;
    private String imgUrl;
    private List<String> types;
    private List<String> abilities;

}
