package com.party.mobile.biz.crowdfund;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.utils.DateUtils;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.activity.CrowfundResources;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.distributor.DistributorDetailType;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.DistributorRelationType;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.ICrowfundResourcesService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.web.dto.crowdfund.input.ApplyInput;
import com.party.mobile.web.dto.crowdfund.input.StatisticsInput;
import com.party.mobile.web.dto.crowdfund.output.ActivityDetailOutput;
import com.party.mobile.web.dto.distributor.input.ApplyDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.order.Output.OrderOutput;
import com.party.mobile.web.dto.order.input.BookOrderInput;
import com.party.common.utils.PartyCode;

import com.party.notify.notifyPush.servce.INotifySendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 众筹活动业务接口
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 17:57
 */

@Service
public class CrowdfundActivityBizService {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IActivityDetailService activityDetailService;

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private ITargetProjectService targetProjectService;

    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private ICrowfundResourcesService crowfundResourcesService;

    @Autowired
    private DistributorBizService distributorBizService;

    @Autowired
    private IDistributorRelationService distributorRelationService;

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private INotifySendService notifySendService;

    protected static Logger logger = LoggerFactory.getLogger(CrowdfundActivityBizService.class);

    /**
     * 获取活动详情
     * @param id 编号
     * @param currentUser 当前用户
     * @return 活动详情
     */
    public ActivityDetailOutput detail(String id, CurrentUser currentUser){
        Activity activity = activityService.get(id);/**/
        if (null == activity){
            throw new BusinessException("该众筹项目不存在");
        }
        ActivityDetailOutput activityDetailOutput = ActivityDetailOutput.transform(activity);
        
        int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
        activityDetailOutput.setJoinNum(joinNum);

        //是否已经过期
        Integer result = DateUtils.compareDate(new Date(), activity.getEndTime());
        if (result == 1){
            activityDetailOutput.setClosed(true);
        }

        //创建者
        Member member = memberService.get(activity.getMember());
        if (!Strings.isNullOrEmpty(activity.getPublisher()) && !Strings.isNullOrEmpty(activity.getPublisher())){
            activityDetailOutput.setAuthorName(activity.getPublisher());
            activityDetailOutput.setAuthorLogo(activity.getPublisherLogo());
        }
        else {
            activityDetailOutput.setAuthorName(member.getRealname());
            activityDetailOutput.setAuthorLogo(member.getLogo());
        }
        activityDetailOutput.setAuthorId(activity.getMember());
        activityDetailOutput.setTemplateStyle(activity.getTemplateStyle());

        //详情
        ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
        if (null != activityDetail){
            activityDetailOutput.setContent(StringUtils.stringtohtml(activityDetail.getContent()));
            activityDetailOutput.setMatchStandard(StringUtils.stringtohtml(activityDetail.getMatchStandard()));
            activityDetailOutput.setApplyRelated(StringUtils.stringtohtml(activityDetail.getApplyRelated()));
        }

        // 跑马灯
        List<CrowfundResources> picList = crowfundResourcesService.findByRefId(activity.getId(), "1");
        activityDetailOutput.setPicList(picList);
        
        // 视频
        List<CrowfundResources> videoList = crowfundResourcesService.findByRefId(activity.getId(), "2");
        if (videoList.size() > 0) {
			activityDetailOutput.setVideo(videoList.get(0));
		}

        if (null != currentUser){
            //是否报名过
            MemberAct memberAct = memberActService.findByMemberAct(currentUser.getId(), id);
            if (memberAct != null){
                TargetProject targetProject = targetProjectService.findByOrderId(memberAct.getOrderId());
                Project project = projectService.get(targetProject.getProjectId());
                activityDetailOutput.setMyProjectId(project.getId());
            }

            //是否已经众筹
            DistributorRelation distributorRelation
                    = distributorRelationService.get(GoodsType.GOODS_CROWD_FUND.getCode(), id ,currentUser.getId());
            if (null != distributorRelation){
                activityDetailOutput.setParentId(distributorRelation.getParentId());
                activityDetailOutput.setDistributorId(distributorRelation.getDistributorId());
            }
        }

        //如果没有绑定事件
        if (!Strings.isNullOrEmpty(activity.getEventId())){
            Activity count = activityService.countForEventId(activity.getEventId());
            activityDetailOutput.setJoinNum(count.getJoinNum());
            activityDetailOutput.setFavorerNum(count.getFavorerNum());
            activityDetailOutput.setBeCrowdfundNum(count.getBeCrowdfundNum());
            activityDetailOutput.setCrowdfundedNum(count.getCrowdfundedNum());
        }
        return activityDetailOutput;
    }

    /**
     * 活动报名
     * @param applyInput 输入视图
     * @param memberId 会员编号
     * @return 报名信息
     */
    @Transactional
    public HashMap<String, String> apply(ApplyInput applyInput, String memberId){
        HashMap<String, String> map = Maps.newHashMap();
        MemberAct dbMemberAct = memberActService.findByMemberAct(memberId, applyInput.getId());
        Activity activity = activityService.get(applyInput.getId()); // 对应活动
        MemberAct memberAct;
        //修改报名
        if (null != dbMemberAct){
            // 已经报名,取出报名信息
            if (ActStatus.ACT_STATUS_AUDITING.getCode() == dbMemberAct.getCheckStatus()
                    || ActStatus.ACT_STATUS_AUDIT_PASS.getCode() == dbMemberAct.getCheckStatus()
                    || ActStatus.ACT_STATUS_PAID.getCode() == dbMemberAct.getCheckStatus()) {
                //查找众筹项目
                TargetProject targetProject = targetProjectService.findByOrderId(dbMemberAct.getOrderId());
                Project project = projectService.get(targetProject.getProjectId());
                map.put("id", project.getId());
                return map;
            }

            this.updateOrder(applyInput, activity.getPrice(), dbMemberAct.getOrderId() );
            memberAct = this.updateMemberAct(applyInput, activity.getPrice(), dbMemberAct);
        }
        //新增报名
        else {
            String orderId = this.saveOrder(applyInput, memberId);
            memberAct = this.saveMemberAct(applyInput, memberId, orderId);
        }

        //调用生成众筹
        String id = projectBizService.insert(memberAct, memberId, applyInput);

        //修改活动
        activity.setBeCrowdfundNum(activity.getBeCrowdfundNum() + 1);
        activity.setJoinNum(activity.getJoinNum() + 1);
        activityService.update(activity);

        //分销统计
        DistributorRelation distributorRelation
                = distributorRelationService.get(GoodsType.GOODS_CROWD_FUND.getCode(),
                applyInput.getDistributorTargetId(),applyInput.getDistributorId());
        //如果存在分销关系
        if (null != distributorRelation){
            StatisticsInput statisticsInput = new StatisticsInput();
            statisticsInput.setApplyId(memberAct.getId());
            statisticsInput.setOrderId(memberAct.getOrderId());
            statisticsInput.setProjectId(id);
            statisticsInput.setDistributorId(applyInput.getDistributorId());
            statisticsInput.setDistributorParentId(applyInput.getDistributorParentId());
            statisticsInput.setDistributorTargetId(applyInput.getDistributorTargetId());
            this.statistics(statisticsInput);
        }
        else {
            //来源于哪个众筹
            if (!Strings.isNullOrEmpty(applyInput.getSourceProjectId())){
                Project project = projectService.get(applyInput.getSourceProjectId());
                DistributorDetail distributorDetail = distributorDetailService.findByTargetId(project.getId());
                if (null != distributorDetail){
                    //生成众筹详情
                    this.statistics(distributorDetail.getDistributorRelationId(), memberAct.getId(), memberAct.getOrderId(), id);
                }
            }

        }

        try {
            this.send(id, memberId);
        } catch (Exception e) {
            logger.error("消息推送异常", e.getMessage());
        }
        map.put("orderId", memberAct.getOrderId());
        map.put("id", id);
        map.put("memberActId", memberAct.getId());
        return map;
    }

    /**
     * 发送众筹成功消息
     * @param projectId 项目编号
     * @param memberId 会员编号
     */
    public void send(String projectId, String memberId){
        Project project = projectService.get(projectId);
        Member member = memberService.get(memberId);
        //发送众筹成功消息
        notifySendService.sendLaunchProject(project, member);
    }


    /**
     * 更新订单
     * @param applyInput 输入视图
     * @param price 价格
     * @param orderId 订单号
     */
    public void updateOrder(ApplyInput applyInput, float price, String orderId){
        OrderForm orderForm = orderFormService.get(orderId);
        orderForm.setLinkman(applyInput.getRealname());// 联系人
        orderForm.setPhone(applyInput.getMobile());// 手机
        float payment = price * orderForm.getUnit(); // 金额
        orderForm.setPayment(payment);
        orderForm.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()); // 待支付
        orderFormService.update(orderForm);// 更新订单信息
    }

    /**
     * 更新报名信息
     * @param applyInput 输入视图
     * @param price 价格
     * @param memberAct 报名信息
     */
    public MemberAct updateMemberAct(ApplyInput applyInput, float price, MemberAct memberAct){
        memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode()); // 待支付
        memberAct.setJoinin(YesNoStatus.YES.getCode());
        memberAct.setName(applyInput.getRealname()); // 联系人
        memberAct.setMobile(applyInput.getMobile()); // 手机
        memberAct.setCompany(applyInput.getCompany()); // 公司名称
        memberAct.setJobTitle(applyInput.getTitle()); // 公司职位
        memberAct.setPayment(price); // 活动费用
        memberActService.update(memberAct);// 更新报名信息
        return memberAct;
    }

    /**
     * 活动订单下单
     * @param input 输入视图
     * @param memberId 会员编号
     * @return 订单编号
     */
    public String saveOrder(ApplyInput input, String memberId){
        BookOrderInput bookOrderInput = new BookOrderInput();
        bookOrderInput.setType(OrderType.ORDER_ACTIVITY.getCode());
        bookOrderInput.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());

        bookOrderInput.setGoodsId(input.getId());
        bookOrderInput.setUnit(1);
        bookOrderInput.setLinkman(input.getRealname());
        bookOrderInput.setPhone(input.getMobile());

        OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
        return orderOutput.getId();
    }


    /**
     * 保存报名信息
     * @param input
     * @param memberId
     * @return
     */
    public MemberAct saveMemberAct(ApplyInput input, String memberId, String orderId){
        MemberAct memberAct = new MemberAct();
        memberAct.setActId(input.getId());
        memberAct.setMemberId(memberId);
        memberAct.setName(input.getRealname());
        memberAct.setMobile(input.getMobile());
        memberAct.setCompany(input.getCompany());
        memberAct.setJobTitle(input.getTitle());

        Activity activity = activityService.get(input.getId());
        //报名验证
        BusinessException businessException = this.verifyApply(activity);
        if (null != businessException){
            throw businessException;
        }
        memberAct.setPayment(activity.getPrice());
        memberAct.setUpdateBy(memberId);
        memberAct.setJoinin(YesNoStatus.YES.getCode());//报名
        memberAct.setCollect(YesNoStatus.YES.getCode());
        memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode());
        //将订单Id放入报名表
        memberAct.setOrderId(orderId);
        BaseModel.preByInfo(memberId, memberAct);
        memberActService.insert(memberAct);
        return memberAct;
    }


    /**
     * 活动报名验证
     * @param activity 活动信息
     * @return 数据异常
     */
    public BusinessException verifyApply(Activity activity){

        //验证报名结束时间
        boolean isVerifyDate = DateUtils.afterTodayDate(activity.getEndTime());
        if (!isVerifyDate){
            return new BusinessException(PartyCode.JOIN_ACT_ENDDATE_ERROR, "报名失败，报名时间晚于报名结束时间");
        }

        //验证人数上限
        Integer applyNum = memberActService.findMemberNum(activity.getId()).size();
        if (applyNum >= activity.getLimitNum()){
            return new BusinessException(PartyCode.JOIN_ACT_LIMITNUM_ERROR, "报名失败，报名人数上限已满");
        }
        return null;
    }


    /**
     * 统计发起众筹详情
     * @param input 输入视图
     */
    public void statistics(StatisticsInput input){

        ApplyDistributorInput applyDistributorInput = new ApplyDistributorInput();
        applyDistributorInput.setDistributorId(input.getDistributorId());
        applyDistributorInput.setDistributorParentId(input.getDistributorParentId());
        applyDistributorInput.setDistributorType(GoodsType.GOODS_CROWD_FUND.getCode());
        applyDistributorInput.setDistributorTargetId(input.getDistributorTargetId());

        //统计报名
        applyDistributorInput.setTargetId(input.getApplyId());
        applyDistributorInput.setType(DistributorDetailType.DISTRIBUTOR_TYPE_APPLY.getCode());
        distributorBizService.setDistributorData(applyDistributorInput);

        //统计订单
        applyDistributorInput.setTargetId(input.getOrderId());
        applyDistributorInput.setType(DistributorDetailType.DISTRIBUTOR_TYPE_ORDER.getCode());
        distributorBizService.setDistributorData(applyDistributorInput);

        //统计众筹
        applyDistributorInput.setTargetId(input.getProjectId());
        applyDistributorInput.setType(DistributorDetailType.DISTRIBUTOR_TYPE_CROWDFUN.getCode());
        distributorBizService.setDistributorData(applyDistributorInput);
    }

    /**
     * 统计发起众筹详情
     * @param distributorRelationId 分销关系编号
     * @param applyId 报名编号
     * @param orderId 订单编号
     * @param projectId 众筹编号
     */
    public void statistics(String distributorRelationId, String applyId, String orderId, String projectId){
        //统计报名
        distributorBizService.setDistributorData(distributorRelationId, applyId, DistributorDetailType.DISTRIBUTOR_TYPE_APPLY.getCode());

        //统计订单
        distributorBizService.setDistributorData(distributorRelationId, orderId, DistributorDetailType.DISTRIBUTOR_TYPE_ORDER.getCode());

        //统计众筹
        distributorBizService.setDistributorData(distributorRelationId, projectId, DistributorDetailType.DISTRIBUTOR_TYPE_CROWDFUN.getCode());
    }

}
