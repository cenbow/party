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
import com.party.core.dao.read.circle.CircleTypeReadDao;
import com.party.core.dao.write.circle.CircleTypeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleType;
import com.party.core.service.circle.ICircleTypeService;
import com.sun.istack.NotNull;

/**
 * 圈子类型服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleTypeService implements ICircleTypeService {

	@Autowired
	private CircleTypeReadDao circleTypeReadDao;
	@Autowired
	private CircleTypeWriteDao circleTypeWriteDao;

	/**
	 * 插入圈子类型数据
	 * 
	 * @param circleTag
	 *            圈子类型实体
	 * @return 插入结果（true/false）
	 */
	public String insert(CircleType circleTag) {
		BaseModel.preInsert(circleTag);
		boolean result = circleTypeWriteDao.insert(circleTag);
		if (result) {
			return circleTag.getId();
		}
		return null;
	}

	/**
	 * 更新圈子类型信息
	 * 
	 * @param circleTag
	 *            圈子类型实体
	 * @return 更新结果（true/false）
	 */
	public boolean update(CircleType circleTag) {
		if (null == circleTag)
			return false;
		circleTag.setUpdateDate(new Date());
		return circleTypeWriteDao.update(circleTag);
	}

	/**
	 * 逻辑删除圈子类型数据
	 * 
	 * @param id
	 *            圈子类型实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return circleTypeWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除圈子类型数据
	 * 
	 * @param id
	 *            圈子类型实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return circleTypeWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除圈子类型数据
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDeleteLogic(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return circleTypeWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除圈子类型数据
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDelete(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return circleTypeWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取圈子类型实体数据
	 * 
	 * @param id
	 *            主键
	 * @return 圈子类型实体
	 */
	public CircleType get(String id) {
		if (StringUtils.isBlank(id))
			return null;

		return circleTypeReadDao.get(id);
	}

	/**
	 * 分页查询圈子类型列表
	 * 
	 * @param circleTag
	 *            圈子类型实体
	 * @param page
	 *            分页信息
	 * @return 圈子类型列表
	 */
	public List<CircleType> listPage(CircleType circleTag, Page page) {
		return circleTypeReadDao.listPage(circleTag, page);
	}

	/**
	 * 查询所有圈子类型数据
	 * 
	 * @param circleTag
	 *            圈子类型实体
	 * @return 圈子类型列表
	 */
	public List<CircleType> list(CircleType circleTag) {
		return circleTypeReadDao.listPage(circleTag, null);
	}

	/**
	 * 根据圈子类型主键集合查询圈子类型数据
	 * 
	 * @param ids
	 *            主键集合
	 * @param circleTag
	 *            圈子类型实体
	 * @param page
	 *            分页信息
	 * @return 圈子类型列表
	 */
	public List<CircleType> batchList(@NotNull Set<String> ids, CircleType circleTag, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return circleTypeReadDao.batchList(ids, new HashedMap(), page);
	}
}
