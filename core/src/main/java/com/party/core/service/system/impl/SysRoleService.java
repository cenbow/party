package com.party.core.service.system.impl;

import java.util.*;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.party.core.model.BaseModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.system.SysRoleReadDao;
import com.party.core.dao.write.system.SysRoleWriteDao;
import com.party.core.model.system.MemberSysRole;
import com.party.core.model.system.SysRole;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.core.service.system.ISysRoleService;
import com.sun.istack.NotNull;

/**
 * 角色接口实现
 * 
 * @author Administrator
 *
 */
@Service
public class SysRoleService implements ISysRoleService {

	@Autowired
	SysRoleReadDao sysRoleReadDao;

	@Autowired
	SysRoleWriteDao sysRoleWriteDao;

	@Autowired
	IMemberSysRoleService memberSysRoleService;

	/**
	 * 系统字典插入
	 * 
	 * @param sysRole
	 *            系统字典
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(SysRole sysRole) {
		BaseModel.preInsert(sysRole);
		boolean result = sysRoleWriteDao.insert(sysRole);
		if (result) {
			return sysRole.getId();
		}
		return null;
	}

	/**
	 * 系统字典更新
	 * 
	 * @param sysRole
	 *            系统字典
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(SysRole sysRole) {
		sysRole.setUpdateDate(new Date());
		return sysRoleWriteDao.update(sysRole);
	}

	/**
	 * 系统字典删除
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
		return sysRoleWriteDao.deleteLogic(id);
	}

	/**
	 * 系统字典物理删除
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean delete(@NotNull String id) {
		return sysRoleWriteDao.delete(id);
	}

	/**
	 * 系统字典批量逻辑删除
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
		return sysRoleWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 系统资源批量物理删除
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
		return sysRoleWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取系统资源
	 * 
	 * @param id
	 *            主键
	 * @return 系统资源
	 */
	@Override
	public SysRole get(String id) {
		return sysRoleReadDao.get(id);
	}

	/**
	 * 分页查询系统资源
	 * 
	 * @param sysRole
	 *            系统资源
	 * @param page
	 *            分页信息
	 * @return 系统资源列表
	 */
	@Override
	public List<SysRole> listPage(SysRole sysRole, Page page) {
		return sysRoleReadDao.listPage(sysRole, page);
	}

	/**
	 * 查询所有系统资源
	 * 
	 * @param sysRole
	 *            系统资源
	 * @return 系统资源列表
	 */
	@Override
	public List<SysRole> list(SysRole sysRole) {
		return sysRoleReadDao.listPage(sysRole, null);
	}

	/**
	 * 批量查询系统资源
	 * 
	 * @param ids
	 *            主键集合
	 * @param sysRole
	 *            系统资源
	 * @param page
	 *            分页信息
	 * @return 系统资源列表
	 */
	@Override
	public List<SysRole> batchList(@NotNull Set<String> ids, SysRole sysRole, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return sysRoleReadDao.batchList(ids, new HashedMap(), page);
	}

	/**
	 * 提取角色代码
	 * @param roles 角色集合
	 * @return 代码集合
	 */
	@Override
	public Set<String> extractCode(List<SysRole> roles) {
		Set<String> set = Sets.newHashSet();
		for (SysRole role : roles){
			set.add(role.getCode());
		}
		return set;
	}

	@Override
	public List<SysRole> getRoleByMemberId(Map<String, Object> params) {
		return sysRoleReadDao.getRoleByMemberId(params);
	}

	/**
	 * 根据会员编号查询
	 * @param memberId 会员编号
	 * @return 角色集合
	 */
	@Override
	public List<SysRole> findByMemberId(String memberId) {
		return this.findByMemberId(memberId, null);
	}

	/**
	 * 根据会员编号查询
	 * @param memberId 会员编号
	 * @param type 类型
	 * @return 角色集合
	 */
	@Override
	public List<SysRole> findByMemberId(String memberId, Integer type) {
		List<MemberSysRole> userRoleList = memberSysRoleService.findByMemberId(memberId);
		List<String> roleIdList = LangUtils.transform(userRoleList, input -> input.getRoleId());
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Collections.EMPTY_LIST;
		}

		Map<String, Object> param = Maps.newHashMap();
		param.put("type", type);
		List<SysRole> roleList = sysRoleReadDao.batchList(new HashSet<>(roleIdList), param, null);
		return roleList;
	}

	@Override
	public SysRole uniqueProperty(String property, String value) {
		return sysRoleReadDao.uniqueProperty(property, value);
	}
}
