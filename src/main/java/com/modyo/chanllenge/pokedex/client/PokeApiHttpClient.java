package com.modyo.chanllenge.pokedex.client;

import com.modyo.chanllenge.pokedex.model.pokeapi.MultiPokeApiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PokeApiHttpClient {

    private static final String POKEMON_ENDPOINT = "/pokemon";

    private static final String OFFSET_QUERY_PARAM = "offset";

    private static final String LIMIT_QUERY_PARAM = "limit";

    private static final int ITEMS_PER_PAGE = 20;

    @Value("${api.pokeapi.host}")
    private String pokeApiHost;

    public Mono<MultiPokeApiResponseDTO> getPokemon(int page) {

        int offset = page * ITEMS_PER_PAGE;

        return buildPokeApiWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder.path(POKEMON_ENDPOINT)
                        .queryParam(OFFSET_QUERY_PARAM, offset)
                        .queryParam(LIMIT_QUERY_PARAM, ITEMS_PER_PAGE)
                        .build())
                .retrieve()
                .bodyToMono(MultiPokeApiResponseDTO.class);
    }

    private WebClient buildPokeApiWebClient() {
        return WebClient
                .builder()
                    .baseUrl(pokeApiHost)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
