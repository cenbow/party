package com.party.core.dao.read.count;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.count.DataCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 统计数据写入
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 19:21
 */
@Repository
public interface DataCountReadDao extends BaseReadDao<DataCount> {

    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @return 统计数据
     */
    DataCount findByTargetId(@Param(value = "targetId") String targetId);
}
