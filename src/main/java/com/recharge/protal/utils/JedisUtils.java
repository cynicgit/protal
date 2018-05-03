package com.recharge.protal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//@Component
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;

    //设置Redis缓存
    public void setJedis(String key, String value, int time) {
        Jedis jedis = jedisPool.getResource();
        jedis.setex(key, time, value);
        jedis.close();
    }

    //设置Redis缓存
    public void setJedis(String key, String value) {
        setJedis(key, value, -1);
    }

    public void setToken(String key, String value) {
        setJedis(key, value, 30 * 60 * 1000);
    }




    //获取Redis缓存
    public String getJedis(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    //删除Redis缓存
    public void delJedis(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }
}
