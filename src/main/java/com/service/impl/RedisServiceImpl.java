package com.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisServiceImpl {

    private RedisTemplate<String, Object> redisTemplate;

    public void deleteByKey(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
