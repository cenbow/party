package com.party.core.service.crowdfund.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.crowdfund.ProjectCommentReadDao;
import com.party.core.dao.write.crowdfund.ProjectCommentWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.ProjectComment;
import com.party.core.service.crowdfund.IProjectCommentService;
import com.sun.istack.NotNull;

/**
 * 众筹项目服务接口实现
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 18:40
 */
@Service
public class ProjectCommentService implements IProjectCommentService {

    @Autowired
    private ProjectCommentReadDao projectCommentReadDao;

    @Autowired
    private ProjectCommentWriteDao projectCommentWriteDao;


    /**
     * 项目插入
     * @param comment 项目信息
     * @return 项目编号
     */
    @Override
    public String insert(ProjectComment comment) {
        BaseModel.preInsert(comment);
        boolean result = projectCommentWriteDao.insert(comment);
        if (result){
            return comment.getId();
        }
        return null;
    }

    /**
     * 项目更新
     * @param comment 项目信息
     * @return 是否更新成功（true/false）
     */
    @Override
    public boolean update(ProjectComment comment) {
        return projectCommentWriteDao.update(comment);
    }

    /**
     * 逻辑删除项目
     * @param id 实体主键
     * @return 是否删除成功（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return projectCommentWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除项目信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return projectCommentWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除项目信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return projectCommentWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除项目信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return projectCommentWriteDao.batchDelete(ids);
    }

    /**
     * 根据编号获取项目信息
     * @param id 主键
     * @return 项目信息
     */
    @Override
    public ProjectComment get(String id) {
        return projectCommentReadDao.get(id);
    }

    /**
     * 分页查询项目信息
     * @param comment 项目信息
     * @param page 分页信息
     * @return 项目列表
     */
    @Override
    public List<ProjectComment> listPage(ProjectComment comment, Page page) {
        return projectCommentReadDao.listPage(comment, page);
    }

    /**
     * 查询所有项目信息
     * @param comment 项目信息
     * @return 项目列表
     */
    @Override
    public List<ProjectComment> list(ProjectComment comment) {
        return projectCommentReadDao.listPage(comment,null);
    }

    /**
     * 批量查询项目信息
     * @param ids 主键集合
     * @param comment 项目信息
     * @param page 分页信息
     * @return
     */
    @Override
    public List<ProjectComment> batchList(@NotNull Set<String> ids, ProjectComment comment, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return projectCommentReadDao.batchList(ids, new HashedMap(), page);
    }
}
