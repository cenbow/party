package com.party.admin.web.interceptor;

import com.party.admin.utils.RealmUtils;
import com.party.core.model.member.Member;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器
 * party
 * Created by wei.li
 * on 2016/8/30 0030.
 */

@Component(value = "loginInterceptor")
public class LoginInterceptor implements HandlerInterceptor {

    //登陆URL
    private static String LOGIN_URL = "/user/login/home.do";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Session session = RealmUtils.getSession();
        Member member =(Member) session.getAttribute("currentUser");
        //如果没有用户登陆
        if (null == member){
            String contextPath = request.getContextPath();
            response.sendRedirect( contextPath + LOGIN_URL);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
