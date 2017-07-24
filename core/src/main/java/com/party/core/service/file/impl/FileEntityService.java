package com.party.core.service.file.impl;

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
import com.party.core.dao.read.file.FileEntityReadDao;
import com.party.core.dao.write.file.FileEntityWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.file.FileEntity;
import com.party.core.service.file.IFileEntityService;
import com.sun.istack.NotNull;

@Service
public class FileEntityService implements IFileEntityService {

	@Autowired
	private FileEntityReadDao fileEntityReadDao;

	@Autowired
	private FileEntityWriteDao fileEntityWriteDao;

	@Override
	public String insert(FileEntity fileEntity) {
		BaseModel.preInsert(fileEntity);
		boolean result = fileEntityWriteDao.insert(fileEntity);
		if (result) {
			return fileEntity.getId();
		}
		return null;
	}

	/**
	 * 反馈更新
	 * 
	 * @param fileEntity
	 *            反馈信息
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(FileEntity fileEntity) {
		fileEntity.setUpdateDate(new Date());
		return fileEntityWriteDao.update(fileEntity);
	}

	/**
	 * 逻辑删除反馈
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
		return fileEntityWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除反馈信息
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
		return fileEntityWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除反馈信息
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
		return fileEntityWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除反馈信息
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
		return fileEntityWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取反馈信息
	 * 
	 * @param id
	 *            主键
	 * @return 反馈信息列表
	 */
	@Override
	public FileEntity get(String id) {
		return fileEntityReadDao.get(id);
	}

	/**
	 * 分页查询反馈信息
	 * 
	 * @param fileEntity
	 *            反馈信息
	 * @param page
	 *            分页信息
	 * @return 反馈列表
	 */
	@Override
	public List<FileEntity> listPage(FileEntity fileEntity, Page page) {
		return fileEntityReadDao.listPage(fileEntity, page);
	}

	/**
	 * 查询所有反馈信息
	 * 
	 * @param fileEntity
	 *            反馈信息
	 * @return 反馈列表
	 */
	@Override
	public List<FileEntity> list(FileEntity fileEntity) {
		return fileEntityReadDao.listPage(fileEntity, null);
	}

	/**
	 * 批量查询反馈信息
	 * 
	 * @param ids
	 *            主键集合
	 * @param fileEntity
	 *            反馈信息
	 * @param page
	 *            分页信息
	 * @return 反馈信息列表
	 */
	@Override
	public List<FileEntity> batchList(@NotNull Set<String> ids, FileEntity fileEntity, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return fileEntityReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public FileEntity getByOptionId(String optionId) {
		return fileEntityReadDao.getByOptionId(optionId);
	}

}
