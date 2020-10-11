package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.client.PokeApiHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PokedexServiceImpl implements PokedexService{

    @Autowired
    private PokeApiHttpClient client;

    public Mono<String> listPokemons(int page){
        return client.getPokemon();
    }

}
