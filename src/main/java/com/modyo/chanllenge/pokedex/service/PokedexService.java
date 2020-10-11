package com.modyo.chanllenge.pokedex.service;

import reactor.core.publisher.Mono;

public interface PokedexService {

    Mono<String> listPokemons(int page);

}
