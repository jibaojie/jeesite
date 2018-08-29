package com.baojie.jeesite.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：redis操作
 */
@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

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

    /**
     * 以json字符串的形式保存数据
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        try {
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            T result = operations.get(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 以JdkSerializationRedisSerializer获取value
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValueJdkSerializer(String key) {
        logger.info("从redis中获取session，sessionId:{}", key);
        T result = null;
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 以json字符串的形式保存value
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
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

    /**
     * 以json字符串的形式保存value
     *
     * @param key
     * @param value
     * @param expireTime
     * @param <T>
     * @return
     */
    public <T> boolean set(String key, T value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * redis按照JdkSerializationRedisSerializer序列化方式保存value
     *
     * @param key
     * @param value
     * @param expireTime
     * @param <T>
     * @return
     */
    public <T> boolean setValueJdkSerializer(String key, T value, Long expireTime) {
        boolean result = false;
        try {
            logger.info("保存session到redis，sessionId:{}", key);
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
            ValueOperations<Serializable, T> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取key的生存周期
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        try {
            Long time = this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
            return time;
        } catch (Exception e) {
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
    public Set<String> setKeys(String pattern) {
        try {
            Set<String> keys = this.redisTemplate.keys(pattern);
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
