package com.modyo.chanllenge.pokedex.controller;

import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.api.PokemonResponseDTO;
import com.modyo.chanllenge.pokedex.service.PokedexService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
public class PokedexControllerTest {

    @Mock
    private PokedexService pokedexService;

    @InjectMocks
    private PokedexController pokedexController;

    @Test
    public void listPokemons_shouldReturnEmptyResponse() {
        when(pokedexService.listPokemons(anyInt())).thenReturn(null);
        ResponseEntity<CommonResponseDTO<PokemonResponseDTO>> response = pokedexController.listPokemons(Optional.of(0));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNull();
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void listPokemons_shouldReturnOkResponse() {
        when(pokedexService.listPokemons(anyInt())).thenReturn(CommonResponseDTO.<PokemonResponseDTO>builder().build());
        ResponseEntity<CommonResponseDTO<PokemonResponseDTO>> response = pokedexController.listPokemons(Optional.of(0));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

}
