package com.party.core.service.circle.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleTopicTagReadDao;
import com.party.core.dao.write.circle.CircleTopicTagWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTopicTag;
import com.party.core.service.circle.ICircleTopicTagService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *圈子话题类型服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleTopicTagService implements ICircleTopicTagService {

	@Autowired
	private CircleTopicTagReadDao circleTopicTagReadDao;
	@Autowired
	private CircleTopicTagWriteDao circleTopicTagWriteDao;

	/**
	 * 插入圈子类型数据
	 * 
	 * @param circleTopicTag
	 *           圈子话题类型实体
	 * @return 插入结果（true/false）
	 */
	public String insert(CircleTopicTag circleTopicTag) {
		BaseModel.preInsert(circleTopicTag);
		boolean result = circleTopicTagWriteDao.insert(circleTopicTag);
		if (result) {
			return circleTopicTag.getId();
		}
		return null;
	}

	/**
	 * 更新圈子类型信息
	 * 
	 * @param circleTopicTag
	 *           圈子话题类型实体
	 * @return 更新结果（true/false）
	 */
	public boolean update(CircleTopicTag circleTopicTag) {
		if (null == circleTopicTag)
			return false;
		circleTopicTag.setUpdateDate(new Date());
		return circleTopicTagWriteDao.update(circleTopicTag);
	}

	/**
	 * 逻辑删除圈子类型数据
	 * 
	 * @param id
	 *           圈子话题类型实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return circleTopicTagWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除圈子类型数据
	 * 
	 * @param id
	 *           圈子话题类型实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return circleTopicTagWriteDao.delete(id);
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

		return circleTopicTagWriteDao.batchDeleteLogic(ids);
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

		return circleTopicTagWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取圈子类型实体数据
	 * 
	 * @param id
	 *            主键
	 * @return圈子话题类型实体
	 */
	public CircleTopicTag get(String id) {
		if (StringUtils.isBlank(id))
			return null;

		return circleTopicTagReadDao.get(id);
	}

	/**
	 * 分页查询圈子类型列表
	 * 
	 * @param circleTopicTag
	 *           圈子话题类型实体
	 * @param page
	 *            分页信息
	 * @return圈子话题类型列表
	 */
	public List<CircleTopicTag> listPage(CircleTopicTag circleTopicTag, Page page) {
		return circleTopicTagReadDao.listPage(circleTopicTag, page);
	}

	/**
	 * 查询所有圈子类型数据
	 * 
	 * @param circleTopicTag
	 *           圈子话题类型实体
	 * @return圈子话题类型列表
	 */
	public List<CircleTopicTag> list(CircleTopicTag circleTopicTag) {
		return circleTopicTagReadDao.listPage(circleTopicTag, null);
	}

	/**
	 * 根据圈子类型主键集合查询圈子类型数据
	 * 
	 * @param ids
	 *            主键集合
	 * @param circleTopicTag
	 *           圈子话题类型实体
	 * @param page
	 *            分页信息
	 * @return圈子话题类型列表
	 */
	public List<CircleTopicTag> batchList(@NotNull Set<String> ids, CircleTopicTag circleTopicTag, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return circleTopicTagReadDao.batchList(ids, new HashedMap(), page);
	}

	/**
	 * 通过圈子id删除话题标签
	 * @param id
	 */
	@Override
	public void delByCircle(String id) {
		circleTopicTagWriteDao.delByCircle(id);
	}
}
