package com.baojie.jeesite.login.sessiondao;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ：冀保杰
 * @date：2018-08-24
 * @desc：
 */
//@Component
public class RedisManager2 {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    /**
     * 过期时间
     */
//  private Long expire;

    /**
     * 添加缓存数据（给定key已存在，进行覆盖）
     * @param key
     * @param obj
     * @throws DataAccessException
     */
    public <T> void set(String key, T obj) throws DataAccessException{
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serialize(obj);
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(bkey, bvalue);
                return null;
            }
        });
    }

    /**
     * 添加缓存数据（给定key已存在，不进行覆盖，直接返回false）
     * @param key
     * @param obj
     * @return 操作成功返回true，否则返回false
     * @throws DataAccessException
     */
    public <T> boolean setNX(String key, T obj) throws DataAccessException{
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serialize(obj);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(bkey, bvalue);
            }
        });

        return result;
    }

    /**
     * 添加缓存数据，设定缓存失效时间
     * @param key
     * @param obj
     * @param expireSeconds 过期时间，单位 秒
     * @throws DataAccessException
     */
    public <T> void setEx(String key, T obj, final long expireSeconds) throws DataAccessException{
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serialize(obj);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireSeconds, bvalue);
                return true;
            }
        });
    }

    /**
     * 获取key对应value
     * @param key
     * @return
     * @throws DataAccessException
     */
    public <T> T get(final String key) throws DataAccessException{
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return SerializerUtil.deserialize(result);
    }

    /**
     * 删除指定key数据
     * @param key
     * @return 返回操作影响记录数
     */
    public Long del(final String key){
        if (StringUtils.isEmpty(key)) {
            return 0l;
        }
        Long delNum = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keys = key.getBytes();
                return connection.del(keys);
            }
        });
        return delNum;
    }

    public Set<byte[]> keys(final String key){
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Set<byte[]> bytesSet = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keys = key.getBytes();
                return connection.keys(keys);
            }
        });

        return bytesSet;
    }

}
