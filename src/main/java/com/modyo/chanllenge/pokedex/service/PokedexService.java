package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.model.pokeapi.Pokemon;

import java.util.List;

public interface PokedexService {

    List<Pokemon> listPokemons(int page);

}
