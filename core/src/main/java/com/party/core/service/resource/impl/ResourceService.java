package com.party.core.service.resource.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.resource.ResourceReadDao;
import com.party.core.dao.write.resource.ResourceWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.resource.Resource;
import com.party.core.service.resource.IResourceService;
import com.sun.istack.NotNull;

/**
 * 资源服务实现
 * 
 * @author Administrator
 *
 */
@Service
public class ResourceService implements IResourceService {

	@Autowired
	ResourceWriteDao resourceWriteDao;

	@Autowired
	ResourceReadDao resourceReadDao;

	/**
	 * 资源插入
	 * 
	 * @param resource
	 *            资源
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(Resource resource) {
		BaseModel.preInsert(resource);
		boolean result = resourceWriteDao.insert(resource);
		if (result) {
			return resource.getId();
		}
		return null;
	}

	/**
	 * 资源更新
	 * 
	 * @param resource
	 *            资源
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(Resource resource) {
		resource.setUpdateDate(new Date());
		return resourceWriteDao.update(resource);
	}

	/**
	 * 逻辑删除资源
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
		return resourceWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除资源
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
		return resourceWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除资源
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
		return resourceWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除资源
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
		return resourceWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取资源
	 * 
	 * @param id
	 *            主键
	 * @return 资源
	 */
	@Override
	public Resource get(String id) {
		return resourceReadDao.get(id);
	}

	/**
	 * 分页查询资源
	 * 
	 * @param resource
	 *            资源
	 * @param page
	 *            分页信息
	 * @return 资源列表
	 */
	@Override
	public List<Resource> listPage(Resource resource, Page page) {
		return resourceReadDao.listPage(resource, page);
	}

	/**
	 * 查询所有资源
	 * 
	 * @param resource
	 *            资源
	 * @return 资源列表
	 */
	@Override
	public List<Resource> list(Resource resource) {
		return resourceReadDao.listPage(resource, null);
	}

	/**
	 * 批量查询资源
	 * 
	 * @param ids
	 *            主键集合
	 * @param resource
	 *            资源
	 * @param page
	 *            分页信息
	 * @return 资源列表
	 */
	@Override
	public List<Resource> batchList(@NotNull Set<String> ids, Resource resource, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return resourceReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<Resource> findByType(String type) {
		Resource resource = new Resource();
		resource.setType(type);
		return resourceReadDao.listPage(resource, null);
	}

	@Override
	public List<Resource> webListPage(Resource resource, Map<String, Object> params, Page page) {
		return resourceReadDao.webListPage(resource, params, page);
	}
}
