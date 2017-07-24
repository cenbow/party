package com.party.core.redisDao.user;


import com.party.core.model.user.User;
import com.party.common.redis.BaseRedisDao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户缓存
 * party
 * Created by wei.li
 * on 2016/9/8 0008.
 */
@Repository
public class UserRedisDao extends BaseRedisDao<User> {


    /**
     * 用户缓存
     * @param key 缓存 key
     * @param user 用户实体
     */
    @Override
    public void setValue(final String key, final User user) {
        super.setValue(key, user);
    }

    /**
     * 获取用户缓存
     * @param key 缓存key
     * @return 用户
     */
    @Override
    public User getValue(final String key) {
        return super.getValue(key);
    }

    /**
     * 删除缓存对象
     * @param key 缓存key
     */
    @Override
    public void delete(final String key) {
        super.delete(key);
    }

    /**
     * 缓存list单个对象
     * @param key 缓存key
     * @param user 用户实体
     * @return 返回参数码
     */
    @Override
    public long listPush(final String key, final User user) {
        return super.listPush(key, user);
    }

    /**
     * 缓存所有list
     * @param key 缓存key
     * @param t 缓存对象
     * @return 返回参数码
     */
    @Override
    public long listPushAll(final String key, List<User> t) {
        return super.listPushAll(key, t);
    }

    /**
     * 删除缓存
     * @param key 缓存key
     * @param count 数量
     * @param user 用户实体
     * @return 返回参数码
     */
    @Override
    public long listRemove(final String key, final long count, final User user) {
        return super.listRemove(key, count, user);
    }

    /**
     * 根据下标获取缓存实体
     * @param key 缓存key
     * @param index 缓存下标
     * @return 缓存实体
     */
    @Override
    public User listIndex(final String key, final long index) {
        return super.listIndex(key, index);
    }

    /**
     * 根据偏移量获取缓存列表
     * @param key 缓存key
     * @param star 偏移量 star
     * @param stop 偏移量 stop
     * @return 缓存列表
     */
    @Override
    public List<User> listRang(final String key, final long star, final long stop) {
        return super.listRang(key, star, stop);
    }

    /**
     * 获取所有缓存列表
     * @param key 缓存key
     * @return 缓存列表
     */
    @Override
    public List<User> listAll(final String key) {
        return super.listAll(key);
    }
}
