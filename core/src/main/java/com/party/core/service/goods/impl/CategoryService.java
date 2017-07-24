package com.party.core.service.goods.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.goods.CategoryReadDao;
import com.party.core.dao.write.goods.CategoryWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.goods.Category;
import com.party.core.service.IBaseService;
import com.party.core.service.goods.ICategoryService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * IGoodsService 分类服务接口现象
 *
 * @author Wesley
 * @data 16/9/7 16:57 .
 */
@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private CategoryReadDao readDao;

    @Autowired
    private CategoryWriteDao writeDao;

    /**
     * 插入分类数据
     * @param category
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Category category) {
        BaseModel.preInsert(category);
        boolean result = writeDao.insert(category);
        if (result){
            return category.getId();
        }
        return null;
    }

    /**
     * 更新分类数据
     * @param category
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Category category) {
        if (null == category)
            return false;
        category.setUpdateDate(new Date());
        return writeDao.update(category);
    }

    /**
     * 逻辑删除分类数据
     * @param id 实体主键
     * @return 逻辑删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.deleteLogic(id);
    }

    /**
     * 物理删除分类数据
     * @param id 实体主键
     * @return 物理删除结果（true/false）
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.delete(id);
    }

    /**
     * 批量逻辑删除分类数据
     * @param ids 主键集合
     * @return 批量逻辑删除记过（true/false）
     */
    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除分类数据
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
     * @param id 主键
     * @return 分类实体
     */
    @Override
    public Category get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return readDao.get(id);
    }

    /**
     * 分页查询分类列表
     * @param category
     * @param page 分页信息
     * @return 分类列表
     */
    @Override
    public List<Category> listPage(Category category, Page page) {
        return readDao.listPage(category, page);
    }

    /**
     * 查询所有分类列表
     * @param category
     * @return 分类列表
     */
    @Override
    public List<Category> list(Category category) {
        return readDao.listPage(category, null);
    }

    /**
     * 批量分页查询是分类列表
     * @param ids 主键集合
     * @param category
     * @param page 分页信息
     * @return 分类列表
     */
    @Override
    public List<Category> batchList(Set<String> ids, Category category, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return readDao.batchList(ids, new HashedMap(), page);
    }
}
