package com.baojie.jeesite.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public void removePattern(String pattern) {
        Set<Serializable> keys = this.redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }
    }

    public void remove(String key) {
        if (exists(key)) {
            this.redisTemplate.delete(key);
        }
    }

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    public <T> T get(String key) {
        T result = null;
        ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public <T> boolean set(String key, T value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> boolean set(String key, T value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void expire(String key, Long expireTime) {
        this.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 获取key的生存周期
     * @param key
     * @param expireTime
     * @return
     */
    public Long getExpire(String key, Long expireTime) {
        try{
            Long time =  this.redisTemplate.getExpire(key,TimeUnit.SECONDS);
            return time;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过正则表达式获取key
     *
     * @param pattern
     * @return
     */
    public Set<String>  setKeys(String pattern) {
        try{
            Set<String> keys =  this.redisTemplate.keys(pattern);
            return keys;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
