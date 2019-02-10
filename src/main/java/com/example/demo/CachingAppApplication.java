package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class CachingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingAppApplication.class, args);
    }

}

@Configuration
@EnableCaching
@Profile("caching")
class CachingConfiguration {
}

@Service
@Slf4j
class CacheableService {

    @Cacheable(value = "cacheable", unless = "#result == null")
    public String get(String id) {
        if (id == "unknown") {
            return null;
        }
        return UUID.randomUUID().toString();
    }
}