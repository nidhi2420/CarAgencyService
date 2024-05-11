package com.carserviceagency.configuration;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class CacheConfig {
		@Bean
	    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
	        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofMinutes(10)); // Cache entries expire after 10 minutes

	        return RedisCacheManager.builder(connectionFactory)
	                .cacheDefaults(cacheConfiguration)
	                .build();
	    }
}
