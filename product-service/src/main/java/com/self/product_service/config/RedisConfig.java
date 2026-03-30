package com.self.product_service.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean("customKeyGenerator")
    public KeyGenerator customKeyGenerator() {

        return (Object target, Method method, Object... params) -> {

            StringBuilder key = new StringBuilder();

            key.append(target.getClass().getSimpleName());
            key.append("_");
            key.append(method.getName());

            for (Object param : params) {
                key.append("_").append(param);
            }

            return key.toString();
        };
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)); // cache expires after 10 minutes
    }
}
