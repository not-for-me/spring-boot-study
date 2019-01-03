package kr.notforme.boot.cache.config;

import java.io.IOException;

import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonRedisSerializer<T> implements RedisSerializer<T> {
    private ObjectMapper om;
    private Class<T> klass;

    /**
     * @param t
     * @return
     */
    @Override
    public byte[] serialize(T t) {
        try {
            return om.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            // noop
            return null;
        }
    }

    /**
     * @param bytes
     * @return
     */
    @Override
    public T deserialize(byte[] bytes) {
        try {
            return om.readValue(bytes, klass);
        } catch (IOException e) {
            // noop
            return null;
        }
    }
}
