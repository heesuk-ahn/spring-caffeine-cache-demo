package com.heesuk.projects.cache.demo.controller;

import java.util.concurrent.TimeUnit;

import javax.websocket.server.PathParam;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @GetMapping("/v1/cache-test")
    @Cacheable(cacheNames = "cache-test")
    public String getCacheRoute(@PathParam("value") String value) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return value;
    }

}
