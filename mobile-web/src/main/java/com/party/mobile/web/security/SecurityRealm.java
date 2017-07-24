package com.party.mobile.web.security;

import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 安全数据源realm
 * Created by wei.li
 *
 * @date 2017/1/3 0003
 * @time 14:14
 */
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    protected static Logger logger = LoggerFactory.getLogger(SecurityRealm.class);

    @Autowired
    private IMemberService memberService;

    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获取基于用户名和密码的令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为{}", ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        Member member = memberService.get(token.getUsername());
        if (null != member){
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(member.getId(), member.getPassword(), getName());
            this.setSession("currentUser", member);
            return authenticationInfo;
        }
        return null;
    }


    /**
     * 设置session内容
     * @param key 键
     * @param value 值
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
