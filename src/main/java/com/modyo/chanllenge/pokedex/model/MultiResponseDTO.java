package com.modyo.chanllenge.pokedex.model;

import lombok.Data;

import java.util.List;

@Data
public class MultiResponseDTO {

    private int count;
    private String next;
    private String previous;
    private List<Result> results;

    @Data
    static class Result {

        private String name;
        private String url;

    }
}
