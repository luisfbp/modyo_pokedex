package com.modyo.chanllenge.pokedex.model.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommonResponseDTO<T> {

    private int currentPage;
    private int totalItems;
    private int totalPages;
    private List<T> items;

}
