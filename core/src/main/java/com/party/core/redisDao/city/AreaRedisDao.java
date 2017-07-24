package com.party.core.redisDao.city;

import com.alibaba.fastjson.JSONArray;
import com.party.common.redis.StringJedis;
import com.party.core.model.city.Area;
import com.party.common.redis.BaseRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地区缓存
 * party
 * Created by wei.li
 * on 2016/9/10 0010.
 */
@Repository
public class AreaRedisDao{

    @Autowired
    StringJedis stringJedis;



    /**
     * 缓存城市列表
     * @param key 缓存key
     * @param t 缓存对象
     * @return 返回状态码
     */
    public void listPushAll(final String key, final List<Area> t) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(t);
        stringJedis.setValue(key, jsonArray.toJSONString());
    }


    /**
     * 查询所有缓存对象
     * @param key 缓存key
     * @return 缓存列表
     */
    public List<Area> listAll(final String key) {
        String result = stringJedis.getValue(key);
        List<Area> areaList = JSONArray.parseArray(result, Area.class);
        return areaList;
    }


    /**
     * 删除缓存
     * @param key 缓存key
     */
    public void delete(final String key) {
        stringJedis.delete(key);
    }
}
