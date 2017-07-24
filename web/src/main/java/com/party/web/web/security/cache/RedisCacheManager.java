package com.party.web.web.security.cache;
import com.party.common.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * redis 缓存管理器
 * Created by wei.li
 *
 * @date 2017/6/26 0026
 * @time 19:03
 */
@Component(value = "redisCacheManager")
public class RedisCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Autowired
    private RedisManager redisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Cache c = caches.get(s);
        if (c == null) {
            c = new RedisCache<K, V>(redisManager);
            caches.put(s, c);
        }
        return c;
    }


}
