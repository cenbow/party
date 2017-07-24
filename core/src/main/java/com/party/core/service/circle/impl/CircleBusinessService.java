package com.party.core.service.circle.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleBusinessReadDao;
import com.party.core.dao.write.circle.CircleBusinessWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleBusiness;
import com.party.core.service.circle.ICircleBusinessService;
import com.sun.istack.NotNull;

/**
 * CircleBusinessService圈子标签服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleBusinessService implements ICircleBusinessService{

    @Autowired
    private CircleBusinessReadDao circleBusinessReadDao;
    @Autowired
    private CircleBusinessWriteDao circleBusinessWriteDao;

    /**
     * 插入圈子标签数据
     * @param circleBusiness 圈子标签实体
     * @return 插入结果（true/false）
     */
    public String insert(CircleBusiness circleBusiness) {
        BaseModel.preInsert(circleBusiness);
        boolean result = circleBusinessWriteDao.insert(circleBusiness);
        if (result){
            return circleBusiness.getId();
        }
        return null;
    }

    /**
     * 更新圈子标签信息
     * @param circleBusiness 圈子标签实体
     * @return 更新结果（true/false）
     */
    public boolean update(CircleBusiness circleBusiness) {
        if (null == circleBusiness)
            return false;
        circleBusiness.setUpdateDate(new Date());
        return circleBusinessWriteDao.update(circleBusiness);
    }

    /**
     * 逻辑删除圈子标签数据
     * @param id 圈子标签实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleBusinessWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子标签数据
     * @param id 圈子标签实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleBusinessWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子标签数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleBusinessWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子标签数据
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleBusinessWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取圈子标签实体数据
     * @param id 主键
     * @return 圈子标签实体
     */
    public CircleBusiness get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleBusinessReadDao.get(id);
    }

    /**
     * 分页查询圈子标签列表
     * @param circleBusiness 圈子标签实体
     * @param page 分页信息
     * @return 圈子标签列表
     */
    public List<CircleBusiness> listPage(CircleBusiness circleBusiness, Page page) {
        return circleBusinessReadDao.listPage(circleBusiness, page);
    }

    /**
     * 查询所有圈子标签数据
     * @param circleBusiness 圈子标签实体
     * @return 圈子标签列表
     */
    public List<CircleBusiness> list(CircleBusiness circleBusiness) {
        return circleBusinessReadDao.listPage(circleBusiness, null);
    }

    /**
     * 根据圈子标签主键集合查询圈子标签数据
     * @param ids 主键集合
     * @param circleBusiness 圈子标签实体
     * @param page 分页信息
     * @return 圈子标签列表
     */
    public List<CircleBusiness> batchList(@NotNull Set<String> ids, CircleBusiness circleBusiness, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleBusinessReadDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public CircleBusiness findByBusinessId(String businessId, String type) {
		return circleBusinessReadDao.findByBusinessId(businessId, type);
	}

	@Override
	public boolean delByCircle(String id) {
		if (StringUtils.isBlank(id))
            return false;
		
		return circleBusinessWriteDao.delByCircle(id);
	}
}
