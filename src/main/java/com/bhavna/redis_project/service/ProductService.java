package com.bhavna.redis_project.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final StringRedisTemplate redisTemplate;

    private static final String PRODUCT_CACHE_KEY = "products:all";

    public Map<String, String> getProducts() {

        // 1. Try to fetch from Redis cache
        String cached = redisTemplate.opsForValue().get(PRODUCT_CACHE_KEY);
        if (cached != null) {
            return Map.of(
                    "message", "Here are your products (from cache)",
                    "data", cached
            );
        }

        // 2. Simulate DB/product fetch
        String products = "Laptop, Mouse, Keyboard";

        // 3. Store in Redis cache with TTL of 60 seconds
        redisTemplate.opsForValue().set(PRODUCT_CACHE_KEY, products, 60, TimeUnit.SECONDS);

        // 4. Return result (from database)
        return Map.of(
                "message", "Here are your products (fresh)",
                "data", products
        );
    }
}

