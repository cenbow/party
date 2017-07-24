package com.party.core.dao.read.circle;

import com.party.common.paging.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleApply;
import com.party.core.model.circle.CircleTag;

import java.util.List;
import java.util.Map;

/**
 * CircleApplyReadDao
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleApplyReadDao extends BaseReadDao<CircleApply> {
    List<Map<String,Object>> webListPage(@Param(value="apply")CircleApply apply, @Param(value="params")Map<String, Object> params, Page page);
}
