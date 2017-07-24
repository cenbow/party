package com.party.mobile.web.interceptor;

import com.google.common.base.Strings;
import com.party.core.model.YesNoStatus;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.member.Member;
import com.party.core.model.store.StoreGoods;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.store.IStoreGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * 分享页面拦截
 * party
 * Created by wei.li
 * on 2016/10/13 0013.
 */
@Component(value = "shareInterceptor")
public class ShareInterceptor extends HandlerInterceptorAdapter {

    private static final String DITRIBUTOR_TYPE = "type";

    private static final String DISTRIBUTOR_TARGET_ID = "targetId";

    private static final String DISTRIBUTOR_PARENT_ID = "parentId";

    private static final String DISTRIBUTOR_DISTRIBUTOR_ID = "distributorId";
    @Autowired
    private IDistributorRelationService distributorRelationService;

    @Autowired
    private IDistributorCountService distributorCountService;


    /**
     * 处理之前进行调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //String methodName = ((HandlerMethod) handler).getMethod().getName();
        String type = request.getParameter(DITRIBUTOR_TYPE);
        String targetId = request.getParameter(DISTRIBUTOR_TARGET_ID);
        String parentId = request.getParameter(DISTRIBUTOR_PARENT_ID);
        String distributorId = request.getParameter(DISTRIBUTOR_DISTRIBUTOR_ID);
        //是分销连接
        if (null != type && !Strings.isNullOrEmpty(targetId) && !Strings.isNullOrEmpty(parentId) && !Strings.isNullOrEmpty(distributorId)){
            DistributorRelation distributorRelation = distributorRelationService.get(Integer.valueOf(type), targetId, distributorId);
            if (null != distributorRelation){
                DistributorCount distributorCount = distributorCountService.findByRelationId(distributorRelation.getId());
                distributorCount.setViewNum(distributorCount.getViewNum() + 1);//阅读量加
                distributorCountService.update(distributorCount);
            }
        }
        return true;
    }

}
