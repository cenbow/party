package com.party.web.biz.crowdfund;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.*;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderRefundTrade;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.PaymentState;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderRefundTradeService;
import com.party.pay.model.refund.WechatPayRefundResponse;

import com.party.web.web.dto.output.crowdfund.SupportCountOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 支持业务结论
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 16:17
 */

@Service
public class SupportBizService {

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IOrderRefundTradeService orderRefundTradeService;
    
    @Autowired
    private IOrderFormService orderFormService;
    
    @Autowired
    private IProjectService projectService;

    @Autowired
    private ITargetProjectService targetProjectService;

    @Autowired
    private IActivityService activityService;

    /**
     * 根据分销编号查询
     * @param relationId 分销编号
     * @param page 分页参数
     * @return 支持列表
     */
    public List<SupportWithMember> listForDistributorId(String relationId, Page page){
        SupportWithMember supportWithMember = new SupportWithMember();
        supportWithMember.setRelationId(relationId);
        List<SupportWithMember> supportWithMemberList = supportService.distributorList(supportWithMember, page);
        return supportWithMemberList;
    }

    /**
     * 众筹项目查询支持
     * @param projectId 项目比昂
     * @param startTime 开始时间
     * @param page 分页参数
     * @return 支持列表
     */
    public List<SupportWithMember> listForProjectId(String projectId,String startTime, Page page){
        SupportWithMember supportWithMember = new SupportWithMember();
        supportWithMember.setProjectId(projectId);
        supportWithMember.setPayStatus(Constant.IS_SUCCESS);
        supportWithMember.setStartTime(startTime);
        List<SupportWithMember> supportWithMemberList = supportService.listWithMember(supportWithMember, page);
        return supportWithMemberList;
    }
    
    /**
     * 众筹支持退款业务
     * @param support 支持信息
     * @param response 退款信息
     * @param project 众筹信息
     */
    @Transactional
    public void refundSupport(Support support, Project project, WechatPayRefundResponse response){

        //修改订单状态
        OrderForm orderForm = orderFormService.get(support.getOrderId());

        orderForm.setIsPay(PaymentState.NO_PAY.getCode()); // 支付状态更改为未付款
        orderForm.setStatus(OrderStatus.ORDER_STATUS_REFUND.getCode()); // 订单状态更改为已退款
        orderFormService.update(orderForm);

        //持久化流水信息
        OrderRefundTrade orderTrade = new OrderRefundTrade();
        orderTrade.setOrderFormId(orderForm.getId());
        orderTrade.setOrigin(3);
        orderTrade.setTransactionId(response.getTransactionId());
        orderTrade.setType(orderForm.getPaymentWay());
        orderTrade.setData(JSONObject.toJSONString(response));
        orderRefundTradeService.insert(orderTrade);

        //修改业务数据
        support.setPayStatus(Constant.SUPPORT_PAY_STATUS_REFUND);//已经退款
        supportService.update(support);

        project.setFavorerNum(project.getFavorerNum() - 1);//支持人数减
        project.setActualAmount(project.getActualAmount() -  (float)(response.getRefundFee() * 0.01));//实际筹款减
        projectService.update(project);

        TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
        Activity activity = activityService.get(targetProject.getTargetId());
        activity.setFavorerNum(activity.getFavorerNum() - 1);
        activityService.update(activity);
    }

    /**
     * 图表数据
     * @param projectId 项目编号
     * @return 输出视图
     */
    public SupportCountOutput chart(String projectId){

        Project project = projectService.get(projectId);
        Date now = new Date();
        Date startDate = DateUtils.addDay(now, -30);
        List<Date> dateList = DateUtils.getDateDifference(project.getCreateDate(), now);
        if (dateList.size() > 30){
            dateList = DateUtils.getDateDifference(startDate, now);
        }
        List<SupportCount> supportCountList
                = supportService.count(DateUtils.formatDateTime(startDate), DateUtils.formatDate(now), projectId);
        return conformity(dateList, supportCountList);
    }


    /**
     * 整合数据
     * @param dateList 时间列表
     * @param supportCountList 统计列表
     * @return 输出视图
     */
    public SupportCountOutput conformity(List<Date> dateList, List<SupportCount> supportCountList){
        SupportCountOutput supportCountOutput = new SupportCountOutput();
        List<String> dates = Lists.newArrayList();
        List<Float> paymentList = Lists.newArrayList();
        List<Integer> favorerList = Lists.newArrayList();

        for (Date date : dateList){
            Float payment = 0f;
            Integer favorer = 0;
            for (SupportCount supportCount : supportCountList){
                if (DateUtils.formatDate(date, "yyyy-MM-dd").equals(DateUtils.formatDate(supportCount.getDate(), "yyyy-MM-dd"))){
                    payment = supportCount.getPayment();
                    favorer = supportCount.getFavorerNum();
                    break;
                }
            }
            paymentList.add(payment);
            favorerList.add(favorer);
            dates.add(DateUtils.formatDate(date, "yyyy-MM-dd"));
        }

        supportCountOutput.setDateList(dates);
        supportCountOutput.setFavorerList(favorerList);
        supportCountOutput.setPaymentList(paymentList);
        return supportCountOutput;
    }
}
