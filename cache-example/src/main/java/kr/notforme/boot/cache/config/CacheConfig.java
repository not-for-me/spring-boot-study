package kr.notforme.boot.cache.config;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheConfiguration useCacheConfiguration() {
        return defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .prefixKeysWith("sbs")
                .disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                          RedisCacheConfiguration useCacheConfiguration
    ) {
        final Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("userCache", useCacheConfiguration);

        return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultCacheConfig())
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .disableCreateOnMissingCache()
                                .transactionAware()
                                .build();
    }
}
