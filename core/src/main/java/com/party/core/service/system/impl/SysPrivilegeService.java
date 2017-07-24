package com.party.core.service.system.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.party.common.utils.UUIDUtils;
import com.party.core.model.BaseModel;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.system.SysPrivilegeReadDao;
import com.party.core.dao.write.system.SysPrivilegeWriteDao;
import com.party.core.model.menu.Menu;
import com.party.core.model.system.SysPrivilege;
import com.party.core.model.system.SysRolePrivilege;
import com.party.core.service.system.ISysPrivilegeService;
import com.sun.istack.NotNull;

/**
 * 权限接口实现
 * 
 * @author Administrator
 *
 */
@Service
public class SysPrivilegeService implements ISysPrivilegeService {

	@Autowired
	SysPrivilegeReadDao sysPrivilegeReadDao;

	@Autowired
	SysPrivilegeWriteDao sysPrivilegeWriteDao;

	@Autowired
	SysRolePrivilegeService sysRolePrivilegeService;

	/**
	 * 系统字典插入
	 * 
	 * @param sysPrivilege
	 *            系统字典
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(SysPrivilege sysPrivilege) {
		sysPrivilege.setId(UUIDUtils.generateRandomUUID());
		sysPrivilege.setCreateDate(new Date());
		sysPrivilege.setUpdateDate(new Date());
		sysPrivilege.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

		if (sysPrivilege.getParentId().equals("0")){
			sysPrivilege.setParentIds("0");
		}
		else {
			SysPrivilege parent = get(sysPrivilege.getParentId());
			StrBuilder parents = new StrBuilder(parent.getParentIds()).append(",").append(sysPrivilege.getParentId());
			sysPrivilege.setParentIds(parents.toString());
		}
		boolean result = sysPrivilegeWriteDao.insert(sysPrivilege);
		if (result) {
			return sysPrivilege.getId();
		}
		return null;
	}

	/**
	 * 系统字典更新
	 * 
	 * @param sysPrivilege
	 *            系统字典
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(SysPrivilege sysPrivilege) {
		sysPrivilege.setUpdateDate(new Date());
		if (sysPrivilege.getParentId().equals("0")){
			sysPrivilege.setParentIds("0");
		}
		else {
			SysPrivilege parent = get(sysPrivilege.getParentId());
			StrBuilder parents = new StrBuilder(parent.getParentIds()).append(",").append(sysPrivilege.getParentId());
			sysPrivilege.setParentIds(parents.toString());
		}
		return sysPrivilegeWriteDao.update(sysPrivilege);
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
		return sysPrivilegeWriteDao.deleteLogic(id);
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
		return sysPrivilegeWriteDao.delete(id);
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
		return sysPrivilegeWriteDao.batchDeleteLogic(ids);
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
		return sysPrivilegeWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取系统资源
	 * 
	 * @param id
	 *            主键
	 * @return 系统资源
	 */
	@Override
	public SysPrivilege get(String id) {
		return sysPrivilegeReadDao.get(id);
	}

	/**
	 * 分页查询系统资源
	 * 
	 * @param sysPrivilege
	 *            系统资源
	 * @param page
	 *            分页信息
	 * @return 系统资源列表
	 */
	@Override
	public List<SysPrivilege> listPage(SysPrivilege sysPrivilege, Page page) {
		return sysPrivilegeReadDao.listPage(sysPrivilege, page);
	}

	/**
	 * 查询所有系统资源
	 * 
	 * @param sysPrivilege
	 *            系统资源
	 * @return 系统资源列表
	 */
	@Override
	public List<SysPrivilege> list(SysPrivilege sysPrivilege) {
		return sysPrivilegeReadDao.listPage(sysPrivilege, null);
	}


	/**
	 * 查询顶级权限
	 * @return 权限列表
	 */
	@Override
	public List<SysPrivilege> findTop() {
		SysPrivilege sysPrivilege = new SysPrivilege();
		sysPrivilege.setParentId("0");
		return this.list(sysPrivilege);
	}

	/**
	 * 根据父编号查询
	 * @param parentId 父编号
	 * @return 权限列表
	 */
	@Override
	public List<SysPrivilege> findByParent(String parentId) {
		SysPrivilege sysPrivilege = new SysPrivilege();
		sysPrivilege.setParentId(parentId);
		return this.list(sysPrivilege);
	}

	/**
	 * 批量查询系统资源
	 * 
	 * @param ids
	 *            主键集合
	 * @param sysPrivilege
	 *            系统资源
	 * @param page
	 *            分页信息
	 * @return 系统资源列表
	 */
	@Override
	public List<SysPrivilege> batchList(@NotNull Set<String> ids, SysPrivilege sysPrivilege, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return sysPrivilegeReadDao.batchList(ids, new HashedMap(), page);
	}

	/**
	 * 查看会员权限
	 * @param memberId 会员编号
	 * @return 权限集合
	 */
	@Override
	public List<SysPrivilege> findByMemberId(String memberId) {
		return sysPrivilegeReadDao.findByMemberId(memberId);
	}

	/**
	 * 根据角色查询权限
	 * @param roles 角色集合
	 * @return 权限集合
	 */
	@Override
	public List<SysPrivilege> findByRoles(Set<String> roles) {
		return sysPrivilegeReadDao.findByRoles(roles);
	}

	/**
	 * 提取资源代码
	 * @param privileges 资源集合
	 * @return 代码集合
	 */
	@Override
	public Set<String> extractCode(List<SysPrivilege> privileges) {
		Set<String> set = Sets.newHashSet();
		for (SysPrivilege privilege : privileges){
			set.add(privilege.getPermission());
		}
		return set;
	}

	@Override
	public List<SysPrivilege> findByRoleId(String roleId) {
		List<SysRolePrivilege> rolePrivileges = sysRolePrivilegeService.findByRoleId(roleId);
		List<String> privileges = LangUtils.transform(rolePrivileges, input -> input.getPrivilegeId());
		if (CollectionUtils.isEmpty(privileges)) {
			return Collections.EMPTY_LIST;
		}
		List<SysPrivilege> menuList = sysPrivilegeReadDao.batchList(new HashSet<>(privileges), null, null);
		return menuList;
	}
}
