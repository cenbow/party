package com.party.mobile.web.controller.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.redis.StringJedis;
import com.party.common.utils.LangUtils;
import com.party.common.utils.PartyCode;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.member.WithBuyer;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.city.ICityService;
import com.party.core.service.counterfoil.ICounterfoilService;
import com.party.core.service.counterfoil.biz.CounterfoilBizService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.message.IMessageService;
import com.party.core.service.message.IMessageSetService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.user.IUserService;
import com.party.mobile.biz.activity.ActivityBizService;
import com.party.mobile.biz.activity.MemberActBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.order.MessageOrderBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.biz.refund.RefundBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.activity.input.ApplyInput;
import com.party.mobile.web.dto.activity.input.ListInput;
import com.party.mobile.web.dto.activity.output.ActivityOutput;
import com.party.mobile.web.dto.activity.output.ApplyOutput;
import com.party.mobile.web.dto.activity.output.BuyerOutput;
import com.party.mobile.web.dto.activity.output.ListOutput;
import com.party.mobile.web.dto.activity.output.MemberActOutput;
import com.party.mobile.web.dto.activity.output.RegisterCertificateOutput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;
import com.party.pay.service.pay.PayOrderBizService;
import com.party.pay.service.refund.RefundOrderBizService;

/**
 * 活动控制层
 * party
 * Created by wei.li
 * on 2016/9/23 0023.
 */

@Controller
@RequestMapping(value = "/activity/activity")
public class ActivityController {

    @Autowired
    IActivityService activityService;

    @Autowired
    ICityService cityService;

    @Autowired
    IMemberActService memberActService;

    @Autowired
    IMemberService memberService;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    IUserService userService;

    @Autowired
    IActivityDetailService activityDetailService;

    @Autowired
    ActivityBizService activityBizService;

    @Autowired
    MemberActBizService memberActBizService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    VerifyCodeBizService verifyCodeBizService;

    @Autowired
    IMessageSetService messageSetService;

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    OrderActivityBizService orderActivityBizService;

    @Autowired
    IMessageService messageService;

    @Autowired
    INotifySendService notifySendService;

    @Autowired
    DistributorBizService distributorBizService;

    @Autowired
    private PayOrderBizService payOrderBizService;

    @Autowired
    private MessageOrderBizService messageOrderBizService;

    @Autowired
    private RefundBizService refundBizService;

    @Autowired
    RefundOrderBizService refundOrderBizService;
    @Autowired
    ICounterfoilService counterfoilService;
    @Autowired
    CounterfoilBizService counterfoilBizService;

    protected static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    /**
     * 分页查询活动列表
     *
     * @param listInput 查询参数
     * @param page      分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult list(ListInput listInput, Page page) {
        List<ListOutput> listOutputList = activityBizService.list(listInput, page);
        return AjaxResult.success(listOutputList, page);
    }

    /**
     * 获取活动详情
     *
     * @param id 活动主键
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getDetails")
    public AjaxResult getDetails(String id, HttpServletRequest request) {

        //数据验证
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "活动主键不能为空");
        }

        //查询活动
        Activity activity = activityService.get(id);
        if (null == activity) {
            return AjaxResult.error(PartyCode.ACTIVITY_UNEXIST, "活动不存在");
        }

        ActivityOutput activityOutput = activityBizService.get(activity, request);
        return AjaxResult.success(activityOutput);
    }

    /**
     * 获取活动报名列表
     *
     * @param id 活动主键
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getActMember")
    public AjaxResult getActMember(String id, Page page, HttpServletRequest request) {
        WithBuyer withBuyer = new WithBuyer();
        withBuyer.setActId(id);
        List<WithBuyer> withBuyerList = memberActService.withBuyerList(withBuyer, page);
        List<BuyerOutput> buyerOutputList = LangUtils.transform(withBuyerList, new Function<WithBuyer, BuyerOutput>() {
            @Nullable
            @Override
            public BuyerOutput apply(@Nullable WithBuyer withBuyer) {
                return BuyerOutput.transform(withBuyer);
            }
        });
        return AjaxResult.success(buyerOutputList);
    }

    /**
     * 分销活动详情
     *
     * @param input   输入参数
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "distribution/details")
    public AjaxResult getDistributionDetails(GetDistributorInput input, HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        //数据验证
        if (Strings.isNullOrEmpty(input.getTargetId())) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "活动主键不能为空");
        }

        //查询活动
        Activity activity = activityService.get(input.getTargetId());
        if (null == activity) {
            return AjaxResult.error(PartyCode.ACTIVITY_UNEXIST, "活动不存在");
        }
        ActivityOutput activityOutput;
        try {
            activityOutput = activityBizService.getDistributionDetails(activity, input, request);
        } catch (BusinessException be) {
            ajaxResult.setErrorCode(be.getCode());
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }

        ajaxResult.setSuccess(true);
        ajaxResult.setData(activityOutput);
        return ajaxResult;
    }


    /**
     * 活动是否报名
     *
     * @param id 活动id
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping("/isApply")
    public AjaxResult isApply(String id, HttpServletRequest request) {

        //数据验证
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "活动编号不能为空");
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        //是否报名
        MemberAct memberAct = memberActService.findByMemberAct(currentUser.getId(), id);
        if (null != memberAct) {
            ApplyOutput applyOutput = new ApplyOutput();
            applyOutput.setMemberActId(memberAct.getId());
            applyOutput.setActStatus(memberAct.getCheckStatus());
            return AjaxResult.success(applyOutput);
        }
        return AjaxResult.success();
    }

    /**
     * 签到
     *
     * @param hdId
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/signIn")
    public AjaxResult signIn(String hdId, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(hdId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "活动编号不能为空");
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        // 是否报名
        MemberAct memberAct = memberActService.findByMemberAct(currentUser.getId(), hdId);
        if (memberAct == null || memberAct.getJoinin() == YesNoStatus.NO.getCode()) {
            return AjaxResult.error(100, "还没有报名");
        }
        if (memberAct != null) {
            if (memberAct.getCheckStatus() == ActStatus.ACT_STATUS_PAID.getCode()) {
                // 已支付 直接签到
                memberAct.setSignin(YesNoStatus.YES.getCode());
                memberActService.update(memberAct);
                return AjaxResult.success();
            } else if (memberAct.getCheckStatus() == ActStatus.ACT_STATUS_AUDIT_PASS.getCode()) {
                OrderForm orderForm = orderFormService.get(memberAct.getOrderId());
                // 待支付 转向付款
                return AjaxResult.error(orderForm.getId(), 101, "还没有支付");
            } else if (memberAct.getCheckStatus() == ActStatus.ACT_STATUS_AUDITING.getCode()) {
                // 未审核 提示审核 转向我的活动参与
                return AjaxResult.error(102, "还没有审核");
            }
        }
        return null;
    }


    /**
     * 活动报名
     *
     * @param applyInput 报名输入视图
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/apply")
    public AjaxResult apply(@Validated ApplyInput applyInput, BindingResult result, HttpServletRequest request) {

        //数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        // 获取登陆用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        MemberOutput memberOutput = null;
        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (CurrentUser.isThirdparty(currentUser)) {
            //验证码验证
            boolean verifyResult = verifyCodeBizService.verify(applyInput.getMobile(), applyInput.getVerifyCode());
            if (verifyResult) {
                //绑定手机号
                memberOutput = memberBizService.bindPhone(applyInput.getMobile(), applyInput.getRealname(), applyInput.getCompany(), applyInput.getTitle(), applyInput.getIndustry(), request);
                //当前登录用户需重新赋值
                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
            }
        }
        try {
            ApplyOutput applyOutput = memberActBizService.getApplyData(currentUser.getId(), applyInput);

            Map map = new HashMap();
            map.put("order", applyOutput);
            map.put("member", memberOutput);

            return AjaxResult.success(map);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 活动报名
     *
     * @param applyInput 报名输入视图
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/apply2")
    public AjaxResult apply2(@Validated ApplyInput applyInput, BindingResult result, HttpServletRequest request) {

        //数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        
        Activity activity = activityService.get(applyInput.getId());
        if (activity == null) {
        	return AjaxResult.error(PartyCode.DATA_IS_BE_DELETE, "活动不存在");
		}
        
        // 获取登陆用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        MemberOutput memberOutput = null;
        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (CurrentUser.isThirdparty(currentUser)) {
            //验证码验证
            boolean verifyResult = verifyCodeBizService.verify(applyInput.getMobile(), applyInput.getVerifyCode());
            if (verifyResult) {
                //绑定手机号
                memberOutput = memberBizService.bindPhone(applyInput.getMobile(), applyInput.getRealname(), applyInput.getCompany(), applyInput.getTitle(), applyInput.getIndustry(), request);
                //当前登录用户需重新赋值
                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
                
                if (StringUtils.isNotEmpty(applyInput.getWechatNum())) {
                	Member dbMember = memberService.findByPhone(applyInput.getMobile());//根据用户输入手机号获取的会员实体
                	if (dbMember == null) {
                		Member memberNew = memberService.get(currentUser.getId());//根据当前登录用户获取会员实体
                		memberNew.setWechatNum(applyInput.getWechatNum());
                		memberService.update(memberNew);
                	}
				}
            }
        }
        try {
            ApplyOutput applyOutput = memberActBizService.getApplyData2(currentUser.getId(), applyInput);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("order", applyOutput);
            map.put("member", memberOutput);

            return AjaxResult.success(map);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

    }
    
    /**
     * 活动报名
     *
     * @param applyInput 报名输入视图
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/apply3")
    public AjaxResult apply3(@Validated ApplyInput applyInput, BindingResult result, HttpServletRequest request) {

        //数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        
        if (StringUtils.isEmpty(applyInput.getCounterfoilId())) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "没有票据ID");
		}
        
        Counterfoil counterfoil = counterfoilService.get(applyInput.getCounterfoilId());
        if (counterfoil == null) {
        	return AjaxResult.error(PartyCode.DATA_IS_BE_DELETE, "票据不存在");
		}
        
        // 获取登陆用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        MemberOutput memberOutput = null;
        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (CurrentUser.isThirdparty(currentUser)) {
            //验证码验证
            boolean verifyResult = verifyCodeBizService.verify(applyInput.getMobile(), applyInput.getVerifyCode());
            if (verifyResult) {
                //绑定手机号
                memberOutput = memberBizService.bindPhone(applyInput.getMobile(), applyInput.getRealname(), applyInput.getCompany(), applyInput.getTitle(), applyInput.getIndustry(), request);
                //当前登录用户需重新赋值
                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
                
                if (StringUtils.isNotEmpty(applyInput.getWechatNum())) {
                	Member dbMember = memberService.findByPhone(applyInput.getMobile());//根据用户输入手机号获取的会员实体
                	if (dbMember == null) {
                		Member memberNew = memberService.get(currentUser.getId());//根据当前登录用户获取会员实体
                		memberNew.setWechatNum(applyInput.getWechatNum());
                		memberService.update(memberNew);
                	}
				}
            }
        }
        try {
            ApplyOutput applyOutput = memberActBizService.getApplyData3(currentUser.getId(), applyInput, request);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("order", applyOutput);
            map.put("member", memberOutput);

            return AjaxResult.success(map);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 取消报名
     *
     * @param memberActId 活动报名编号
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/cancel")
    public AjaxResult cancel(String memberActId, HttpServletRequest request) {

        //数据验证
        if (Strings.isNullOrEmpty(memberActId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "报名编号不能为空");
        }

        try {
            ApplyOutput applyOutput = memberActBizService.joinCancel(memberActId, request);
            MemberAct memberAct = memberActService.get(memberActId);
            Map<String, Object> map = Maps.newHashMap();
    		List<Counterfoil> counterfoils = counterfoilBizService.getActivityCounterfoils(memberAct.getActId());
    		Activity activity = activityService.get(memberAct.getActId());    		
    		if (counterfoils.size() == 0) {
    			activity = orderActivityBizService.updateActivityInfo(activity.getId());
    		}
    		map.put("limitNum", activity.getLimitNum());
    		map.put("joinNum", activity.getJoinNum());
            map.put("actStatus", applyOutput.getActStatus());
            return AjaxResult.success(map);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 取消报名
     *
     * @param memberActId 活动报名编号
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/distributorCancel")
    public AjaxResult distributorCancel(String memberActId, String distributorId, String parentId, HttpServletRequest request) {

        //数据验证
        if (Strings.isNullOrEmpty(memberActId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "报名编号不能为空");
        }

        try {
            ApplyOutput applyOutput = memberActBizService.joinCancel(memberActId, request);
            MemberAct memberAct = memberActService.get(memberActId);
            Map<String, Object> map = Maps.newHashMap();
            List<Counterfoil> counterfoils = counterfoilBizService.getActivityCounterfoils(memberAct.getActId());
            Activity activity = activityService.get(memberAct.getActId());
    		if (counterfoils.size() == 0) {
    			activity = orderActivityBizService.updateActivityInfo(activity.getId());
    		}
    		map.put("limitNum", activity.getLimitNum());
    		map.put("joinNum", activity.getJoinNum());

            // 是否是自己的分销
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            boolean isMyDistributor
                    = distributorBizService.isMyDistribution(currentUser, GoodsType.GOODS_ACTIVITY.getCode(),
                    activity.getId(), distributorId, parentId);
            
            map.put("actStatus", applyOutput.getActStatus());
            map.put("isMyDistribution", isMyDistributor);
            return AjaxResult.success(map);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 活动凭证接口
     *
     * @param memberActId 活动报名id
     * @param request
     * @return 活动凭证交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/evidence")
    public AjaxResult evidence(String memberActId, HttpServletRequest request) {
        //数据验证
        if (Strings.isNullOrEmpty(memberActId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "报名编号不能为空");
        }

        RegisterCertificateOutput registerCertificateOutput = null;
        try {
            registerCertificateOutput = memberActBizService.getRegisterCertification(memberActId, request);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

        return AjaxResult.success(registerCertificateOutput);
    }

    /**
     * 分页查询发布的活动列表
     *
     * @param page 分页参数
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/publishList")
    public AjaxResult publishList(Page page, HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        Activity activity = new Activity();
        activity.setMember(currentUser.getId());
        activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        activity.setIsCrowdfunded(0);
        List<ActivityOutput> activityOutputs = activityBizService.publishList(page, request, activity);
        return AjaxResult.success(activityOutputs, page);
    }

    /**
     * 分页查询发布的众筹活动列表
     *
     * @param page 分页参数
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/publishZcActivityList")
    public AjaxResult publishZcActivityList(Page page, HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        Activity activity = new Activity();
        activity.setMember(currentUser.getId());
        activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        activity.setIsCrowdfunded(1);
        List<ActivityOutput> activityOutputs = activityBizService.publishList(page, request, activity);
        return AjaxResult.success(activityOutputs, page);
    }

    /**
     * 分页查询发布的活动的报名列表
     *
     * @param page 分页参数
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/actMemberList")
    public AjaxResult actMemberList(String hdId, Page page, HttpServletRequest request) {
        // 数据验证
        if (Strings.isNullOrEmpty(hdId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "活动主键不能为空");
        }
        List<MemberActOutput> memberOutputs = activityBizService.actMemberList(page, hdId);
        return AjaxResult.success(memberOutputs, page);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "verify")
    public AjaxResult verify(Integer checkStatus, String targetId, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(targetId)) {
            MemberAct entity = memberActService.get(targetId);
            if (null == entity || (entity.getCheckStatus() > ActStatus.ACT_STATUS_PAID.getCode())) {
                String strTips = "";
                if (entity.getCheckStatus() == ActStatus.ACT_STATUS_CANCEL.getCode()) {
                    strTips = "当前会员报名状态:已取消;不能继续审核";
                } else {
                    strTips = "当前会员报名状态:未参与;不能继续审核";
                }
                return new AjaxResult(false, strTips);
            }

            Activity activity = activityService.get(entity.getActId());
            Member member = memberService.get(entity.getMemberId());
            Integer orderStatus = null;
            if (checkStatus == ActStatus.ACT_STATUS_AUDIT_PASS.getCode()) {
                entity.setCheckStatus(checkStatus);

                // 通知审核成功
                orderStatus = OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode();
                try {
                    notifySendService.sendApplyPass(activity, entity.getOrderId(), entity.getMobile(), member.getId(), orderStatus);
                } catch (Exception e) {
                    logger.error("审核通过通知异常", e);
                }
                if (StringUtils.isNotEmpty(entity.getOrderId())) {
                    OrderForm orderForm = orderFormService.get(entity.getOrderId());
                    // 如果订单状态等于已退款，则重新生成订单信息
                    if (orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
                        String orderId = orderActivityBizService.insertOrderForm(activity.getTitle(), entity, orderStatus);
                        entity.setOrderId(orderId);
                        memberActService.update(entity);
                    } else {
                        // 0元活动
                        if (orderForm.getPayment() == Float.parseFloat("0.0")) {
                            try {
                                payOrderBizService.updatePayBusiness(entity.getOrderId());
                                messageOrderBizService.send(entity.getOrderId());
                                return new AjaxResult(true, "审核成功");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            orderActivityBizService.updateActivityOrderStatus(entity.getOrderId(), orderStatus);
                        }
                    }
                } else {
                    // 是第一次报名
                    String orderId = orderActivityBizService.insertOrderForm(activity.getTitle(), entity, orderStatus);
                    entity.setOrderId(orderId);
                    memberActService.update(entity);
                    OrderForm orderForm = orderFormService.get(orderId);
                    // 0元活动
                    if (orderForm.getPayment() == Float.parseFloat("0.0")) {
                        try {
                            payOrderBizService.updatePayBusiness(entity.getOrderId());
                            messageOrderBizService.send(entity.getOrderId());
                            return new AjaxResult(true, "审核成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (StringUtils.isNotEmpty(entity.getOrderId())) {
                    OrderForm orderForm = orderFormService.get(entity.getOrderId());
                    if (orderForm != null) {
                        // 退款
                        if (orderForm.getStatus() == OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()) {
                            try {
                                if (orderForm.getPayment() > Float.parseFloat("0.0")) {
                                	// 收费活动退款
									Object responseData = refundBizService.refund(orderForm.getId());
									if (responseData != null) {
										if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
											AliPayRefundResponse response = (AliPayRefundResponse) responseData;
											// 状态码等于10000表示成功
											if (response.getCode().equals(Constant.ALI_SUCCESS_CODE)) {
												return refundBizService.activityRefundCallback(orderForm.getId(), response, PaymentWay.ALI_PAY.getCode());
											}
										} else if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
											WechatPayRefundResponse response = (WechatPayRefundResponse) responseData;
											// 退款成功
											if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode())
													&& Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
												return refundBizService.activityRefundCallback(orderForm.getId(), response, PaymentWay.WECHAT_PAY.getCode());
											}
										}
									}
                                } else {
                                    // 免费活动
                                    refundOrderBizService.updateRefundBusiness(orderForm.getId(), null, null);
                                    messageOrderBizService.activityRefundSend(orderForm.getId());
                                }
                                return new AjaxResult(true, "审核成功");
                            } catch (Exception e) {
                                e.printStackTrace();
                                return new AjaxResult(false, "审核失败");
                            }
                        }
                    }
                }

                orderStatus = OrderStatus.ORDER_STATUS_OTHER.getCode();
                if (StringUtils.isNotEmpty(entity.getOrderId())) {
                    orderActivityBizService.updateActivityOrderStatus(entity.getOrderId(), orderStatus);
                    payOrderBizService.updateJoinNum(entity.getOrderId());
                }
                entity.setJoinin(YesNoStatus.NO.getCode());//设置取消报名
                entity.setCheckStatus(ActStatus.ACT_STATUS_CANCEL.getCode());//设置取消报名
            }
            memberActService.update(entity);
        }
        return new AjaxResult(true, "审核成功");
    }

}
