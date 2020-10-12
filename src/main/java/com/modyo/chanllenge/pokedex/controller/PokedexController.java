package com.modyo.chanllenge.pokedex.controller;

import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/v1/pokedex")
public class PokedexController {

    @Autowired
    private PokedexService pokedexService;

    @GetMapping("/pokemon")
    public Mono<CommonResponseDTO<String>> listPokemon(@RequestParam Optional<Integer> page) {
        return pokedexService.listPokemons(page.orElse(0));
    }

}
