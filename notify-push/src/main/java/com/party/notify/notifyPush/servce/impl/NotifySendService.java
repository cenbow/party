package com.party.notify.notifyPush.servce.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Strings;
import com.party.core.model.gatherForm.GatherProject;
import com.party.core.model.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.party.common.redis.StringJedis;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.DateUtils;
import com.party.core.model.MessageTypeModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.goods.Goods;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.message.Message;
import com.party.core.model.message.MessageTag;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.goods.impl.GoodsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.notify.notifyPush.servce.INotifyService;
import com.party.notify.wechatNotify.service.IWechatKeynotService;

/**
 * 消息推送统一接口实现
 * Created by wei.li
 *
 * @date 2017/4/18 0018
 * @time 16:56
 */

@Service
public class NotifySendService implements INotifySendService {

    @Autowired
    private StringJedis stringJedis;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IWechatKeynotService wechatKeynotService;
    
    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private ITargetProjectService targetProjectService;

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IProjectService projectService;

    // 验证码过期时间
    private final static long TIME_OUT = 3600;

    private final static String GOOD_BUY_CODE ="goodBuy";

    private final static String APPLY_CODE = "apply";

    private final static String CROWFUND = "crowfund";

    private final static String FREE_APPLY_REFUSE = "freeApplyRefuse";

    private final static String PAY_APPLY_REFUSE = "payApplyRefuse";

    private final static String APPLY_CHEACK_PASS = "applyCheackPass";

    private final static String GOODS_REFUND = "goodsRefund";

    private final static String ACTIVITY_REFUND = "activityRefund";

    private final static String EXPERT_APPROVE = "expertApprove";

    private final static String EXPERT_CANCEL = "expertCancel";

    private final static String LOVE = "love";

    private final static String COMMENT = "comment";

    private final static String FOCUS = "focus";

    private final static String MEMBER_CHEACK_PASS = "memberCheackPass";

    private final static String MEMBER_CHEACK_REFUSE = "memberCheackRefuse";

    private final static String ACTIVITY_CHEACK_PASS = "activityCheackPass";

    private final static String ACTIVITY_CHEACK_REFUSE = "activityCheackRefuse";

    private final static String SUPPORT_STAGE = "supportStage";

    private final static String PROJECT_STAGE = "projectStage";

    private final static String LAUNCH_PROJECT = "launchProject";

    private final static String MOBILE_CODE = "mobileCode";

    private final static String ACCEPT_SUPPORT = "acceptSupport";

    private final static String ALL_CROWDFUND = "allCrowdfundPush";

    private final static String UNDERWAY_CORDFUND = "underwayCordfundPush";

    private final static String SUCCESS_CROWDFUND = "successCrowdfundPush";
    
    private final static String SUCCESS_FORM = "successFormPush";

    private final static String SUCCESS_BUY_LEVEL = "successBuyLevel";

    protected static Logger logger = LoggerFactory.getLogger(NotifySendService.class);

    private static final String ADMIN_ID = "32f133dd6f764d7681437cc4521daa17";

    /**
     * 发送验证码
     * @param phone 手机号
     */
    public void sendVerifyCode(String phone) {
        // 六位随机数
        DecimalFormat format = new DecimalFormat("000000");
        String code = format.format(Math.random() * 999999);

        // 缓存验证码
        stringJedis.setValue(phone, code, TIME_OUT);
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{code}", code);
        constant.put("senderId", Strings.nullToEmpty(ADMIN_ID));
        notifyService.push(MOBILE_CODE, phone, null, constant);
    }

    /**
     * 下单成功发送
     * @param orderForm 订单
     * @param verifyCode 验证码
     */
    public void senGoodsBuy(OrderForm orderForm, String verifyCode) {
        Goods goods = goodsService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", goods.getTitle());
        constant.put("{verCode}", verifyCode);
        constant.put("{address}", goods.getPlace());
        constant.put("logo", Message.MSG_LOGO_GOODS);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", goods.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        constant.put("targetId", Strings.nullToEmpty(goods.getId()));
        constant.put("senderId", Strings.nullToEmpty(goods.getMemberId()));

        Member author = memberService.get(orderForm.getMemberId());
        JSONObject jsonData = new JSONObject();
        JSONObject keyword1 = wechatKeynotService.get(author.getRealname());//下单者
        JSONObject keyword2 = wechatKeynotService.get(orderForm.getId());//订单号
        JSONObject keyword3 = wechatKeynotService.get(orderForm.getPayment().toString());//金额
        JSONObject keyword4 = wechatKeynotService.get(orderForm.getTitle());//商品信息
        jsonData.put("keyword1", keyword1);
        jsonData.put("keyword2", keyword2);
        jsonData.put("keyword3", keyword3);
        jsonData.put("keyword4", keyword4);

        HashMap<String, Object> urlConstant = Maps.newHashMap();
        urlConstant.put("{id}", orderForm.getId());
        constant = wechatKeynotService.getWechatConstant(GOOD_BUY_CODE, orderForm.getMemberId(), constant, jsonData, urlConstant);
        notifyService.push(GOOD_BUY_CODE, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 报名成功发送
     * @param orderForm 订单
     * @param verifyCode 验证码
     */
    public void sendApply(OrderForm orderForm, String verifyCode) {
        Activity activity = activityService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", activity.getTitle());
        constant.put("{verCode}", verifyCode);
        constant.put("{address}", activity.getPlace());
        constant.put("logo", Message.MSG_LOGO_ACT);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", activity.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        constant.put("targetId", Strings.nullToEmpty(activity.getId()));
        constant.put("senderId", Strings.nullToEmpty(activity.getMember()));

        //微信推送
        Member builder = memberService.get(activity.getMember());
        JSONObject jsonData = new JSONObject();
        JSONObject keyword1 = wechatKeynotService.get(orderForm.getTitle());//标题
        JSONObject keyword2 = wechatKeynotService.get(builder.getRealname());//主办方
        JSONObject keyword3 = wechatKeynotService.get(DateUtils.formatDate(activity.getStartTime()));//时间
        jsonData.put("keyword1", keyword1);
        jsonData.put("keyword2", keyword2);
        jsonData.put("keyword3", keyword3);

        HashMap<String, Object> urlConstant = Maps.newHashMap();
        urlConstant.put("{id}", orderForm.getGoodsId());
        constant = wechatKeynotService.getWechatConstant(APPLY_CODE, orderForm.getMemberId(), constant, jsonData, urlConstant);
        notifyService.push(APPLY_CODE, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 商品退款推送
     * @param orderForm 订单
     */
    public void sendGoodsRefund(OrderForm orderForm) {
        Goods goods = goodsService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", goods.getTitle());
        constant.put("logo", Message.MSG_LOGO_GOODS);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", goods.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        constant.put("targetId", Strings.nullToEmpty(goods.getId()));
        constant.put("senderId", Strings.nullToEmpty(goods.getMemberId()));

        //微信消息
        JSONObject jsonData = new JSONObject();
        JSONObject keyword1 = wechatKeynotService.get(orderForm.getPayment().toString());//退款金额
        JSONObject keyword2 = wechatKeynotService.get(orderForm.getTitle());//商品详情
        JSONObject keyword3 = wechatKeynotService.get(orderForm.getId());//订单编号
        jsonData.put("orderProductPrice",keyword1);
        jsonData.put("orderProductName",keyword2);
        jsonData.put("orderName",keyword3);

        HashMap<String, Object> urlConstant = Maps.newHashMap();
        urlConstant.put("{id}", orderForm.getGoodsId());
        constant = wechatKeynotService.getWechatConstant(GOODS_REFUND, orderForm.getMemberId(), constant, jsonData, urlConstant);
        notifyService.push(GOODS_REFUND, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 活动退款推送
     * @param orderForm 订单
     */
    public void sendActivityRefund(OrderForm orderForm) {
        Activity activity = activityService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", activity.getTitle());
        constant.put("logo", Message.MSG_LOGO_GOODS);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", activity.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        constant.put("targetId", Strings.nullToEmpty(activity.getId()));
        constant.put("senderId", Strings.nullToEmpty(activity.getMember()));

        //微信消息
        JSONObject jsonData = new JSONObject();
        JSONObject keyword1 = wechatKeynotService.get(orderForm.getPayment().toString());//退款金额
        JSONObject keyword2 = wechatKeynotService.get(orderForm.getTitle());//商品详情
        JSONObject keyword3 = wechatKeynotService.get(orderForm.getId());//订单编号
        jsonData.put("orderProductPrice",keyword1);
        jsonData.put("orderProductName",keyword2);
        jsonData.put("orderName",keyword3);

        HashMap<String, Object> urlConstant = Maps.newHashMap();
        urlConstant.put("{id}", orderForm.getGoodsId());
        constant = wechatKeynotService.getWechatConstant(ACTIVITY_REFUND, orderForm.getMemberId(), constant, jsonData, urlConstant);
        notifyService.push(ACTIVITY_REFUND, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 众筹成功发送
     * @param orderForm 订单
     * @param verifyCode 验证码
     * @param member 会员信息
     */
    public void sendCorowdfund(OrderForm orderForm, String verifyCode, Member member) {
        Activity activity = activityService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", activity.getTitle());
        constant.put("{verCode}", verifyCode);
        constant.put("{address}", activity.getPlace());
        constant.put("{user}", member.getRealname());
        constant.put("logo", Message.MSG_LOGO_ACT);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", activity.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        constant.put("targetId", Strings.nullToEmpty(activity.getId()));
        constant.put("senderId", Strings.nullToEmpty(activity.getMember()));

        //微信推送
        JSONObject jsonData = new JSONObject();
        JSONObject keyword1 = wechatKeynotService.get(orderForm.getTitle());//标题
        JSONObject keyword2 = wechatKeynotService.get(orderForm.getPayment().toString());//标题
        JSONObject keyword3 = wechatKeynotService.get(DateUtils.formatDate(orderForm.getCreateDate()));//时间
        jsonData.put("keyword1",keyword1);
        jsonData.put("keyword2", keyword2);
        jsonData.put("keyword3", keyword3);

        HashMap<String, Object> urlConstant = Maps.newHashMap();
        TargetProject targetProject = targetProjectService.findByOrderId(orderForm.getId());
        urlConstant.put("{id}", targetProject.getProjectId());
        constant = wechatKeynotService.getWechatConstant(activity.getMember(), CROWFUND, member.getId(), constant, jsonData, urlConstant);
        notifyService.push(CROWFUND, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 拒绝审核发送
     * @param orderForm 订单
     */
    public void sendFreeCheack(OrderForm orderForm) {
        Activity activity = activityService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", activity.getTitle());
        constant.put("logo", Message.MSG_LOGO_ACT);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", activity.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        notifyService.push(FREE_APPLY_REFUSE, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 拒绝审核发送
     * @param orderForm 订单
     */
    public void sendPayCheack(OrderForm orderForm) {
        Activity activity = activityService.get(orderForm.getGoodsId());
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", activity.getTitle());
        constant.put("logo", Message.MSG_LOGO_ACT);
        constant.put("orderId", orderForm.getId());
        constant.put("orderStatus", orderForm.getStatus());
        constant.put("relId", activity.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", orderForm.getMemberId());
        notifyService.push(PAY_APPLY_REFUSE, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }

    /**
     * 审核通过发送
     * @param activity 活动
     * @param orderId 订单号
     * @param phone 电话
     * @param memberId 会员编号
     * @param orderStatus 订单状态
     */
    public void sendApplyPass(Activity activity, String orderId, String phone, String memberId, Integer orderStatus) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", activity.getTitle());
        constant.put("logo", Message.MSG_LOGO_ACT);
        constant.put("orderId", orderId);
        constant.put("orderStatus", orderStatus);
        constant.put("relId", activity.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("createBy", memberId);
        notifyService.push(APPLY_CHEACK_PASS, phone, memberId, constant);
    }

    /**
     * 达人认证推送
     */
    public void sendExpertApprove(String memberId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("logo", Message.MSG_LOGO_MEMBER);
        constant.put("tag", MessageTypeModel.MESSAGE_TAG_MEMBER);
        constant.put("createBy", memberId);
        notifyService.push(EXPERT_APPROVE, memberId, constant);
    }

    /**
     * 达人取消推送
     */
    public void sendExpertCancel(String memberId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("logo", Message.MSG_LOGO_MEMBER);
        constant.put("tag", MessageTypeModel.MESSAGE_TAG_MEMBER);
        constant.put("createBy", memberId);
        notifyService.push(EXPERT_CANCEL, memberId, constant);
    }

    /**
     * 点赞推送
     * @param memberId 会员
     * @param author 作者
     * @param logo 标志
     * @param url 路径
     * @param content 内容
     * @param relId 关联编号
     */
    public void sendLove(String memberId, Member author, String logo, String url, String content, String relId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{user}", author.getRealname());
        constant.put("logo", logo);
        constant.put("url", url);
        constant.put("constant", content);
        constant.put("relId", relId);
        constant.put("createBy", author.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        notifyService.push(LOVE, memberId, constant);
    }

    /**
     * 关注推送
     * @param memberId 会员
     * @param author 作者
     * @param logo 标志
     * @param url 路径
     * @param content 内容
     * @param relId 关联编号
     */
    public void sendFocus(String memberId, Member author, String logo, String url, String content, String relId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{user}", author.getRealname());
        constant.put("logo", logo);
        constant.put("url", url);
        constant.put("constant", content);
        constant.put("relId", relId);
        constant.put("createBy", author.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        notifyService.push(FOCUS, memberId, constant);
    }

    /**
     * 评论推送
     * @param memberId 会员
     * @param author 作者
     * @param logo 标志
     * @param url 路径
     * @param content 内容
     * @param relId 关联编号
     */
    public void sendComment(String memberId, Member author, String logo, String url, String content, String relId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{user}", author.getRealname());
        constant.put("logo", logo);
        constant.put("url", url);
        constant.put("constant", content);
        constant.put("relId", relId);
        constant.put("createBy", author.getId());
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        notifyService.push(COMMENT, memberId, constant);
    }

    /**
     * 会员审核通过
     * @param member 会员
     */
    public void sendMemberPass(Member member) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("createBy", member.getId());
        constant.put("logo", MessageTypeModel.MSG_LOGO_MEMBER);
        constant.put("tag", MessageTypeModel.MESSAGE_TAG_MEMBER);
        notifyService.push(MEMBER_CHEACK_PASS, member.getMobile(), member.getId(), constant);
    }

    /**
     * 会员审核拒绝
     * @param member 会员
     */
    public void sendMemberRefuse(Member member) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("createBy", member.getId());
        constant.put("logo", MessageTypeModel.MSG_LOGO_MEMBER);
        constant.put("tag", MessageTypeModel.MESSAGE_TAG_MEMBER);
        notifyService.push(MEMBER_CHEACK_REFUSE, member.getMobile(), member.getId(), constant);
    }


    /**
     * 活动审核通过
     * @param title 题目
     * @param relId 关联编号
     * @param memberId 会员编号
     */
    public void sendActivityPass(String title, String relId, String memberId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", title);
        constant.put("logo", MessageTypeModel.MSG_LOGO_ACT);
        constant.put("relId", relId);
        constant.put("createBy", memberId);
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        notifyService.push(ACTIVITY_CHEACK_PASS, memberId, constant);
    }

    /**
     * 活动审核拒绝
     * @param title 题目
     * @param relId 关联编号
     * @param memberId 会员编号
     */
    public void sendActivityRefuse(String title, String relId, String memberId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{title}", title);
        constant.put("logo", MessageTypeModel.MSG_LOGO_ACT);
        constant.put("relId", relId);
        constant.put("createBy", memberId);
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        notifyService.push(ACTIVITY_CHEACK_REFUSE, memberId, constant);
    }


    /**
     * 发送支持阶段提醒的推送
     * @param orderForm 订单编号
     * @param project 众筹项目
     * @param member 会员编号
     */
    public void sendSupportStage(OrderForm orderForm, Project project,Member member){
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{user}", member.getRealname());
        constant.put("{project}", project.getTitle());
        constant.put("{favorerNum}", project.getFavorerNum());
        constant.put("{payment}", orderForm.getPayment());
        constant.put("{actualAmount}", project.getActualAmount());
        getProjectSender(project.getId(), constant);
        notifyService.push(SUPPORT_STAGE, member.getMobile(), member.getId(), constant);
    }


    /**
     * 发送项目阶段提醒
     * @param project 众筹
     * @param member 会员编号
     * @param percent 百分比
     */
    public void sendProjectStage(Project project,Member member, int percent){
        HashMap<String, Object> constant = Maps.newHashMap();
        Float balance = BigDecimalUtils.sub(project.getTargetAmount(), project.getActualAmount());
        balance = BigDecimalUtils.round(balance, 2);
        constant.put("{user}", member.getRealname());
        constant.put("{project}", project.getTitle());
        constant.put("{percent}", percent);
        constant.put("{actualAmount}", project.getActualAmount());
        constant.put("{favorerNum}", project.getFavorerNum());
        constant.put("{balance}", balance);
        getProjectSender(project.getId(), constant);
        notifyService.push(PROJECT_STAGE, member.getMobile(), member.getId(),constant);
    }

    /**
     * 发起众筹消息推送
     * @param project 众筹
     * @param member 会员编号
     */
    public void sendLaunchProject(Project project, Member member) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{user}", member.getRealname());
        constant.put("{project}", project.getTitle());
        constant.put("{targetAmount}", project.getTargetAmount());
        getProjectSender(project.getId(), constant);
        notifyService.push(LAUNCH_PROJECT, member.getMobile(), member.getId(), constant);
    }

    /**
     * 获取众筹发送者
     * @param projectId 众筹编号
     * @param constant 内容
     */
    public void getProjectSender(String projectId, HashMap<String, Object> constant){
        TargetProject targetProject = targetProjectService.findByProjectId(projectId);
        Activity activity = activityService.get(targetProject.getTargetId());
        if (null != activity){
            constant.put("targetId", Strings.nullToEmpty(activity.getId()));
            constant.put("senderId", Strings.nullToEmpty(activity.getMember()));
        }
    }


    /**
     * 众筹收到支持消息推送
     * @param orderForm 订单信息
     * @param favorer 支持者
     * @param author 接受者
     * @param activityMemberId 众筹项目发起者
     */
    public void sendAcceptSupport(OrderForm orderForm, Member favorer, Member author, String activityMemberId, String projectId) {
        HashMap<String, Object> constant = Maps.newHashMap();
        JSONObject jsonData = new JSONObject();
        JSONObject keyword1 = wechatKeynotService.get(orderForm.getTitle());//标题
        JSONObject keynote2 = wechatKeynotService.get(favorer.getRealname());//支持者
        JSONObject keynote3 = wechatKeynotService.get(orderForm.getPayment().toString());//金额
        JSONObject keynote4 = wechatKeynotService.get(DateUtils.formatDate(orderForm.getCreateDate()));//时间
        jsonData.put("keyword1", keyword1);
        jsonData.put("keyword2", keynote2);
        jsonData.put("keyword3", keynote3);
        jsonData.put("keyword4", keynote4);

        HashMap<String, Object> urlConstant = Maps.newHashMap();
        urlConstant.put("{id}", projectId);
        constant = wechatKeynotService.getWechatConstant(activityMemberId, ACCEPT_SUPPORT, author.getId(), constant, jsonData, urlConstant);

        //特殊处理众筹支持消息
        Support support = supportService.findByOrderId(orderForm.getId());
        JSONObject jsonRemark = wechatKeynotService.get(support.getComment());
        jsonData.put("remark", jsonRemark);
        notifyService.push(ACCEPT_SUPPORT, author.getMobile(), author.getId(), constant);
    }

    /**
     * 免费活动报名
     * @param memberActId 活动报名ID
     */
	public void sendApplyFree(String activityId, String memberActId, String verifyCode) {
		Activity activity = activityService.get(activityId);
		MemberAct memberAct = memberActService.get(memberActId);
		HashMap<String, Object> constant = Maps.newHashMap();
		constant.put("{title}", activity.getTitle());
		constant.put("{verCode}", verifyCode);
		constant.put("{address}", activity.getPlace());
		constant.put("logo", Message.MSG_LOGO_ACT);
		constant.put("relId", activity.getId());
		constant.put("tag", MessageTag.UNDEFIND.getCode());
		constant.put("orderId", memberActId);
		constant.put("orderStatus", OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
		constant.put("createBy", memberAct.getMemberId());
        constant.put("targetId", Strings.nullToEmpty(activityId));
        constant.put("senderId", Strings.nullToEmpty(activity.getMember()));
		notifyService.push(APPLY_CODE, memberAct.getMobile(), memberAct.getMemberId(), constant);
	}

    /**
     * 所有众筹手动推送
     */
	public void sendAllCrowdfund(){
        List<ProjectWithAuthor> allList = projectService.allList();
        this.handSendCrowdfund(allList, ALL_CROWDFUND);
    }

    /**
     * 众筹成功推送
     */
    public void sendSuccessCrowdfund(){
        List<ProjectWithAuthor> allList = projectService.successList();
        this.handSendCrowdfund(allList, SUCCESS_CROWDFUND);
    }

    /**
     * 众筹中推送
     */
    public void sendUnderwayCordfund(){
        List<ProjectWithAuthor> allList = projectService.underwayList();
        this.handSendCrowdfund(allList, UNDERWAY_CORDFUND);
    }

    /**
     * 推送所有众筹
     * @param targetId 目标编号
     * @param template 模板类容
     */
    public void sendAllCrowdfund(String targetId, String authorId, String template) {
        List<ProjectWithAuthor> allList = projectService.allList(targetId);
        this.handSendCrowdfund(allList, ALL_CROWDFUND, template, targetId, authorId);
    }

    /**
     * 发送成功众筹
     * @param targetId 目标编号
     * @param template 模板
     */
    public void sendSuccessCrowdfund(String targetId, String authorId, String template) {
        List<ProjectWithAuthor> allList = projectService.successList(targetId);
        this.handSendCrowdfund(allList, SUCCESS_CROWDFUND, template, targetId, authorId);
    }

    /**
     * 发送
     * @param targetId 目标编号
     * @param template 模板
     */
    public void sendUnderwayCordfund(String targetId, String authorId, String template) {
        List<ProjectWithAuthor> allList = projectService.underwayList(targetId);
        this.handSendCrowdfund(allList, UNDERWAY_CORDFUND, template, targetId, authorId);
    }

    /**
     * 手动推送众筹
     * @param list 众筹列表
     * @param eventCode 消息事件
     * @param template 模板编号
     * @param targetId 目标编号
     * @param authorId 会员编号
     */
    private void handSendCrowdfund(List<ProjectWithAuthor> list, String eventCode, String template ,String targetId, String authorId){
        for (ProjectWithAuthor projectWithAuthor : list){
            HashMap<String, Object> constant = Maps.newHashMap();
            String user = projectWithAuthor.getAuthorName() == null ? "" : projectWithAuthor.getAuthorName();
            constant.put("{user}", user);
            constant.put("{project}", projectWithAuthor.getTitle());
            constant.put("{favorerNum}", projectWithAuthor.getFavorerNum());
            constant.put("{actualAmount}", projectWithAuthor.getActualAmount());
            constant.put("logo", Message.MSG_LOGO_ACT);
            constant.put("relId", projectWithAuthor.getId());
            constant.put("tag", MessageTag.UNDEFIND.getCode());
            constant.put("orderStatus", OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
            constant.put("createBy", projectWithAuthor.getAuthorId());
            constant.put("targetId", Strings.nullToEmpty(targetId));
            constant.put("senderId", Strings.nullToEmpty(authorId));
            if (null == template){
                notifyService.push(eventCode, projectWithAuthor.getAuthorMobile(), projectWithAuthor.getAuthorId(), constant);
            }
            else {
                notifyService.push(eventCode, projectWithAuthor.getAuthorMobile(), projectWithAuthor.getAuthorId(), constant, template);
            }
        }
    }


    /**
     * 手动推送众筹
     * @param list 众筹列表
     * @param eventCode 时间编号
     */
    private void handSendCrowdfund(List<ProjectWithAuthor> list,String eventCode){
        this.handSendCrowdfund(list, eventCode, null, null, null);
    }
    
    /**
     * 表单填写成功推送
     */
    public void sendSuccessForm(String telephone, GatherProject gatherProject) {
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("createBy", gatherProject.getCreateBy());
        constant.put("logo", Message.MSG_LOGO_TZ);
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        constant.put("{formName}", gatherProject.getTitle());
        constant.put("{currentTime}", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm"));
    	notifyService.push(SUCCESS_FORM, telephone, gatherProject.getCreateBy(), constant);
    }

    public void sendBuyLevel(OrderForm orderForm) {
        HashMap<String, Object> constant = Maps.newHashMap();
        Member member = memberService.get(orderForm.getMemberId());
        constant.put("{user}", member.getRealname());
        constant.put("createBy", orderForm.getMemberId());
        constant.put("logo", Message.MSG_LOGO_TZ);
        constant.put("tag", MessageTag.UNDEFIND.getCode());
        notifyService.push(SUCCESS_BUY_LEVEL, orderForm.getPhone(), orderForm.getMemberId(), constant);
    }
}
