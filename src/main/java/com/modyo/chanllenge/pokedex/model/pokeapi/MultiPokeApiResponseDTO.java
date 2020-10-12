package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.Data;

import java.util.List;

@Data
public class MultiPokeApiResponseDTO {

    private int count;
    private String next;
    private String previous;
    private List<Result> results;

    @Data
    public static class Result {

        private String name;
        private String url;

    }
}
