package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public Optional<String> get(String id) {
        if (id == "unknown") {
            return Optional.empty();
        }
        return Optional.of(UUID.randomUUID().toString());
    }
}