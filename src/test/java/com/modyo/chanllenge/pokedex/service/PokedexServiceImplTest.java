package com.modyo.chanllenge.pokedex.service;

import com.modyo.chanllenge.pokedex.client.PokeApiHttpClient;
import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.api.PokemonResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.MultiPokeApiResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.Pokemon;
import com.modyo.chanllenge.pokedex.model.pokeapi.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@SpringBootTest
public class PokedexServiceImplTest {

    private static final int TOTAL_ITEMS = 100;

    @Mock
    private PokeApiHttpClient client;

    @InjectMocks
    private PokedexServiceImpl pokedexService;

    @Test
    public void listPokemons_shouldTransformListOfPokemonsIntoACommonResponseDTO() {

        var currentPage = 1;
        var multiPokeApiResponseDTO = buildMultiPokeApiResponseDTO();

        when(client.getPokemons(currentPage)).thenReturn(buildMultiPokeApiResponseDTO());
        when(client.getPokemon(anyString())).thenReturn(Mono.just(new Pokemon()));

        CommonResponseDTO<PokemonResponseDTO> commonResponseDTO = pokedexService.listPokemons(currentPage);

        Assertions.assertThat(commonResponseDTO).isNotNull();
        Assertions.assertThat(commonResponseDTO.getCurrentPage()).isEqualTo(currentPage);
        Assertions.assertThat(commonResponseDTO.getTotalItems()).isEqualTo(TOTAL_ITEMS);
        Assertions.assertThat(commonResponseDTO.getTotalPages()).isEqualTo(TOTAL_ITEMS / PokeApiHttpClient.ITEMS_PER_PAGE);
        Assertions.assertThat(commonResponseDTO.getItems()).isNotNull().isNotEmpty().hasSize(multiPokeApiResponseDTO.getResults().size());


    }

    @Test
    public void listPokemons_shouldReturnNullWhenGetPokemonsIsEmpty() {
        when(client.getPokemons(anyInt())).thenReturn(null);
        var response = pokedexService.listPokemons(0);
        Assertions.assertThat(response).isNull();
    }

    @Test
    public void listPokemons_shouldReturnNullWhenGetPokemonIsEmpty() {
        when(client.getPokemons(anyInt())).thenReturn(buildMultiPokeApiResponseDTO());
        when(client.getPokemon(anyString())).thenReturn(Mono.empty());
        var response = pokedexService.listPokemons(0);
        Assertions.assertThat(response).isNull();
    }

    private MultiPokeApiResponseDTO buildMultiPokeApiResponseDTO() {
        var multiPokeApiResponseDTO = new MultiPokeApiResponseDTO();
        multiPokeApiResponseDTO.setResults(Arrays.asList(
                new Result("pokemon1"),
                new Result("pokemon2"),
                new Result("pokemon3")
        ));
        multiPokeApiResponseDTO.setCount(TOTAL_ITEMS);
        return multiPokeApiResponseDTO;
    }

}
