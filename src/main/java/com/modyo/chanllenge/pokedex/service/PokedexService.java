package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.api.PokemonResponseDTO;

import java.util.List;

public interface PokedexService {

    CommonResponseDTO<PokemonResponseDTO> listPokemons(int page);

}
