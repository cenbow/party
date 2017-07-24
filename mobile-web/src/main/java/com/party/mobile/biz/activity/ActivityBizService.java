package com.party.mobile.biz.activity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.AuditStatus;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.city.City;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.member.WithBuyer;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.city.ICityService;
import com.party.core.service.counterfoil.ICounterfoilService;
import com.party.core.service.counterfoil.biz.CounterfoilBizService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.impl.OrderFormService;
import com.party.core.service.user.IUserService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.member.CreateByBizService;
import com.party.mobile.web.dto.activity.input.ListInput;
import com.party.mobile.web.dto.activity.output.ActivityOutput;
import com.party.mobile.web.dto.activity.output.BuyerOutput;
import com.party.mobile.web.dto.activity.output.ListOutput;
import com.party.mobile.web.dto.activity.output.MemberActOutput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;

/**
 * 活动业务逻辑接口
 * party
 * Created by wei.li
 * on 2016/9/27 0027.
 */

@Service
public class ActivityBizService {

    @Autowired
    ICityService cityService;

    @Autowired
    IUserService userService;

    @Autowired
    IMemberService memberService;

    @Autowired
    IActivityDetailService activityDetailService;

    @Autowired
    CreateByBizService createByBizService;

    @Autowired
    IActivityService activityService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
	IMemberActService memberActService;

    @Autowired
    OrderFormService orderFormService;

    @Autowired
    IDistributorRelationService distributorRelationService;

    @Autowired
    DistributorBizService distributorBizService;

    @Autowired
    IDistributorTargetAttacheService distributorTargetAttacheService;
	@Autowired
	ICounterfoilService counterfoilService;
	@Autowired
	CounterfoilBizService counterfoilBizService;
	@Autowired
	private OrderActivityBizService orderActivityBizService;

    /**
     * 根据活动获取活动输出视图
     * @param activity 活动实体
     * @return 活动输出视图
     */
    public ActivityOutput get(Activity activity, HttpServletRequest request){

        ActivityOutput activityOutput = ActivityOutput.transform(activity);

        //是否登陆
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser){
            MemberAct dbMemberAct = memberActService.findByMemberAct(currentUser.getId(), activity.getId());

            Member member = memberService.get(currentUser.getId());
            //如果不是自己的产品
            if (!member.getId().equals(activity.getMember())){
                activityOutput.setIsDistributor(member.getIsDistributor());
            }
            else {
                activityOutput.setIsDistributor(Constant.BAN_DISTRIBUTION);
            }

            //如果未报名
            if (null == dbMemberAct){
                activityOutput.setActStatus(ActStatus.ACT_STATUS_NO_JOIN.getCode());
            }
            else {
                //设置订单编号
                OrderForm orderForm = orderFormService.get(dbMemberAct.getOrderId());
                if(null != orderForm) {
                    activityOutput.setOrderId(orderForm.getId());
                }

                //设置活动报名状态及id
                activityOutput.setActStatus(dbMemberAct.getCheckStatus());
                activityOutput.setMeberActId(dbMemberAct.getId());
            }

        }
        else
        {
            activityOutput.setIsDistributor(YesNoStatus.NO.getCode());//默认没有开分销权限
            activityOutput.setActStatus(ActStatus.ACT_STATUS_NO_JOIN.getCode());//如果未登录
        }

        //查询城市
        City city = cityService.get(activity.getCityId());
        activityOutput.setCityName(city.getName());

        //活动详情
        ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
        if (null != activityDetail && StringUtils.isNotEmpty(activityDetail.getContent())) {
        	String content = StringUtils.stringtohtml(activityDetail.getContent());
			activityOutput.setContent(content);
		}

        //获取创建者信息
        activityOutput = createByBizService.getCreateBy(activityOutput, activity.getCreateBy(), activity.getMember());

        //活动状态：是否正在进行
        activityOutput.setIsInProgress(setIsInProgress(activityOutput.getEndTime()));

        //分享链接
        activityOutput.setShareLink(setShareLink(activity.getId()));

        //如果不隐藏报名者
        if (activity.getJoinHidden().equals(YesNoStatus.NO.getCode())){
            WithBuyer withBuyer = new WithBuyer();
            withBuyer.setActId(activity.getId());
            Page page = new Page(1, 20);
            List<WithBuyer> withBuyerList = memberActService.withBuyerList(withBuyer, page);
            List<BuyerOutput> buyerOutputList = LangUtils.transform(withBuyerList, new Function<WithBuyer, BuyerOutput>() {
                @Nullable
                @Override
                public BuyerOutput apply(@Nullable WithBuyer withBuyer) {
                    return BuyerOutput.transform(withBuyer);
                }
            });
            activityOutput.setBuyerOutputList(buyerOutputList);
            activityOutput.setBuyerNum(page.getTotalCount());
        }

		List<Counterfoil> counterfoils = counterfoilBizService.getActivityCounterfoils(activity.getId());
		Activity dbActivity = activityService.get(activity.getId());
		if (counterfoils.size() > 0) {
			String newPrice = counterfoilBizService.getShowPrice(counterfoils, activity.getId());
			activityOutput.setNewPrice(newPrice);
			activityOutput.setCounterfoils(counterfoils);
		} else {
			// 活动的有效订单量
			dbActivity = orderActivityBizService.updateActivityInfo(activity.getId());
		}
		activityOutput.setLimitNum(dbActivity.getLimitNum());
		activityOutput.setJoinNum(dbActivity.getJoinNum());
		return activityOutput;
	}


    /**
     * 获取分销活动详情
     * @param activity 活动
     * @param input 输入参数
     * @param request 请求参数
     * @return 输出视图
     */
    public ActivityOutput getDistributionDetails(Activity activity, GetDistributorInput input, HttpServletRequest request){
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        ActivityOutput activityOutput = this.get(activity, request);

        //如果是活动发布者
        if (input.getParentId().equals(Constant.BUILDER_PARENT)){
            Member member = memberService.get(activityOutput.getMember());
            activityOutput.setDistributorName(member.getRealname());
            activityOutput.setDistributorLogo(member.getLogo());
            activityOutput.setDistributorTime(activityOutput.getCreateDate());

            activityOutput.setDeclaration(Constant.BUILDER_DECLARATION);

            if (null != currentUser){
                //如果是自己的产品
                if (currentUser.getId().equals(activity.getMember())){
                    activityOutput.setIsDistributor(Constant.BAN_DISTRIBUTION);
                    activityOutput.setMyDistribution(true);
                }
                else {
                    activityOutput.setIsDistributor(Constant.ALLOW_DISTRIBUTION);
                }
            }
            else {
                activityOutput.setIsDistributor(Constant.ALLOW_DISTRIBUTION);
            }
        }
        else {
            DistributorRelation distributorRelation
                    = distributorRelationService.get(input.getType(), input.getTargetId(),input.getDistributorId());

            if (null == distributorRelation){
                throw new BusinessException("分销关系不存在");
            }

            //分销过不允许分销
            activityOutput.setIsDistributor(Constant.BAN_DISTRIBUTION);

            //分销者
            Member distributor = memberService.get(distributorRelation.getDistributorId());
            activityOutput.setDistributorName(distributor.getRealname());
            activityOutput.setDistributorLogo(distributor.getLogo());
            activityOutput.setDistributorTime(distributorRelation.getCreateDate());

            //分销附属信息
            DistributorTargetAttache distributorTargetAttache
                    = distributorTargetAttacheService.findByRelationId(distributorRelation.getId());
            if (null != distributorTargetAttache){
                activityOutput.setStyle(distributorTargetAttache.getStyle());
                activityOutput.setDeclaration(distributorTargetAttache.getContent());
            }

            if (null != currentUser){
                //是否是我的分销
                if (currentUser.getId().equals(distributorRelation.getDistributorId())){
                    activityOutput.setMyDistribution(true);
                }
            }
			List<Counterfoil> counterfoils = counterfoilBizService.getActivityCounterfoils(activity.getId());
			Activity dbActivity = activityService.get(activity.getId());
			if (counterfoils.size() > 0) {
				String newPrice = counterfoilBizService.getShowPrice(counterfoils, activity.getId());
				activityOutput.setNewPrice(newPrice);
				activityOutput.setCounterfoils(counterfoils);
    		} else {
    			// 活动的有效订单量
    			dbActivity = orderActivityBizService.updateActivityInfo(activity.getId());
    		}
    		activityOutput.setLimitNum(dbActivity.getLimitNum());
    		activityOutput.setJoinNum(dbActivity.getJoinNum());
        }


       return activityOutput;
    }




    /**
     * 分页查询所有活动
     * @param listInput 查询参数
     * @param page 分页参数
     * @return 活动输出视图列表
     */
    public List<ListOutput> list(ListInput listInput, Page page){
        Activity activity = new Activity();
        activity.setCityId(listInput.getCityId());
        activity.setShowFront(1);
        activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        activity.setCheckStatus(AuditStatus.PASS.getCode());//审核通过
        activity.setIsCrowdfunded(Constant.NOT_CROWDFUNDED); // 非众筹项目
        List<Activity> activityList = activityService.listPage(activity, page);

        if (!CollectionUtils.isEmpty(activityList)){
            List<ListOutput> activityOutputList = LangUtils.transform(activityList, input -> {
                ListOutput listOutput = ListOutput.transform(input);
                //活动状态：是否正在进行
                listOutput.setIsInProgress(setIsInProgress(listOutput.getEndTime()));
                //设置分享链接
                listOutput.setShareLink(setShareLink(listOutput.getId()));

                return listOutput;
            });
            return activityOutputList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 设置活动状态，是否进行中
     * @param endtime
     * @return
     */
    public Integer setIsInProgress(Date endtime)
    {
        //设置活动状态，是否进行中
        if (endtime.getTime() > new Date().getTime())
        {
            return 1;//正在进行
        }

        return 0;//已截至
    }

    /**
     * 设置活动分享链接
     * @param actId 活动id
     * @return
     */
    public String setShareLink(String actId)
    {
        return "http://3g.tongxingzhe.cn/micWeb/html/hd/hd_detail.html?hdId=" + actId;
    }

    /**
     * 我发布的活动列表
     * @param page
     * @param request
     * @return
     */
	public List<ActivityOutput> publishList(Page page, HttpServletRequest request, Activity activity) {
	    Map<String,Object> params = Maps.newHashMap();
        params.put("sortType","startTime");
        List<Activity> activityList = activityService.listPage(activity,params, page);
		if (!CollectionUtils.isEmpty(activityList)){
            List<ActivityOutput> activityOutputList = LangUtils.transform(activityList, input -> {
            	ActivityOutput output = ActivityOutput.transform(input);
				if (input.getStartTime().getTime() > new Date().getTime()) {
					output.setIsInProgress(0); // 0表示未開始
				} else if (input.getEndTime().getTime() < new Date().getTime()) {
					output.setIsInProgress(1); // 已截至
				}
				// 活动的有效订单量
				int orderCount = orderFormService.calculateBuyNum(input.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
				output.setJoinNum(orderCount);
                return output;
            });
            return activityOutputList;
        }
        return Collections.EMPTY_LIST;
	}

	/**
	 * 活动报名列表
	 * @param page
	 * @param hdId
	 * @return
	 */
	public List<MemberActOutput> actMemberList(Page page, String hdId) {
		MemberAct memberAct = new MemberAct();
		memberAct.setActId(hdId);
		memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<MemberAct> memberActs = memberActService.listPage(memberAct, page);
		if (!CollectionUtils.isEmpty(memberActs)) {
			List<MemberActOutput> memberOutputs = LangUtils.transform(memberActs, input -> {
				MemberActOutput memberOutput = MemberActOutput.transform(input);
				Member member = memberService.get(input.getMemberId());
				memberOutput.setLogo(member.getLogo());
				return memberOutput;
			});
			return memberOutputs;
		}
		return Collections.EMPTY_LIST;
	}


}
