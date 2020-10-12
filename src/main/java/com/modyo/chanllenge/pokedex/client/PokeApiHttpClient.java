package com.modyo.chanllenge.pokedex.client;

import com.modyo.chanllenge.pokedex.model.pokeapi.MultiPokeApiResponseDTO;
import com.modyo.chanllenge.pokedex.model.pokeapi.Pokemon;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Component
public class PokeApiHttpClient {

    private static final String POKEMON_ENDPOINT = "/pokemon";

    private static final String OFFSET_QUERY_PARAM = "offset";

    private static final String LIMIT_QUERY_PARAM = "limit";

    private static final int ITEMS_PER_PAGE = 20;

    @Value("${api.pokeapi.host}")
    private String pokeApiHost;

    @Cacheable("")
    public Mono<Pokemon> getPokemon(String name) {
        return buildPokeApiWebClient().get()
                .uri(POKEMON_ENDPOINT + "/{name}", name)
                .retrieve()
                .bodyToMono(Pokemon.class);
    }

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
        } catch (SSLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
