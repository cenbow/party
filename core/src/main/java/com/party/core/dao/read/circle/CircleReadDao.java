package com.party.core.dao.read.circle;

import com.party.common.paging.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.Circle;

import java.util.List;
import java.util.Map;

/**
 * CircleReadDao活动数据读取
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleReadDao extends BaseReadDao<Circle> {
    List<Map<String,Object>> webListPage(@Param(value="circle")Circle circle, @Param(value="params") Map<String, Object> params, Page page);
}
