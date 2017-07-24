package com.party.core.redisDao.menu;

import com.party.core.model.menu.Menu;
import com.party.common.redis.BaseRedisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单缓存
 * party
 * Created by wei.li
 * on 2016/9/10 0010.
 */
@Repository
public class MenuRedisDao extends BaseRedisDao<Menu> {


    /**
     * 缓存菜单列表元素
     * @param key 缓存key
     * @param menu 菜单元素
     * @return 返回状态码
     */
    @Override
    public long listPush(String key, Menu menu) {
        return super.listPush(key, menu);
    }

    /**
     * 缓存菜单列表
     * @param key 缓存key
     * @param t 缓存对象
     * @return 返回状态码
     */
    @Override
    public long listPushAll(String key, List<Menu> t) {
        return super.listPushAll(key, t);
    }

    /**
     * 移除缓存菜单元素
     * @param key 缓存key
     * @param count 数量
     * @param menu 菜单元素
     * @return 返回状态码
     */
    @Override
    public long listRemove(String key, long count, Menu menu) {
        return super.listRemove(key, count, menu);
    }

    /**
     * 根据偏移量查询缓存列表
     * @param key 缓存key
     * @param star 偏移量 star
     * @param stop 偏移量 stop
     * @return 缓存菜单列表
     */
    @Override
    public List<Menu> listRang(String key, long star, long stop) {
        return super.listRang(key, star, stop);
    }

    /**
     * 查询所有缓存列表
     * @param key 缓存key
     * @return 菜单列表
     */
    @Override
    public List<Menu> listAll(String key) {
        return super.listAll(key);
    }

    /**
     * 删除缓存
     * @param key 缓存key
     */
    @Override
    public void delete(String key) {
        super.delete(key);
    }
}
