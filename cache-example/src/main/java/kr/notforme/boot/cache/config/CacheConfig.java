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
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.notforme.boot.cache.api.User;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public ObjectMapper objectMapperForRedisSerialization() {
        // would be customized
        return new ObjectMapper();
    }

    @Bean
    public RedisCacheConfiguration useCacheConfiguration(ObjectMapper objectMapperForRedisSerialization) {
        return defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .prefixKeysWith("sbs")
                // JsonSerializer 클래스와 실제 직렬화 library도 인터페이싱할 수 있지만 일단 이렇게...
                .serializeValuesWith(SerializationPair.fromSerializer(
                        new JsonRedisSerializer<>(objectMapperForRedisSerialization, User.class)))
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
