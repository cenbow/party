package com.party.core.service.system.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.system.MemberSysRoleReadDao;
import com.party.core.dao.write.system.MemberSysRoleWriteDao;
import com.party.core.model.system.MemberSysRole;
import com.party.core.service.system.IMemberSysRoleService;

/**
 * 用户角色服务实现
 */

@Service
public class MemberSysRoleService implements IMemberSysRoleService {

	@Autowired
	private MemberSysRoleReadDao memberSysRoleReadDao;

	@Autowired
	private MemberSysRoleWriteDao memberSysRoleWriteDao;

	/**
	 * 用户角色关系插入
	 * 
	 * @param userRole
	 *            用户角色
	 * @return 插入结果（true/false）
	 */
	public String insert(MemberSysRole userRole) {
		memberSysRoleWriteDao.insert(userRole);
		return null;
	}

	/**
	 * 用户角色关系更新
	 * 
	 * @param userRole
	 *            用户角色
	 * @return 更新结果（true/false）
	 */
	public boolean update(MemberSysRole userRole) {
		return memberSysRoleWriteDao.update(userRole);
	}

	/**
	 * 用户角色关系逻辑删除
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return memberSysRoleWriteDao.deleteLogic(id);
	}

	/**
	 * 用户角色关系物理删除
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return memberSysRoleWriteDao.delete(id);
	}

	/**
	 * 根据用户ID删除 用户角色关系
	 * 
	 * @param userId
	 *            用户主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteByMemberId(String userId) {
		if (Strings.isNullOrEmpty(userId)) {
			return false;
		}
		return memberSysRoleWriteDao.deleteByMemberId(userId);
	}

	/**
	 * 根据角色ID删除 用户角色关系
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 删除结果（true/false）
	 */
	public boolean deleteByRoleId(String roleId) {
		return false;
	}

	/**
	 * 用户角色关系批量逻辑删除
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return memberSysRoleWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 用户角色关系批量物理删除
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return memberSysRoleWriteDao.batchDelete(ids);
	}

	public MemberSysRole get(String id) {
		return null;
	}

	/**
	 * 用户角色分页查询
	 * 
	 * @param userRole
	 *            用户角色
	 * @param page
	 *            分页信息
	 * @return 用户角色关系列表
	 */
	public List<MemberSysRole> listPage(MemberSysRole userRole, Page page) {
		return memberSysRoleReadDao.listPage(userRole, page);
	}

	/**
	 * 查询所有用户角色关系
	 * 
	 * @param userRole
	 *            用户角色关系
	 * @return 用户角色关系列表
	 */
	public List<MemberSysRole> list(MemberSysRole userRole) {
		return memberSysRoleReadDao.listPage(userRole, null);
	}

	/**
	 * 根据用户主键查找用户角色关系
	 * 
	 * @param userId
	 *            用户主键
	 * @return 用户角色列表
	 */
	public List<MemberSysRole> findByMemberId(String userId) {
		MemberSysRole userRole = new MemberSysRole();
		userRole.setMemberId(userId);
		List<MemberSysRole> userRoleList = this.list(userRole);
		return userRoleList;
	}

	/**
	 * 根据角色主键查找用户角色关系
	 * 
	 * @param roleId
	 *            角色主键
	 * @return 用户角色列表
	 */
	public List<MemberSysRole> findByRoleId(String roleId) {
		MemberSysRole userRole = new MemberSysRole();
		userRole.setRoleId(roleId);
		List<MemberSysRole> userRoleList = this.list(userRole);
		return userRoleList;
	}

	public List<MemberSysRole> batchList(Set<String> ids, MemberSysRole userRole, Page page) {
		return null;
	}

	@Override
	public boolean deleteByRoleIdAndMemberId(String roleId, String memberId) {
		return memberSysRoleWriteDao.deleteByRoleIdAndMemberId(roleId, memberId);
	}
}
