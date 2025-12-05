package com.bhavna.redis_project.service;

import com.bhavna.redis_project.config.RateLimitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private final RateLimitConfig config;

    public boolean isAllowed(String clientId) {
        String key = "rate_limit:" + clientId;

        // 1. ATOMIC INCREMENT: Prevents Race Conditions
        // Returns the new value after incrementing
        Long count = redisTemplate.opsForValue().increment(key);

        // 2. Set Expiry on the very first request
        if (count != null && count == 1) {
            redisTemplate.expire(key, config.getWindowSeconds(), TimeUnit.SECONDS);
        }

        // 3. Check if count exceeds max
        return count != null && count <= config.getMaxRequests();
    }
}