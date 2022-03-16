package com.alanjgg.wwc.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Alan
 * @Description
 * @date 2022/3/15
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 向缓存中设置字符串内容
     *
     * @param key     key
     * @param value   value
     * @param seconds seconds
     * @return
     * @throws Exception
     */
    public boolean set(String key, String value, Integer seconds) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            if (seconds != null) {
                jedis.expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            return set(key, (String) value, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    /**
     * 删除缓存中得对象，根据key
     *
     * @param key
     * @return
     */
    public boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据key 获取内容
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }


    /**
     * 根据key 获取对象
     *
     * @param key
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = JSONObject.toJSONString(jedis.get(key));
            return JSONObject.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

}
