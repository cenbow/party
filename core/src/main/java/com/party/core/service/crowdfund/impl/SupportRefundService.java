package com.party.core.service.crowdfund.impl;

import com.party.common.constant.Constant;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportRefundService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.order.IOrderRefundService;
import com.party.pay.model.refund.WechatPayRefundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支持退款实现
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 11:47
 */

@Service
public class SupportRefundService implements ISupportRefundService {

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ITargetProjectService targetProjectService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IOrderRefundService orderRefundService;

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private IDistributorCountService distributorCountService;

    protected static Logger logger = LoggerFactory.getLogger(SupportRefundService.class);
    /**
     * 众筹支持退款
     * @param support 支持信息
     * @param refundResponse 退款报文
     */
    @Override
    @Transactional
    public void refund(Support support, WechatPayRefundResponse refundResponse) {
        //订单退款
        orderRefundService.refund(support.getOrderId(), refundResponse);
        //修改业务数据
        support.setPayStatus(Constant.SUPPORT_PAY_STATUS_REFUND);//已经退款
        supportService.update(support);

        Project project = projectService.get(support.getProjectId());
        if (project != null) {
        	project.setFavorerNum(project.getFavorerNum() - 1);//支持人数减

            float num = supportService.sumByProjectId(project.getId());
            project.setRealTimeAmount(num);
            num = num < 0 ? 0 : num;//不能小于0

            //不大于目标金额
            if (num >= project.getTargetAmount()){
                num = project.getTargetAmount();
            }
            else {
                //众筹中
                project.setIsSuccess(Constant.IS_CROWFUND_ING);
            }
        	project.setActualAmount(num);//实际筹款
        	projectService.update(project);

        	TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
        	Activity activity = activityService.get(targetProject.getTargetId());
        	activity.setFavorerNum(activity.getFavorerNum() - 1);
        	activityService.update(activity);

            //分销统计更新
            try {
                DistributorDetail distributorDetail = distributorDetailService.findByTargetId(support.getOrderId());
                if (null != distributorDetail){
                    DistributorCount distributorCount = distributorCountService.findByRelationId(distributorDetail.getDistributorRelationId());
                    distributorCount.setFavorerNum(distributorCount.getFavorerNum() - 1);

                    float amount = BigDecimalUtils.sub(distributorCount.getFavorerAmount(), (float)(refundResponse.getRefundFee() * 0.01));
                    amount = BigDecimalUtils.round(amount, 2);
                    distributorCount.setFavorerAmount(amount);
                    distributorCountService.update(distributorCount);
                }
            } catch (Exception e) {
                logger.error("退款分销异常", e);
            }
        }
    }
}
