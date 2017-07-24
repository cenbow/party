package com.party.core.service.crowdfund;

import com.party.core.model.crowdfund.ProjectLabel;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 众筹标签接口
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 16:44
 */
public interface IProjectLabelService extends IBaseService<ProjectLabel> {

    /**
     * 根据项目编号查询
     * @param projectId 项目编号
     * @return 项目标签
     */
    List<ProjectLabel> findByProjectId(String projectId);

    /**
     * 根据项目编号查询
     * @param projectId 项目编号
     * @return 删除结果（true/false）
     */
    boolean deleteByProjectId(String projectId);
}
