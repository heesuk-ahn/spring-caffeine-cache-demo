package com.heesuk.projects.cache.demo.config;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import lombok.extern.slf4j.Slf4j;

import static javax.management.timer.Timer.ONE_DAY;

@Configuration
@Slf4j
public class CaffeineCacheConfig {

    private static final String cacheTest = "cache-test";

    @Scheduled(fixedRate = ONE_DAY)
    @CacheEvict(cacheNames = { cacheTest }, allEntries = true)
    public void clearCache() {}

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(
            Arrays.asList(new CaffeineCache(cacheTest, Caffeine.newBuilder()
                                                               .removalListener(new CustomRemovalListener())
                                                               .build()))
        );

        return cacheManager;
    }

    class CustomRemovalListener implements RemovalListener<Object, Object> {
        @Override
        public void onRemoval(Object key, Object value, RemovalCause cause) {
            log.info("removal called with key {}, cause {}, evicted {} \n", key, cause.toString(), cause.wasEvicted());
        }
    }

}
