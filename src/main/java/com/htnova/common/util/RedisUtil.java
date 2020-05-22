package com.htnova.common.util;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Resource private RedisTemplate<String, Object> redisTemplate;

    @Resource private ValueOperations<String, String> valueOperations;

    @Resource private HashOperations<String, String, Object> hashOperations;

    @Resource private ListOperations<String, Object> listOperations;

    @Resource private SetOperations<String, Object> setOperations;

    @Resource private ZSetOperations<String, Object> zSetOperations;

    /** 默认过期时长，单位：秒 */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;
    /** 不设置过期时长 */
    public static final long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value), expire, TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        set(key, value);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /** Object转成JSON数据 */
    private String toJson(Object object) {
        if (object instanceof Integer
                || object instanceof Long
                || object instanceof Float
                || object instanceof Double
                || object instanceof Boolean
                || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /** JSON数据，转成Object */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
