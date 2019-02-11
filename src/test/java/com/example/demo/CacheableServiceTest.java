package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("caching")
public class CacheableServiceTest {

    @Autowired
    private CacheableService cacheableService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Before
    public void setUp() {
        getCachedKeys()
                .stream()
                .forEach(key -> redisTemplate.delete(key));
    }

    @Test
    public void shouldCacheKey() {
        String key = UUID.randomUUID().toString();

        Optional<String> value = cacheableService.get(key);

        assertThat(value).isPresent();
        assertThat(getCachedKeys()).containsExactly("cacheable::" + key);
    }

    @Test
    public void shouldNotCacheUnknown() {
        Optional<String> value = cacheableService.get("unknown");

        assertThat(value).isNotPresent();
        assertThat(getCachedKeys()).hasSize(0);
    }

    private Set<String> getCachedKeys() {
        return redisTemplate.keys("cacheable*");
    }
}