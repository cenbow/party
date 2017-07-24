package com.party.core.dao.write.crowdfund;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.crowdfund.ProjectLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 众筹标签数据读取
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 15:55
 */
@Repository
public interface ProjectLabelWriteDao extends BaseWriteDao<ProjectLabel> {

    /**
     * 根据项目编号删除
     * @param projectId 项目编号
     * @return 删除结果
     */
    boolean deleteByProjectId(@Param(value = "projectId") String projectId);
}
