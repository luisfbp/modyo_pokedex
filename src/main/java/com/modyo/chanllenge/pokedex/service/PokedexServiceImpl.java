package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.client.PokeApiHttpClient;
import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.api.PokemonResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles business logic to gather, convert and deliver pokemon data.
 */
@Service
public class PokedexServiceImpl implements PokedexService {

    @Autowired
    private PokeApiHttpClient client;

    /**
     * Gets a list of pokemons from Poke API and transform them in {@link CommonResponseDTO}
     * so it can be serialized in the API response.
     * @param page page to be serialized.
     * @return {@link CommonResponseDTO} with pokemon data.
     */
    @Cacheable("pokemonCache")
    public CommonResponseDTO<PokemonResponseDTO> listPokemons(int page) {

        MultiPokeApiResponseDTO allPokemonResults = client.getPokemons(page);

        if (allPokemonResults == null || CollectionUtils.isEmpty(allPokemonResults.getResults())) return null;

        List<Pokemon> pokemons = Flux.fromIterable(allPokemonResults.getResults())
                .map(Result::getName)
                .flatMap(client::getPokemon)
                .collectList().block();

        if (CollectionUtils.isEmpty(pokemons)) return null;

        return buildResponse(pokemons, page, allPokemonResults.getCount());
    }

    /**
     * Build a {@link CommonResponseDTO} for a given list of pokemons.
     * @param pokemons pokemons to be serialized.
     * @param page page to display.
     * @param totalItems total of pokemons.
     * @return {@link CommonResponseDTO} with pokemon list.
     */
    private CommonResponseDTO<PokemonResponseDTO> buildResponse(List<Pokemon> pokemons, int page, int totalItems) {

        List<PokemonResponseDTO> pokemonResponses = pokemons.stream().map(this::mapToPokemonResponse).collect(Collectors.toList());

        return CommonResponseDTO.<PokemonResponseDTO>builder()
                    .totalPages((int) Math.floor(totalItems / PokeApiHttpClient.ITEMS_PER_PAGE))
                    .totalItems(totalItems)
                    .currentPage(page)
                    .items(pokemonResponses)
                .build();
    }

    /**
     * Maps every {@link Pokemon} into a {@link PokemonResponseDTO}.
     * @param pokemon pokemon to map.
     * @return {@link PokemonResponseDTO} object ready for rendering.
     */
    private PokemonResponseDTO mapToPokemonResponse (Pokemon pokemon) {

        Optional<Pokemon> opPokemon = Optional.of(pokemon);

        String imgUrl = opPokemon.map(Pokemon::getSprites)
                .map(Sprites::getOther)
                .map(Sprites.Other::getOfficialArtwork)
                .map(Sprites.OfficialArtwork::getFrontDefault)
                .orElse(null);

        List<String> types = opPokemon.map(Pokemon::getTypes).orElse(Collections.emptyList())
                .stream()
                .map(Type::getType)
                .map(Type.SubType::getName)
                .collect(Collectors.toList());

        List<String> abilities = opPokemon.map(Pokemon::getAbilities).orElse(Collections.emptyList())
                .stream()
                .map(Ability::getAbility)
                .map(Ability.SubAbility::getName)
                .collect(Collectors.toList());

        return PokemonResponseDTO
                .builder()
                    .name(pokemon.getName())
                    .weight(pokemon.getWeight())
                    .imgUrl(imgUrl)
                    .types(types)
                    .abilities(abilities)
                .build();

    }

}
