package com.party.core.service.competition.impl;

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
import com.party.core.dao.read.competition.CompetitionBusinessReadDao;
import com.party.core.dao.write.competition.CompetitionBusinessWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.competition.CompetitionBusiness;
import com.party.core.service.competition.ICompetitionBusinessService;
import com.sun.istack.NotNull;

/**
 * 赛事项目与业务表关系
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionBusinessService implements ICompetitionBusinessService {

	@Autowired
	private CompetitionBusinessReadDao competitionBusinessReadDao;
	@Autowired
	private CompetitionBusinessWriteDao competitionBusinessWriteDao;

	/**
	 * 插入圈子标签数据
	 * 
	 * @param circleBusiness
	 *            圈子标签实体
	 * @return 插入结果（true/false）
	 */
	public String insert(CompetitionBusiness circleBusiness) {
		BaseModel.preInsert(circleBusiness);
		boolean result = competitionBusinessWriteDao.insert(circleBusiness);
		if (result) {
			return circleBusiness.getId();
		}
		return null;
	}

	/**
	 * 更新圈子标签信息
	 * 
	 * @param circleBusiness
	 *            圈子标签实体
	 * @return 更新结果（true/false）
	 */
	public boolean update(CompetitionBusiness circleBusiness) {
		if (null == circleBusiness)
			return false;
		circleBusiness.setUpdateDate(new Date());
		return competitionBusinessWriteDao.update(circleBusiness);
	}

	/**
	 * 逻辑删除圈子标签数据
	 * 
	 * @param id
	 *            圈子标签实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return competitionBusinessWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除圈子标签数据
	 * 
	 * @param id
	 *            圈子标签实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(@NotNull String id) {
		if (StringUtils.isBlank(id))
			return false;

		return competitionBusinessWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除圈子标签数据
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDeleteLogic(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return competitionBusinessWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除圈子标签数据
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDelete(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return competitionBusinessWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取圈子标签实体数据
	 * 
	 * @param id
	 *            主键
	 * @return 圈子标签实体
	 */
	public CompetitionBusiness get(String id) {
		if (StringUtils.isBlank(id))
			return null;

		return competitionBusinessReadDao.get(id);
	}

	/**
	 * 分页查询圈子标签列表
	 * 
	 * @param circleBusiness
	 *            圈子标签实体
	 * @param page
	 *            分页信息
	 * @return 圈子标签列表
	 */
	public List<CompetitionBusiness> listPage(CompetitionBusiness circleBusiness, Page page) {
		return competitionBusinessReadDao.listPage(circleBusiness, page);
	}

	/**
	 * 查询所有圈子标签数据
	 * 
	 * @param circleBusiness
	 *            圈子标签实体
	 * @return 圈子标签列表
	 */
	public List<CompetitionBusiness> list(CompetitionBusiness circleBusiness) {
		return competitionBusinessReadDao.listPage(circleBusiness, null);
	}

	/**
	 * 根据圈子标签主键集合查询圈子标签数据
	 * 
	 * @param ids
	 *            主键集合
	 * @param circleBusiness
	 *            圈子标签实体
	 * @param page
	 *            分页信息
	 * @return 圈子标签列表
	 */
	public List<CompetitionBusiness> batchList(@NotNull Set<String> ids, CompetitionBusiness circleBusiness, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return competitionBusinessReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public CompetitionBusiness findByBusinessId(String businessId, String type) {
		return competitionBusinessReadDao.findByBusinessId(businessId, type);
	}
}
