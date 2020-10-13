package com.modyo.chanllenge.pokedex.model.pokeapi;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Result {

    @NonNull
    private String name;
    private String url;

}