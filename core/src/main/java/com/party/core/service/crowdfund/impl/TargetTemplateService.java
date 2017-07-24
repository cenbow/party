package com.party.core.service.crowdfund.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.notify.TargetTemplateReadDao;
import com.party.core.dao.write.notify.TargetTemplateWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.notify.TargetTemplate;
import com.party.core.service.crowdfund.ITargetTemplateService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 目标模板接口实现
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 11:19
 */

@Service
public class TargetTemplateService implements ITargetTemplateService {

    @Autowired
    private TargetTemplateReadDao targetTemplateReadDao;

    @Autowired
    private TargetTemplateWriteDao targetTemplateWriteDao;

    /**
     * 模板插入
     * @param targetTemplate 模板
     * @return 编号
     */
    @Override
    public String insert(TargetTemplate targetTemplate) {
        BaseModel.preInsert(targetTemplate);
        boolean result = targetTemplateWriteDao.insert(targetTemplate);
        if (result){
            return targetTemplate.getId();
        }
        return null;
    }

    /**
     * 更新模板
     * @param targetTemplate 模板消息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(TargetTemplate targetTemplate) {
        return targetTemplateWriteDao.update(targetTemplate);
    }

    /**
     * 逻辑删除目标模板
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return targetTemplateWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除目标模板
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return targetTemplateWriteDao.delete(id);
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
        return targetTemplateWriteDao.batchDeleteLogic(ids);
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
        return targetTemplateWriteDao.batchDelete(ids);
    }

    /**
     * 获取模板消息
     * @param id 主键
     * @return 模板消息
     */
    @Override
    public TargetTemplate get(String id) {
        return targetTemplateReadDao.get(id);
    }

    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @param type 类型
     * @return 模板信息
     */
    @Override
    public TargetTemplate findByTargetId(String targetId, Integer type) {
        return targetTemplateReadDao.findByTargetId(targetId, type);
    }

    /**
     * 分页查询模板消息
     * @param targetTemplate 模板消息
     * @param page 分页信息
     * @return 模板列表
     */
    @Override
    public List<TargetTemplate> listPage(TargetTemplate targetTemplate, Page page) {
        return targetTemplateReadDao.listPage(targetTemplate, page);
    }

    /**
     * 模板列表
     * @param targetTemplate 模板信息
     * @return 列表数据
     */
    @Override
    public List<TargetTemplate> list(TargetTemplate targetTemplate) {
        return targetTemplateReadDao.listPage(targetTemplate, null);
    }


    /**
     * 批量查询模板信息
     * @param ids 主键集合
     * @param targetTemplate
     * @param page 分页信息
     * @return 列表数据
     */
    @Override
    public List<TargetTemplate> batchList(@NotNull Set<String> ids, TargetTemplate targetTemplate, Page page) {
        return null;
    }
}
