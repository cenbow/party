package com.party.mobile.web.interceptor;

import com.google.common.base.Strings;
import com.party.core.model.count.DataCount;
import com.party.core.service.count.IDataCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统计数据拦截器
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 20:00
 */
@Component(value = "dataCountInterceptor")
public class DataCountInterceptor extends HandlerInterceptorAdapter {

    private static String TARGET_ID = "id";


    @Autowired
    private IDataCountService dataCountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String id = request.getParameter(TARGET_ID);
        if (!Strings.isNullOrEmpty(id)){
            DataCount dataCount = dataCountService.findByTargetId(id);
            dataCount.setViewNum(dataCount.getViewNum() + 1);
            dataCountService.update(dataCount);
        }
        return true;
    }
}
