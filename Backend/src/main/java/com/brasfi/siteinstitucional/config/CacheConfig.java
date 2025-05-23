package com.brasfi.siteinstitucional.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(
                "informacoes-institucionais",
                "informacoes-institucionais-all",
                "informacoes-institucionais-ativas",
                "informacoes-institucionais-tipo",
                "informacoes-institucionais-ativas-tipo",
                "informacoes-institucionais-map",
                "informacoes-institucionais-map-tipo"
        );
        return cacheManager;
    }
}