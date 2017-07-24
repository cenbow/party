package com.party.notify.notifyPush.servce;

import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.gatherForm.GatherProject;
import com.party.core.model.member.Member;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.model.order.OrderForm;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 消息统一推送接口
 * Created by wei.li
 *
 * @date 2017/4/18 0018
 * @time 16:45
 */
public interface INotifySendService {

    /**
     * 推送验证码
     * @param phone 手机号
     */
    void sendVerifyCode(String phone);

    /**
     * 购买成功推送
     * @param orderForm 订单
     * @param verifyCode 验证码
     */
    void senGoodsBuy(OrderForm orderForm, String verifyCode);

    /**
     * 报名成功推送
     * @param orderForm 订单
     * @param verifyCode 验证码
     */
    void sendApply(OrderForm orderForm, String verifyCode);

    /**
     * 商品退款推送
     * @param orderForm 订单
     */
    void sendGoodsRefund(OrderForm orderForm);

    /**
     * 活动退款推送
     * @param orderForm 订单
     */
    void sendActivityRefund(OrderForm orderForm);

    /**
     * 众筹成功推送
     * @param orderForm 订单
     * @param verifyCode 验证码
     * @param member 会员信息
     */
    void sendCorowdfund(OrderForm orderForm, String verifyCode, Member member);

    /**
     * 免费活动审核推送
     * @param orderForm 订单
     */
    void sendFreeCheack(OrderForm orderForm);

    /**
     * 付费活动审核
     * @param orderForm 订单
     */
    void sendPayCheack(OrderForm orderForm);

    /**
     * 活动审核通过
     * @param activity 活动
     * @param orderId 订单号
     * @param phone 电话
     * @param memberId 会员编号
     * @param orderStatus 订单状态
     */
    void sendApplyPass(Activity activity, String orderId, String phone, String memberId, Integer orderStatus);

    /**
     * 达人认证推送
     * @param memberId 会员编号
     */
    void sendExpertApprove(String memberId);

    /**
     * 达人取消推送
     * @param memberId 会员编号
     */
    void sendExpertCancel(String memberId);


    /**
     * 点赞推送
     * @param memberId 会员
     * @param author 作者
     * @param logo 标志
     * @param url 路径
     * @param content 内容
     * @param relId 关联编号
     */
    void sendLove(String memberId, Member author, String logo, String url, String content, String relId);

    /**
     * 关注推送
     * @param memberId 会员
     * @param author 作者
     * @param logo 标志
     * @param url 路径
     * @param content 内容
     * @param relId 关联编号
     */
    void sendFocus(String memberId, Member author, String logo, String url, String content, String relId);

    /**
     * 评论推送
     * @param memberId 会员
     * @param author 作者
     * @param logo 标志
     * @param url 路径
     * @param content 内容
     * @param relId 关联编号
     */
    void sendComment(String memberId, Member author, String logo, String url, String content, String relId);

    /**
     * 会员审核通过推送
     * @param member 会员
     */
    void sendMemberPass(Member member);

    /**
     * 会员审核拒绝推送
     * @param member 会员
     */
    void sendMemberRefuse(Member member);

    /**
     * 活动审核通过推送
     * @param title 题目
     * @param relId 关联编号
     * @param memberId 会员编号
     */
    void sendActivityPass(String title, String relId, String memberId);

    /**
     * 活动审核拒绝通过推送
     * @param title 题目
     * @param relId 关联编号
     * @param memberId 会员编号
     */
    void sendActivityRefuse(String title, String relId, String memberId);

    /**
     * 发送支持阶段提醒的推送
     * @param orderForm 订单编号
     * @param project 众筹项目
     * @param member 会员编号
     */
    void sendSupportStage(OrderForm orderForm, Project project, Member member);


    /**
     * 发送项目阶段提醒
     * @param project 众筹
     * @param member 会员编号
     * @param percent 百分比
     */
    void sendProjectStage(Project project,Member member, int percent);

    /**
     * 发起众筹消息推送
     * @param project 众筹
     * @param member 会员编号
     */
    void sendLaunchProject(Project project, Member member);

    /**
     * 众筹收到支持消息推送
     * @param orderForm 订单信息
     * @param favorer 支持者
     * @param author 接受者
     * @param activityMemberId 众筹项目发起者
     */
    void sendAcceptSupport(OrderForm orderForm, Member favorer, Member author, String activityMemberId, String projectId);

    /**
     * 免费活动报名
     * @param activityId 活动ID
     * @param memberActId 活动报名ID
     * @param verifyCode 核销码
     */
	void sendApplyFree(String activityId, String memberActId, String verifyCode);

    /**
     * 所有众筹手动推送
     */
    void sendAllCrowdfund();

    /**
     * 众筹成功推送
     */
    void sendSuccessCrowdfund();

    /**
     * 众筹中推送
     */
    void sendUnderwayCordfund();

    /**
     * 推送所有众筹
     * @param targetId 目标编号
     * @param authorId 会员编号
     * @param template 模板
     */
    void sendAllCrowdfund(String targetId, String authorId, String template);


    /**
     * 推送成功的众筹
     * @param targetId 目标编号
     * @param authorId 会员编号
     * @param template 模板
     */
    void sendSuccessCrowdfund(String targetId, String authorId, String template);

    /**
     *  推送进行中的
     * @param targetId 目标编号
     * @param authorId 会员编号
     * @param template 模板
     */
    void sendUnderwayCordfund(String targetId, String authorId, String template);
    
    /**
     * 表单填写成功推送
     */
    void sendSuccessForm(String telephone, GatherProject gatherProject);

    /**
     * 服务升级推送
     * @param orderForm
     */
    void sendBuyLevel(OrderForm orderForm);
}
