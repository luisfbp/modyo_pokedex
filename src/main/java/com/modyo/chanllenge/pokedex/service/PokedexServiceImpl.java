package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.client.PokeApiHttpClient;
import com.modyo.chanllenge.pokedex.config.CacheConfig;
import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.MultiPokeApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokedexServiceImpl implements PokedexService {

    @Autowired
    private PokeApiHttpClient client;


    @Cacheable("pokemonListCache")
    public Mono<CommonResponseDTO<String>> listPokemons(int page){
        return client.getPokemon(page).map(multiPokeApiResponseDTO -> mapToCommonResponseDTO(multiPokeApiResponseDTO, page));
    }

    private CommonResponseDTO<String> mapToCommonResponseDTO(MultiPokeApiResponseDTO multiPokeApiResponseDTO, int currentPage) {

        if (multiPokeApiResponseDTO == null) return null;

        CommonResponseDTO<String> commonResponseDTO = new CommonResponseDTO<>();

        commonResponseDTO.setCurrentPage(currentPage);
        commonResponseDTO.setTotalItems(multiPokeApiResponseDTO.getCount());
        commonResponseDTO.setTotalPages(Math.round(multiPokeApiResponseDTO.getCount() / 20));

        if (!CollectionUtils.isEmpty(multiPokeApiResponseDTO.getResults())) {
            List<String> pokemonList = multiPokeApiResponseDTO.getResults().stream()
                    .map(MultiPokeApiResponseDTO.Result::getName)
                    .collect(Collectors.toList());
            commonResponseDTO.setItems(pokemonList);
        }

        return commonResponseDTO;
    }

}
