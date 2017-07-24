package com.party.core.dao.read.notify;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.notify.TargetTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 目标模板数据读取
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 9:47
 */

@Repository
public interface TargetTemplateReadDao extends BaseReadDao<TargetTemplate> {

    /**
     * 根据目标编号查找模板
     * @param targetId 目标编号
     * @return 模板信息
     */
    TargetTemplate findByTargetId(@Param(value = "targetId") String targetId, @Param(value = "type") Integer type);
}
