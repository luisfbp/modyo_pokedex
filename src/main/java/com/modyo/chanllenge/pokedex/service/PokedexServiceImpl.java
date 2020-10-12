package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.client.PokeApiHttpClient;
import com.modyo.chanllenge.pokedex.model.pokeapi.Pokemon;
import com.modyo.chanllenge.pokedex.model.pokeapi.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

/**
 * Handles business logic to gather pokemon data
 */
@Service
public class PokedexServiceImpl implements PokedexService {

    @Autowired
    private PokeApiHttpClient client;

    public List<Pokemon> listPokemons(int page) {

        List<Result> allPokemonResults = client.getPokemons(page).getResults();

        if (CollectionUtils.isEmpty(allPokemonResults)) return Collections.emptyList();

        return Flux.fromIterable(allPokemonResults)
                .map(Result::getName)
                .flatMap(client::getPokemon)
                .collectList().block();
    }

}
