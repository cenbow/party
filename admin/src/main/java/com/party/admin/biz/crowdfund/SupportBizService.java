package com.party.admin.biz.crowdfund;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.party.admin.biz.refund.RefundBizService;
import com.party.admin.jms.publisher.service.ISupportRefundPubulisherService;
import com.party.admin.web.dto.output.crowdfund.SupportCountOutput;
import com.party.common.utils.DateUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.crowdfund.*;
import com.party.core.service.crowdfund.*;
import com.party.core.service.order.IOrderRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderRefundTrade;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.PaymentState;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderRefundTradeService;
import com.party.pay.model.refund.WechatPayRefundResponse;

/**
 * 众筹支持业务接口
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 16:44
 */
@Service
public class SupportBizService {

    @Autowired
    private ISupportService supportService;

    @Autowired
    private RefundBizService refundBizService;

    @Autowired
    private ISupportRefundService supportRefundService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IProjectRefundService projectRefundService;

    @Autowired
    private IOrderRefundService orderRefundService;

    @Autowired
    private ISupportRefundPubulisherService supportRefundPubulisherService;

    @Autowired
    private IOrderFormService orderFormService;

    protected static Logger logger = LoggerFactory.getLogger(ProjectBizService.class);

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
     * @param page 分页参数
     * @return 支持列表
     */
    public List<SupportWithMember> listForProjectId(String projectId, String startTime, Page page){
        SupportWithMember supportWithMember = new SupportWithMember();
        supportWithMember.setProjectId(projectId);
        supportWithMember.setExcludePayStatus(Constant.SUPPORT_PAY_STATUS_UNPAID);
        supportWithMember.setStartTime(startTime);
        List<SupportWithMember> supportWithMemberList = supportService.listWithMember(supportWithMember, page);
        return supportWithMemberList;
    }

    /**
     * 支持退款信息
     * @param id 支持编号
     */
    public void refund(String id){
        Support support = supportService.get(id);

        //验证是否已经退款
        OrderForm orderForm = orderFormService.get(support.getOrderId());
        if (null != orderForm && orderForm.getStatus().equals(Constant.SUPPORT_PAY_STATUS_REFUND)){
            return;
        }

        WechatPayRefundResponse response = null;
        try {
            response = (WechatPayRefundResponse) refundBizService.refund(support.getOrderId());
        } catch (Exception e) {
            //退款失败
            support.setPayStatus(Constant.SUPPORT_PAY_STATUS_FAIL);
            supportService.update(support);
            logger.info("众筹支持订单退款异常", e);
        }

        if (null == response){
            //退款失败
            support.setPayStatus(Constant.SUPPORT_PAY_STATUS_FAIL);
            supportService.update(support);
            return;
        }

        //退款成功
        if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())){
            supportRefundService.refund(support, response);

            //众筹是否全部退完
            Project project = projectService.get(support.getProjectId());
            boolean refundAll = supportService.isRefundAll(project.getId());
            if (refundAll){
                projectRefundService.refund(project);
            }
        }
        //退款失败
        else {

            //如果订单已经退款
            if (Constant.REFUND_ALL_DES.equals(response.getErrCodeDes())){
                supportRefundService.refund(support, response);
                //众筹是否全部退完
                Project project = projectService.get(support.getProjectId());
                boolean refundAll = supportService.isRefundAll(project.getId());
                if (refundAll){
                    projectRefundService.refund(project);
                }
            }
            else {
                //退款失败
                support.setPayStatus(Constant.SUPPORT_PAY_STATUS_FAIL);
                supportService.update(support);
                orderRefundService.orderTrade(support.getOrderId(), response);
            }
        }
    }


    /**
     * 退款队列
     * @param id 支持编号
     */
    public void sendRefund(String id){
        Support support = supportService.get(id);
        if (null == support){
            throw new BusinessException("支持信息不存在");
        }
        support.setPayStatus(Constant.SUPPORT_PAY_STATUS_REFUNDING);
        supportService.update(support);
        supportRefundPubulisherService.send(id);
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
