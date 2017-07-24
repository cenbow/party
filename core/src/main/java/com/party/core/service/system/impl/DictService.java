package com.party.core.service.system.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.system.DictReadDao;
import com.party.core.dao.write.system.DictWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.system.Dict;
import com.party.core.service.system.IDictService;
import com.sun.istack.NotNull;

/**
 * 系统字典服务实现
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */

@Service
public class DictService implements IDictService{

    @Autowired
    DictReadDao dictReadDao;

    @Autowired
    DictWriteDao dictWriteDao;

    /**
     * 系统字典插入
     * @param dict 系统字典
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Dict dict) {
    	BaseModel.preInsert(dict);
        boolean result = dictWriteDao.insert(dict);
        if (result){
            return dict.getId();
        }
        return null;
    }

    /**
     * 系统字典更新
     * @param dict 系统字典
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Dict dict) {
        dict.setUpdateDate(new Date());
        return dictWriteDao.update(dict);
    }

    /**
     * 系统字典删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return dictWriteDao.deleteLogic(id);
    }

    /**
     * 系统字典物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        return dictWriteDao.delete(id);
    }

    /**
     * 系统字典批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return dictWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 系统资源批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return dictWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取系统资源
     * @param id 主键
     * @return 系统资源
     */
    @Override
    public Dict get(String id) {
        return dictReadDao.get(id);
    }

    /**
     * 分页查询系统资源
     * @param dict 系统资源
     * @param page 分页信息
     * @return 系统资源列表
     */
    @Override
    public List<Dict> listPage(Dict dict, Page page) {
        return dictReadDao.listPage(dict, page);
    }

    /**
     * 查询所有系统资源
     * @param dict 系统资源
     * @return 系统资源列表
     */
    @Override
    public List<Dict> list(Dict dict) {
        return dictReadDao.listPage(dict, null);
    }

    /**
     * 批量查询系统资源
     * @param ids 主键集合
     * @param dict 系统资源
     * @param page 分页信息
     * @return 系统资源列表
     */
    @Override
    public List<Dict> batchList(@NotNull Set<String> ids, Dict dict, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return dictReadDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public Dict getByProperty(Dict dict) {
		return dictReadDao.getByProperty(dict);
	}
}
