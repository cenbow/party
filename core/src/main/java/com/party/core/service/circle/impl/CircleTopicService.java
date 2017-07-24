package com.party.core.service.circle.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleTopicReadDao;
import com.party.core.dao.write.circle.CircleTopicWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.service.circle.ICircleTopicService;
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
 * 圈子话题服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleTopicService implements ICircleTopicService {

	@Autowired
	private CircleTopicReadDao circleTopicReadDao;
	@Autowired
	private CircleTopicWriteDao circleTopicWriteDao;

	/**
	 * 插入圈子话题数据
	 * 
	 * @param circleTopic
	 *            圈子话题实体
	 * @return 插入结果（true/false）
	 */
	public String insert(CircleTopic circleTopic) {
		BaseModel.preInsert(circleTopic);
		boolean result = circleTopicWriteDao.insert(circleTopic);
		if (result) {
			return circleTopic.getId();
		}
		return null;
	}

	/**
	 * 更新圈子话题信息
	 * 
	 * @param circleTopic
	 *            圈子话题实体
	 * @return 更新结果（true/false）
	 */
	public boolean update(CircleTopic circleTopic) {
		if (null == circleTopic)
			return false;
		circleTopic.setUpdateDate(new Date());
		return circleTopicWriteDao.update(circleTopic);
	}

	/**
	 * 逻辑删除圈子话题数据
	 * 
	 * @param id
	 *            圈子话题实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return circleTopicWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除圈子话题数据
	 * 
	 * @param id
	 *            圈子话题实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return circleTopicWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除圈子话题数据
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDeleteLogic(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return circleTopicWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除圈子话题数据
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDelete(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return circleTopicWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取圈子话题实体数据
	 * 
	 * @param id
	 *            主键
	 * @return 圈子话题实体
	 */
	public CircleTopic get(String id) {
		if (StringUtils.isBlank(id))
			return null;

		return circleTopicReadDao.get(id);
	}

	/**
	 * 分页查询圈子话题列表
	 * 
	 * @param circleTopic
	 *            圈子话题实体
	 * @param page
	 *            分页信息
	 * @return 圈子话题列表
	 */
	public List<CircleTopic> listPage(CircleTopic circleTopic, Page page) {
		return circleTopicReadDao.listPage(circleTopic, page);
	}

	/**
	 * 查询所有圈子话题数据
	 * 
	 * @param circleTopic
	 *            圈子话题实体
	 * @return 圈子话题列表
	 */
	public List<CircleTopic> list(CircleTopic circleTopic) {
		return circleTopicReadDao.listPage(circleTopic, null);
	}

	/**
	 * 根据圈子话题主键集合查询圈子话题数据
	 * 
	 * @param ids
	 *            主键集合
	 * @param circleTopic
	 *            圈子话题实体
	 * @param page
	 *            分页信息
	 * @return 圈子话题列表
	 */
	public List<CircleTopic> batchList(@NotNull Set<String> ids, CircleTopic circleTopic, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return circleTopicReadDao.batchList(ids, new HashedMap(), page);
	}

}
