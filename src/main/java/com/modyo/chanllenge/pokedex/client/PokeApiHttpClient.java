package com.modyo.chanllenge.pokedex.client;

import com.modyo.chanllenge.pokedex.exception.CustomApiThrowable;
import com.modyo.chanllenge.pokedex.model.pokeapi.MultiPokeApiResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.Pokemon;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * HTTP Client to consume Poke API endpoints.
 */
@Component
public class PokeApiHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokeApiHttpClient.class);

    private static final String POKEMON_ENDPOINT = "/pokemon";

    private static final String OFFSET_QUERY_PARAM = "offset";

    private static final String LIMIT_QUERY_PARAM = "limit";

    public static final int ITEMS_PER_PAGE = 20;

    @Value("${api.pokeapi.host}")
    private String pokeApiHost;

    /**
     * Gets a single pokemon by name.
     * Makes use of a reactive library for making nonblocking calls, allowing to make all calls at the same time.
     * @param name Pokemon Name
     * @return {@link Pokemon}
     */
    public Mono<Pokemon> getPokemon(String name) {
        return buildPokeApiWebClient().get()
                .uri(POKEMON_ENDPOINT + "/{name}", name)
                .retrieve()
                .bodyToMono(Pokemon.class);
    }

    /**
     * Gets the list of Pokemons to be displayed per page.
     * @param page Number of the page.
     * @return DTO representing all pokemons for a single page.
     */
    public MultiPokeApiResponseDTO getPokemons(int page) {

        int offset = page * ITEMS_PER_PAGE;

        return buildPokeApiWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder.path(POKEMON_ENDPOINT)
                        .queryParam(OFFSET_QUERY_PARAM, offset)
                        .queryParam(LIMIT_QUERY_PARAM, ITEMS_PER_PAGE)
                        .build())
                .retrieve()
                .bodyToMono(MultiPokeApiResponseDTO.class).block();

    }

    /**
     * Builds the {@link WebClient} with some default configs.
     * @return {@link WebClient} properly configured.
     * @Error Throw a {@link RuntimeException} if something goes wrong creating the client.
     */
    private WebClient buildPokeApiWebClient() {

        try {
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();

            HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

            return WebClient
                    .builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .baseUrl(pokeApiHost)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .exchangeStrategies(ExchangeStrategies.builder()
                            .codecs(configurer -> configurer
                                    .defaultCodecs()
                                    .maxInMemorySize(16 * 1024 * 1024))
                            .build())
                    .build();
        } catch (Exception e) {
            var message =  "Error trying to create WebClient object";
            LOGGER.error(message);
            throw new CustomApiThrowable(message, e);
        }
    }
}
