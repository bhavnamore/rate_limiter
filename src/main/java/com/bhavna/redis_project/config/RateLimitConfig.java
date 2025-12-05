package com.bhavna.redis_project.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rate.limit")
public class RateLimitConfig
{
        private int maxRequests;
        private int windowSeconds;
}
