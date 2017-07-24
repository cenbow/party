package com.party.admin.web.controller.system;

import java.util.*;

import com.google.common.base.Strings;
import com.party.core.model.system.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.core.service.system.ISysPrivilegeService;
import com.party.core.service.system.ISysRolePrivilegeService;
import com.party.core.service.system.ISysRoleService;

/**
 * 角色控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/system/role")
public class SysRoleController {

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysPrivilegeService sysPrivilegeService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMemberSysRoleService memberSysRoleService;

	@Autowired
	private ISysRolePrivilegeService sysRolePrivilegeService;

	/**
	 * 跳转至列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "roleList")
	public ModelAndView publishList(SysRole sysRole, Page page) {
		ModelAndView mv = new ModelAndView("system/role/roleList");
		page.setLimit(10);
		sysRole.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<SysRole> sysRoles = sysRoleService.listPage(sysRole, page);
		Map<Integer, String> types = RoleType.convertMap();
		mv.addObject("types", types);
		mv.addObject("sysRoles", sysRoles);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 跳转至 分配权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "roleAuth")
	public ModelAndView roleAuth(String roleId) {
		ModelAndView mv = new ModelAndView("system/role/roleAuth");

		// 权限列表
		SysPrivilege privilege = new SysPrivilege();
		privilege.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<SysPrivilege> privileges = sysPrivilegeService.list(privilege);
		mv.addObject("privileges", privileges);

		Set<SysPrivilege> sysPrivileges = new HashSet<SysPrivilege>(sysPrivilegeService.findByRoleId(roleId));
		mv.addObject("myPrivileges", sysPrivileges);
		mv.addObject("roleId", roleId);
		return mv;
	}

	/**
	 * 跳转至 分配角色
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "distributionRole")
	public ModelAndView distributionRole(String memberId) {
		ModelAndView mv = new ModelAndView("system/role/distributionRole");
		// 用户已有的角色

		List<SysRole> myRoles = sysRoleService.findByMemberId(memberId);
		mv.addObject("myRoles", myRoles);

		SysRole sysRole = new SysRole();
		sysRole.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<SysRole> sysRoles = sysRoleService.list(sysRole);
		mv.addObject("sysRoles", sysRoles);
		mv.addObject("memberId", memberId);
		return mv;
	}

	/**
	 * 保存分配角色
	 * 
	 * @param memberId
	 * @param roleIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveDistributionRole")
	public AjaxResult saveDistributionRole(String memberId, String roleIds) {
		try {
			if (StringUtils.isNotEmpty(roleIds)) {
				memberSysRoleService.deleteByMemberId(memberId);
				List<String> roleNames = new ArrayList<String>();
				for (String roleId : roleIds.split(",")) {
					SysRole sysRole = sysRoleService.get(roleId);
					roleNames.add(sysRole.getName());
					MemberSysRole memberSysRole = new MemberSysRole(memberId, roleId);
					memberSysRoleService.insert(memberSysRole);
				}

				Member member = memberService.get(memberId);
				String str = roleNames.toString();
				if (str.contains("分销商")) {
					member.setIsDistributor(1);
				} else {
					member.setIsDistributor(0);
				}

				if (str.contains("合作商")) {
					member.setIsPartner(1);
				} else {
					member.setIsPartner(0);
				}

				if (str.contains("管理员")) {
					member.setIsAdmin(1);
				} else {
					member.setIsAdmin(0);
				}

				if (str.contains("达人")) {
					member.setIsExpert(1);
				} else {
					member.setIsExpert(0);
				}
				memberService.update(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(false, "error");
		}
		return new AjaxResult(true, "success");
	}

	/**
	 * 保存 角色分配权限
	 * 
	 * @param roleId
	 * @param privilegeIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveRoleAuth")
	public AjaxResult saveAuth(String roleId, String privilegeIds) {
		try {
			if (StringUtils.isNotEmpty(privilegeIds)) {
				sysRolePrivilegeService.deleteByRoleId(roleId);
				for (String privilegeId : privilegeIds.split(",")) {
					SysRolePrivilege sysRolePrivilege = new SysRolePrivilege(roleId, privilegeId);
					sysRolePrivilegeService.insert(sysRolePrivilege);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResult(false, "error");
		}
		return new AjaxResult(true, "success");
	}


	/**
	 * 查看角色
	 * @param id 角色编号
	 * @return 交互数据
	 */
	@RequestMapping(value = "view")
	public ModelAndView view(String id){
		ModelAndView modelAndView = new ModelAndView("system/role/roleView");
		SysRole sysRole;
		if (Strings.isNullOrEmpty(id)){
			sysRole = new SysRole();
		}
		else {
			sysRole = sysRoleService.get(id);
		}

		Map<Integer, String> types = RoleType.convertMap();
		modelAndView.addObject("types", types);
		modelAndView.addObject("role", sysRole);
		return modelAndView;
	}

	/**
	 * 保存角色
	 * @param sysRole 角色
	 * @return 交互数据
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(SysRole sysRole){
		AjaxResult ajaxResult = new AjaxResult();
		if (Strings.isNullOrEmpty(sysRole.getId())){
			sysRoleService.insert(sysRole);
		}
		else {
			sysRoleService.update(sysRole);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
}
