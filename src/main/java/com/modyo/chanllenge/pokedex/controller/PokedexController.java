package com.modyo.chanllenge.pokedex.controller;

import com.modyo.chanllenge.pokedex.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/v1/pokedex")
public class PokedexController {

    @Autowired
    private PokedexService pokedexService;

    @GetMapping("/pokemon")
    public ResponseEntity<Mono<String>> listPokemon(@RequestParam Optional<Integer> page) {
        return ResponseEntity.ok(pokedexService.listPokemons(page.orElse(0)));
    }

}
