package com.party.core.dao.read.distributor;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.WithActivity;
import com.party.core.model.distributor.WithCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 分销关系数据读取
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 14:15
 */

@Repository
public interface DistributorRelationReadDao extends BaseReadDao<DistributorRelation> {

    /**
     * 获取分销关系
     * @param type 类型
     * @param targetId 目标编号
     * @param parentId 父编号
     * @return 分销关系
     */
    DistributorRelation findByUniqueness(@Param(value = "type") Integer type,
                                         @Param(value = "targetId") String targetId,
                                         @Param(value = "distributorId") String distributorId);

    /**
     * 我分销的活动
     * @param param 分销活动
     * @return 分销的活动
     */
    List<WithActivity> activityList(@Param(value = "param") HashMap<String, Object> param, Page page);

    /**
     * 我分销的统计
     * @param param 参数
     * @param page 分页
     * @return 统计列表
     */
    List<WithCount> listWithCount(@Param(value = "param") HashMap<String, Object> param, Page page);


    /**
     * 根据上级查找分销关系
     * @param param 上级编号
     * @return 分销关系
     */
    List<WithCount> findByParentId(@Param(value = "param") HashMap<String, Object> param, Page page);

    /**
     * 根据事件查询代言数
     * @param eventId 事件编号
     * @return 代言数
     */
    Integer countForEvent(@Param(value = "eventId") String eventId);

}
