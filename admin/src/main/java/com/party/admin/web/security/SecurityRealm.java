package com.party.admin.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.party.core.model.member.Member;
import com.party.core.model.system.SysPrivilege;
import com.party.core.model.system.SysRole;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.menu.IMenuService;
import com.party.core.service.system.ISysPrivilegeService;
import com.party.core.service.system.ISysRoleService;

/**
 * 安全数据源realm
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    @Autowired
    private MemberService memberService;
	@Autowired
	private ISysPrivilegeService sysPrivilegeService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private IMenuService menuService;

    protected static Logger logger = LoggerFactory.getLogger(SecurityRealm.class);

    private static String ADMMIN_ROLE_CODE = "admin";

    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }

    /**
     * 获取当前用户的权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	String loginName = (String) getAvailablePrincipal(principals);
		Member member = memberService.get(loginName);
		if (member != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户角色
			List<SysRole> sysRoles = sysRoleService.findByMemberId(member.getId());
			List<String> roleNames = new ArrayList<String>();
			for (SysRole sysRole : sysRoles) {
				roleNames.add(sysRole.getCode());
			}
			info.addRoles(roleNames);
			
			// 用户权限
			List<SysPrivilege> sysPrivileges = new ArrayList<SysPrivilege>();
			for (SysRole sysRole : sysRoles) {
				sysPrivileges.addAll(sysPrivilegeService.findByRoleId(sysRole.getId()));
			}

			List<String> permissions = new ArrayList<String>();
			for (SysPrivilege privilege : sysPrivileges) {
				permissions.add(privilege.getCode());
			}

			info.addStringPermissions(permissions);
			return info;
		}
        return null;
    }

    /**
     * 验证当前登录的Subject
     * @param authenticationToken 登陆凭证
     * @return 授权信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为{}", ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        Member member = memberService.findByLoginName(token.getUsername());
        if (null != member){
            boolean hasAdmin = memberService.hasRole(member.getId(), ADMMIN_ROLE_CODE);
            if (hasAdmin){
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(member.getId(), member.getPassword(), getName());
                this.setSession("currentUser", member);
                return authcInfo;
            }
        }
        return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }


}
