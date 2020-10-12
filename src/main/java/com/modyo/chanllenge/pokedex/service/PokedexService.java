package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import reactor.core.publisher.Mono;

public interface PokedexService {

    Mono<CommonResponseDTO<String>> listPokemons(int page);

}
