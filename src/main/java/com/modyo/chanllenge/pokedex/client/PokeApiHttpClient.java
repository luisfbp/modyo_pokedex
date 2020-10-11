package com.modyo.chanllenge.pokedex.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PokeApiHttpClient {

    private static final String POKEMON_ENDPOINT = "/pokemon";

    @Value("${api.pokeapi.host}")
    private String pokeApiHost;

    public Mono<String> getPokemon() {
        return buildPokeApiWebClient().get().uri(POKEMON_ENDPOINT).retrieve().bodyToMono(String.class);
    }

    private WebClient buildPokeApiWebClient() {
        return WebClient
                .builder()
                    .baseUrl(pokeApiHost)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
