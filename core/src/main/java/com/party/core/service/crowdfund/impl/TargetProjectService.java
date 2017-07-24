package com.party.core.service.crowdfund.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.crowdfund.TargetProjectReadDao;
import com.party.core.dao.write.crowdfund.TargetProjectWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 众筹对象关系服务接口实现
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 14:05
 */

@Service
public class TargetProjectService implements ITargetProjectService {

    @Autowired
    private TargetProjectReadDao targetProjectReadDao;

    @Autowired
    private TargetProjectWriteDao targetProjectWriteDao;

    /**
     * 众筹关系插入
     * @param targetProject 关系实体
     * @return 编号
     */
    @Override
    public String insert(TargetProject targetProject) {
        BaseModel.preInsert(targetProject);
        boolean result = targetProjectWriteDao.insert(targetProject);
        if (result){
            return targetProject.getId();
        }
        return null;
    }

    /**
     * 众筹关系更新
     * @param targetProject 关系实体
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(TargetProject targetProject) {
        return targetProjectWriteDao.update(targetProject);
    }

    /**
     * 众筹关系逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return targetProjectWriteDao.deleteLogic(id);
    }

    /**
     * 众筹关系物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return targetProjectWriteDao.delete(id);
    }

    /**
     * 众筹关系批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return targetProjectWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 众筹关系批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return targetProjectWriteDao.batchDelete(ids);
    }

    /**
     * 获取众筹关系
     * @param id 主键
     * @return 众筹关系
     */
    @Override
    public TargetProject get(String id) {
        return targetProjectReadDao.get(id);
    }


    /**
     * 根据众筹编号查找众筹关系
     * @param projectId 项目编号
     * @return 众筹关系
     */
    @Override
    public TargetProject findByProjectId(String projectId) {
        return targetProjectReadDao.findByProjectId(projectId);
    }


    /**
     * 根据订单号查询众筹关系
     * @param orderId 订单号
     * @return 订单号
     */
    @Override
    public TargetProject findByOrderId(String orderId) {
        return targetProjectReadDao.findByOrderId(orderId);
    }

    /**
     * 分页查询众筹关系
     * @param targetProject 众筹关系
     * @param page 分页信息
     * @return 众筹关系列表
     */
    @Override
    public List<TargetProject> listPage(TargetProject targetProject, Page page) {
        return targetProjectReadDao.listPage(targetProject, page);
    }

    /**
     * 查询所有众筹关系
     * @param targetProject 众筹关系
     * @return 众筹关系列表
     */
    @Override
    public List<TargetProject> list(TargetProject targetProject) {
        return targetProjectReadDao.listPage(targetProject, null);
    }

    /**
     * 批量查询众筹关系
     * @param ids 主键集合
     * @param targetProject 众筹关系
     * @param page 分页信息
     * @return 众筹关系列表
     */
    @Override
    public List<TargetProject> batchList(@NotNull Set<String> ids, TargetProject targetProject, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return targetProjectReadDao.batchList(ids, new HashedMap(), page);
    }
}
