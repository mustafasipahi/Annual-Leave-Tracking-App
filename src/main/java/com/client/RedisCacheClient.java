package com.client;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.SerializationException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Component
@AllArgsConstructor
public class RedisCacheClient {

    private final JedisPool jedisPool;

    public Object get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] value = jedis.get(key.getBytes());
            return toObject(value);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key.getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Object toObject(byte[] bytes) {
        if(bytes == null) {
            return null;
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInput input = new ObjectInputStream(inputStream)) {

            return input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException();
        }
    }
}
