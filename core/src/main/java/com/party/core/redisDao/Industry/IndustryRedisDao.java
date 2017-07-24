package com.party.core.redisDao.Industry;

import com.alibaba.fastjson.JSONArray;
import com.party.common.redis.StringJedis;
import com.party.core.model.member.Industry;
import com.party.common.redis.BaseRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 行业缓存
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */
@Repository
public class IndustryRedisDao{


    @Autowired
    StringJedis stringJedis;

    /**
     * 缓存行业
     * @param key 缓存key
     * @param t 缓存对象
     * @return 状态代码
     */

    public void listPushAll(String key, List<Industry> t) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(t);
        stringJedis.setValue(key, jsonArray.toJSONString());
    }


    /**
     * 查询所有行业缓存
     * @param key 缓存key
     * @return 行业缓存列表
     */
    public List<Industry> listAll(String key) {
        String result = stringJedis.getValue(key);
        List<Industry> list = JSONArray.parseArray(result, Industry.class);
        return list;
    }

    /**
     * 删除行业缓存
     * @param key 缓存key
     */
    public void delete(String key) {
        stringJedis.delete(key);
    }
}
