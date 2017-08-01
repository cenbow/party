package com.party.web.web.interceptor;

import com.party.common.utils.StringUtils;
import com.party.core.model.charge.PackageMember;
import com.party.core.model.charge.PackagePrivilege;
import com.party.web.biz.activity.ActivityBizService;
import com.party.web.biz.charge.PackageBizService;
import com.party.web.utils.RealmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * 收费拦截器
 *
 * @author Administrator
 * @date 2017/7/28 0028 10:19
 */
@Component("chargeInterceptor")
public class ChargeInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private PackageBizService packageBizService;
    @Autowired
    private ActivityBizService activityBizService;

    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    private String publish_activity = "publish_activity";

    /**
     * 处理之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 业务id（判断新增或是编辑）
        String id = request.getParameter("id");
        // 当前用户id
        String memberId = RealmUtils.getCurrentUser().getId();

        // 用户最大的套餐
        PackageMember packageMember = packageBizService.getPackageMember(memberId, null);
        if (packageMember == null) {
            return true;
        }
        // 权限code
        String privilegeCode = request.getParameter("privilegeCode");
        PackagePrivilege packagePrivilege = packageBizService.getPackagePrivilege(packageMember.getLevelId(), privilegeCode);
        if (StringUtils.isEmpty(privilegeCode) || packagePrivilege == null) {
            return true;
        } else if (packagePrivilege.getIsNumber() == -1) {
            return true;
        }

        Integer count = 0;
        if (privilegeCode.equals(publish_activity)) {
            count = activityBizService.getMaxCount(memberId);
        }

        Integer paramValue = Integer.valueOf(packagePrivilege.getParamValue());
        boolean flag = false;
        if (StringUtils.isNotEmpty(id)) {
            if (count > paramValue) {
                flag = true;
            }
        } else {
            if (count >= paramValue) {
                flag = true;
            }
        }

        if (flag) {
            response.setStatus(unauthorizedErrorCode);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            writer.write(unauthorizedErrorMessage);
            writer.close();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
