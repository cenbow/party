package com.party.core.service.crowdfund.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.crowdfund.ProjectLabelReadDao;
import com.party.core.dao.write.crowdfund.ProjectLabelWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.ProjectLabel;
import com.party.core.service.crowdfund.IProjectLabelService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 众筹标签接口实现
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 16:45
 */

@Service
public class ProjectLabelService implements IProjectLabelService {

    @Autowired
    private ProjectLabelReadDao projectLabelReadDao;

    @Autowired
    private ProjectLabelWriteDao projectLabelWriteDao;


    /**
     * 插入众筹关系
     * @param projectLabel 众筹标签
     * @return 编号
     */
    @Override
    public String insert(ProjectLabel projectLabel) {
        BaseModel.preInsert(projectLabel);

        boolean result = projectLabelWriteDao.insert(projectLabel);
        if (result){
            return projectLabel.getId();
        }
        return null;
    }

    /**
     * 更新众筹标签
     * @param projectLabel 众筹标签
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(ProjectLabel projectLabel) {
        return projectLabelWriteDao.update(projectLabel);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return projectLabelWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return projectLabelWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return projectLabelWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return projectLabelWriteDao.batchDelete(ids);
    }

    /**
     * 获取众筹标签
     * @param id 主键
     * @return 众筹标签
     */
    @Override
    public ProjectLabel get(String id) {
        return projectLabelReadDao.get(id);
    }

    /**
     * 分页查询
     * @param projectLabel 众筹标签
     * @param page 分页信息
     * @return 众筹列表
     */
    @Override
    public List<ProjectLabel> listPage(ProjectLabel projectLabel, Page page) {
        return projectLabelReadDao.listPage(projectLabel, page);
    }

    /**
     * 查询所有列表
     * @param projectLabel 众筹标签
     * @return 众筹列表
     */
    @Override
    public List<ProjectLabel> list(ProjectLabel projectLabel) {
        return projectLabelReadDao.listPage(projectLabel, null);
    }

    /**
     * 根据项目编号查询
     * @param projectId 项目编号
     * @return 项目列表
     */
    @Override
    public List<ProjectLabel> findByProjectId(String projectId) {
        return projectLabelReadDao.findByProjectId(projectId);
    }

    /**
     * 根据项目删除
     * @param projectId 项目编号
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteByProjectId(String projectId) {
        return projectLabelWriteDao.deleteByProjectId(projectId);
    }

    @Override
    public List<ProjectLabel> batchList(@NotNull Set<String> ids, ProjectLabel projectLabel, Page page) {
        return null;
    }
}
