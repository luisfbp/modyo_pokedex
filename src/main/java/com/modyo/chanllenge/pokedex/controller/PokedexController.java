package com.modyo.chanllenge.pokedex.controller;

import com.modyo.chanllenge.pokedex.model.api.CommonResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.MultiPokeApiResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.Pokemon;
import com.modyo.chanllenge.pokedex.service.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/pokedex")
public class PokedexController {

    @Autowired
    private PokedexService pokedexService;

    @GetMapping("/pokemon")
    public List<Pokemon> listPokemons(@RequestParam Optional<Integer> page) {
        return pokedexService.listPokemons(page.orElse(0));
    }

}
