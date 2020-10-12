package com.modyo.chanllenge.pokedex.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Memory cache configuration.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Custom configuration allowing multiple caches to be set up easily through {@link CacheSetup}.
     * @return {@link CacheManager}
     */
    @Bean
    public CacheManager cacheManager() {

        List<CaffeineCache> caches = Stream.of(CacheSetup.values())
                .map(this::buildCache)
                .collect(Collectors.toList());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);

        return cacheManager;

    }

    /**
     * Creates a {@link CaffeineCache} setting base on {@link CacheSetup} evictions.
     * @param cacheSetup {@link CacheSetup}
     * @return a {@link CaffeineCache} properly configured.
     */
    private CaffeineCache buildCache(CacheSetup cacheSetup) {
        return new CaffeineCache(cacheSetup.getCacheName(), Caffeine.newBuilder()
                .expireAfterWrite(cacheSetup.getExpirationTime(), TimeUnit.SECONDS)
                .maximumSize(cacheSetup.maximumSize)
                .build());

    }

    /**
     * Memory caches with its names and evictions.
     */
    public enum CacheSetup {

        CACHE_POKEMON_LIST("pokemonListCache", 3000, 100);

        private final String cacheName;
        private final int expirationTime;
        private final int maximumSize;

        CacheSetup(String cacheName, int expirationTime, int maximumSize) {
            this.cacheName = cacheName;
            this.expirationTime = expirationTime;
            this.maximumSize = maximumSize;
        }

        public String getCacheName() {
            return this.cacheName;
        }

        public int getExpirationTime() {
            return this.expirationTime;
        }

    }

}
