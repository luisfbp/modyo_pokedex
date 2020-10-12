package com.modyo.chanllenge.pokedex.model.api;

import lombok.Data;

import java.util.List;

@Data
public class CommonResponseDTO<T> {

    private int currentPage;
    private int totalItems;
    private int totalPages;
    private List<T> items;

}
