package com.party.core.dao.read.store;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.store.StoreCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 店铺数据统计读取
 * party
 * Created by wei.li
 * on 2016/10/11 0011.
 */
@Repository
public interface StoreCountReadDao extends BaseReadDao<StoreCount> {

    /**
     * 统计店铺统计数据
     * @param query 查询条件
     * @return 统计数据
     */
    StoreCount count(@Param("query") Map<String, Object> query);
}
