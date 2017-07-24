package com.party.web.web.security.cache;

import com.party.common.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Set;

/**
 * redis缓存
 * Created by wei.li
 *
 * @date 2017/6/26 0026
 * @time 19:18
 */
public class RedisCache<K, V> implements Cache<K, V> {


    private RedisManager<K, V> redisManager;

    protected static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private String KEY_PREFIX = "shiro_redis_session:";

    public RedisCache(RedisManager<K, V> redisManager) {
        this.redisManager = redisManager;
    }

    /**
     * 回去缓存
     * @param k 缓存key
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K k) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [" + k + "]");
        if (null == k){
            return null;
        }
        V value = redisManager.getValue(KEY_PREFIX, k);
        return value;
    }

    /**
     * 设置缓存
     * @param k 缓存key
     * @param v 缓存value
     * @return
     * @throws CacheException
     */
    @Override
    public V put(K k, V v) throws CacheException {
        logger.debug("根据key从存储 key [" + k + "]");
        redisManager.setValue(KEY_PREFIX, k, v);
        return v;
    }

    /**
     * 移除缓存
     * @param k 缓存key
     * @return
     * @throws CacheException
     */
    @Override
    public V remove(K k) throws CacheException {
        logger.debug("从redis中删除 key [" + k + "]");
        V value = get(k);
        redisManager.remove(KEY_PREFIX, k);
        return value;
    }

    /**
     * 清除缓存
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        redisManager.flushDB(KEY_PREFIX);
    }

    /**
     * 缓存长度
     * @return
     */
    @Override
    public int size() {
        return redisManager.size(KEY_PREFIX).intValue();
    }

    /**
     * 缓存key 集合
     * @return
     */
    @Override
    public Set<K> keys() {
        return redisManager.keys(KEY_PREFIX);
    }

    /**
     * 缓存value集合
     * @return
     */
    @Override
    public Collection<V> values() {
        return redisManager.valus(KEY_PREFIX);
    }
}
