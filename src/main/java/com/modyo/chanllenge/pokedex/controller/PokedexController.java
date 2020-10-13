package com.modyo.chanllenge.pokedex.controller;

import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.api.PokemonResponseDTO;
import com.modyo.chanllenge.pokedex.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller to handles incoming request for pokedex calls.
 */
@RestController
@RequestMapping("/v1/pokedex")
public class PokedexController {

    @Autowired
    private PokedexService pokedexService;

    /**
     * List paginated pokemons with their basic information.
     * @param page page to be display.
     * @return {@link CommonResponseDTO} with a list of pokemons.
     */
    @GetMapping("/pokemon")
    public ResponseEntity<CommonResponseDTO<PokemonResponseDTO>> listPokemons(@RequestParam Optional<Integer> page) {

        CommonResponseDTO<PokemonResponseDTO> response = pokedexService.listPokemons(page.orElse(0));

        if (response == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(pokedexService.listPokemons(page.orElse(0)));
    }

}
