package com.jcms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtils {
    /**
     * RedisTemplate是一个简化Redis数据访问的一个帮助类，
     * 此类对Redis命令进行高级封装，通过此类可以调用ValueOperations和ListOperations等等方法。
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 缓存是否存在
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     *
     * @Author Ron
     * @param key
     * @param hashKey
     * @return
     */
    public Object get(final String key, final String hashKey){
        Object result = null;
        HashOperations<String,Object,Object> operations = redisTemplate.opsForHash();
        result = operations.get(key, hashKey);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @Author Ron
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public boolean set(final String key, final String hashKey, Object value) {
        boolean result = false;
        try {
            HashOperations<String,Object,Object> operations = redisTemplate.opsForHash();
            operations.put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
