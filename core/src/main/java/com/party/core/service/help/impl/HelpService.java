package com.party.core.service.help.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.help.HelpReadDao;
import com.party.core.dao.write.help.HelpWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.help.Help;
import com.party.core.service.help.IHelpService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * IHelpService接口实现
 *
 * @author Wesley
 * @data 16/9/7 16:57 .
 */
@Service
public class HelpService implements IHelpService {
    @Autowired
    private HelpReadDao helpReadDao;
    @Autowired
    private HelpWriteDao helpWriteDao;

    /**
     * 插入分类数据
     *
     * @param help
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Help help) {
        BaseModel.preInsert(help);
        boolean result = helpWriteDao.insert(help);
        if (result) {
            return help.getId();
        }
        return null;
    }

    /**
     * 更新分类数据
     *
     * @param help
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Help help) {
        if (null == help)
            return false;
        help.setUpdateDate(new Date());
        return helpWriteDao.update(help);
    }

    /**
     * 逻辑删除分类数据
     *
     * @param id 实体主键
     * @return 逻辑删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return helpWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除分类数据
     *
     * @param id 实体主键
     * @return 物理删除结果（true/false）
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return helpWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除分类数据
     *
     * @param ids 主键集合
     * @return 批量逻辑删除记过（true/false）
     */
    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return helpWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除分类数据
     *
     * @param ids 主键集合
     * @return 批量物理删除结果（true/false）
     */
    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return false;
    }

    /**
     * 根据主键获取分类数据
     *
     * @param id 主键
     * @return 分类实体
     */
    @Override
    public Help get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return helpReadDao.get(id);
    }

    /**
     * 分页查询分类列表
     *
     * @param help
     * @param page 分页信息
     * @return 分类列表
     */
    @Override
    public List<Help> listPage(Help help, Page page) {
        return helpReadDao.listPage(help, page);
    }

    /**
     * 查询所有分类列表
     *
     * @param help
     * @return 分类列表
     */
    @Override
    public List<Help> list(Help help) {
        return helpReadDao.listPage(help, null);
    }

    /**
     * 批量分页查询是分类列表
     *
     * @param ids  主键集合
     * @param help
     * @param page 分页信息
     * @return 分类列表
     */
    @Override
    public List<Help> batchList(Set<String> ids, Help help, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return helpReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public List<Help> webListPage(Help help, Map<String, Object> params, Page page) {
        return helpReadDao.webListPage(help, params, page);
    }

    @Override
    public List<Help> webListPage2(Help help, Map<String, Object> params, Page page) {
        return helpReadDao.webListPage2(help, params, page);
    }

    @Override
    public Integer getMaxSort(Help help) {
        return helpReadDao.getMaxSort(help);
    }
}
