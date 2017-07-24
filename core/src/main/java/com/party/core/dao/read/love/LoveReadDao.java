package com.party.core.dao.read.love;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.love.Love;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 点赞数据读取
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Repository
public interface LoveReadDao extends BaseReadDao<Love> {
    /**
     * 统计点赞数
     * @param entity
     * @return
     */
    public Integer countLove(Love entity);

    /**
     * web端查询
     * @param love
     * @param params
     * @param page
     * @return
     */
	public List<Love> webListPage(@Param(value = "love") Love love, @Param(value = "params") Map<String, Object> params, Page page);
}
