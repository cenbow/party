package com.party.core.dao.read.crowdfund;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.ProjectLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 众筹标签数据写入
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 15:56
 */

@Repository
public interface ProjectLabelReadDao extends BaseReadDao<ProjectLabel> {

    /**
     * 根据项目编号查询
     * @param projectId 项目编号
     * @return 关系列表
     */
    List<ProjectLabel> findByProjectId(@Param(value = "projectId") String projectId);

    /**
     * 根据标签编号查
     * @param labelId 标签编号
     * @return 众筹标签
     */
    List<ProjectLabel> findByLabelId(@Param(value = "labelId") String labelId);

}
