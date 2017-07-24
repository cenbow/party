package com.party.core.service.activity.impl;

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
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.activity.CrowfundResourcesReadDao;
import com.party.core.dao.write.activity.CrowfundResourcesWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.CrowfundResources;
import com.party.core.service.activity.ICrowfundResourcesService;
import com.sun.istack.NotNull;

/**
 * 动态评论服务实现 party Created by wei.li on 2016/9/19 0019.
 */
@Service
public class CrowfundResourcesService implements ICrowfundResourcesService {

	@Autowired
	CrowfundResourcesReadDao crowfundResourcesReadDao;

	@Autowired
	CrowfundResourcesWriteDao crowfundResourcesWriteDao;

	/**
	 * 动态图片插入
	 * 
	 * @param crowfundResources
	 *            动态评论
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(CrowfundResources crowfundResources) {
		BaseModel.preInsert(crowfundResources);
		boolean result = crowfundResourcesWriteDao.insert(crowfundResources);
		if (result) {
			return crowfundResources.getId();
		}
		return null;
	}

	/**
	 * 动态图片更新
	 * 
	 * @param crowfundResources
	 *            动态图片
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(CrowfundResources crowfundResources) {
		crowfundResources.setUpdateDate(new Date());
		return crowfundResourcesWriteDao.update(crowfundResources);
	}

	/**
	 * 逻辑删除动态图片
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean deleteLogic(@NotNull String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return crowfundResourcesWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除动态图片
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean delete(@NotNull String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return crowfundResourcesWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除动态图片
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean batchDeleteLogic(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return crowfundResourcesWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除动态图片
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean batchDelete(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return crowfundResourcesWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取动态图片
	 * 
	 * @param id
	 *            主键
	 * @return 动态图片
	 */
	@Override
	public CrowfundResources get(String id) {
		return crowfundResourcesReadDao.get(id);
	}

	/**
	 * 分页查询动态图片
	 * 
	 * @param crowfundResources
	 *            动态图片
	 * @param page
	 *            分页信息
	 * @return 动态图片列表
	 */
	@Override
	public List<CrowfundResources> listPage(CrowfundResources crowfundResources, Page page) {
		return crowfundResourcesReadDao.listPage(crowfundResources, page);
	}

	/**
	 * 查询所有动态图片
	 * 
	 * @param crowfundResources
	 *            动态图片
	 * @return 动态图片列表
	 */
	@Override
	public List<CrowfundResources> list(CrowfundResources crowfundResources) {
		return crowfundResourcesReadDao.listPage(crowfundResources, null);
	}

	/**
	 * 批量查询动态图片
	 * 
	 * @param ids
	 *            主键集合
	 * @param crowfundResources
	 *            动态图片
	 * @param page
	 *            分页信息
	 * @return 动态图片列表
	 */
	@Override
	public List<CrowfundResources> batchList(@NotNull Set<String> ids, CrowfundResources crowfundResources, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return crowfundResourcesReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<CrowfundResources> findByRefId(String refId, String type) {
		if (StringUtils.isEmpty(refId)) {
			return Collections.EMPTY_LIST;
		}
		CrowfundResources crowfundResource = new CrowfundResources();
		crowfundResource.setRefId(refId);
		crowfundResource.setType(type);
		return crowfundResourcesReadDao.listPage(crowfundResource, null);
	}

	@Override
	public boolean deleteByRefId(String refId, String type) {
		if (StringUtils.isEmpty(refId)) {
			return false;
		}
		return crowfundResourcesWriteDao.deleteByRefId(refId, type);

	}

}
