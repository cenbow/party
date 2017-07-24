package com.party.mobile.biz.order;

import java.util.List;

import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.member.Member;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.member.IThirdPartyUserService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.notify.wechatNotify.service.IWechatNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.order.IOrderFormService;

/**
 * 订单消息推送
 * Created by wei.li
 *
 * @date 2017/3/31 0031
 * @time 19:22
 */
@Service
public class MessageOrderBizService {

    @Autowired
    private INotifySendService notifySendService;

    @Autowired
    private IOrderFormService orderFormService;
    
    @Autowired
    private IGoodsCouponsService goodsCouponsService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private ITargetProjectService targetProjectService;

	@Autowired
	private IActivityService activityService;





    /**
     * 订单短信推送
     * @param orderId 订单号
     */
    public void send(String orderId){
    	OrderForm orderForm = orderFormService.get(orderId);
    	GoodsCoupons t = new GoodsCoupons();
    	t.setOrderId(orderId);
		List<GoodsCoupons> goodsCoupons = goodsCouponsService.list(t);
    	for (GoodsCoupons coupons : goodsCoupons) {
    		// 发送推送短信
    		if (PaymentWay.CROWD_FUND_PAY.getCode().equals(orderForm.getPaymentWay())){
    			Member member = memberService.get(orderForm.getMemberId());
				notifySendService.sendCorowdfund(orderForm, coupons.getVerifyCode(), member);
    		}
    		else {
				if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
					notifySendService.sendApply(orderForm, coupons.getVerifyCode());
				}
				else if (orderForm.getType().equals(OrderType.ORDER_NOMAL.getCode())  || orderForm.getType().equals(OrderType.ORDER_CUSTOMIZED.getCode()) ) {
					notifySendService.senGoodsBuy(orderForm, coupons.getVerifyCode());
				}
    		}
		}

    }
    
    /**
	 * 活动审核拒绝推送
	 * @param orderId 订单号
	 */
	public void activityRefundSend(String orderId) {
		OrderForm orderForm = orderFormService.get(orderId);
		if (orderForm.getPayment() > Float.valueOf("0.0")) {
			notifySendService.sendPayCheack(orderForm);
		} else {
			notifySendService.sendFreeCheack(orderForm);
		}
	}


	/**
	 * 支持阶段短信推送
	 * @param orderForm 订单信息
	 * @param project 项目编号
	 */
	public void supportSend(OrderForm orderForm, Project project){
		//项目开关
		TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
		Activity activity = activityService.get(targetProject.getTargetId());

		if (YesNoStatus.NO.getCode().equals(activity.getCrowdfundHintSwitch())){
			return;
		}
		//发送前十个人
		if (project.getFavorerNum()> 10){
			return;
		}
		Member member = memberService.get(project.getAuthorId());
		notifySendService.sendSupportStage(orderForm, project, member);
	}


	/**
	 * 发送众筹
	 * @param project 众筹信息
	 * @param beforePercent 众筹之前的百分比
	 * @param afterPercent 众筹之后的百分比
	 */
	public void projectSend(Project project, final int beforePercent,final int afterPercent){

		//项目开关
		TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
		Activity activity = activityService.get(targetProject.getTargetId());

		if (YesNoStatus.NO.getCode().equals(activity.getCrowdfundHintSwitch())){
			return;
		}

		if (afterPercent >= 10){
			return;
		}
		//如果十位有变更 发送
		if (afterPercent > beforePercent){
			Member member = memberService.get(project.getAuthorId());
			notifySendService.sendProjectStage(project, member, afterPercent * 10);
		}
	}


	/**
	 * 众筹支持通知
	 * @param orderForm 订单
	 * @param project 众筹项目
	 */
	public void supportWechatSend(OrderForm orderForm, Project project){
		Member favorer = memberService.get(orderForm.getMemberId());

		//接受者
		Member author = memberService.get(project.getAuthorId());

		TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
		Activity activity = activityService.get(targetProject.getTargetId());
		notifySendService.sendAcceptSupport( orderForm, favorer, author, activity.getMember(), project.getId());
	}


	/**
	 * 免费活动推送
	 * @param type 类型
	 * @param goodsId 活动/玩法ID
	 * @param businessId 活动报名/玩法预定ID
	 * @param memberId 当前用户ID
	 */
	public void sendFree(Integer type, String goodsId, String businessId, String memberId) {
    	GoodsCoupons t = new GoodsCoupons();
    	t.setGoodsId(goodsId);
    	t.setMemberId(memberId);
		List<GoodsCoupons> goodsCoupons = goodsCouponsService.list(t);
		for (GoodsCoupons coupons : goodsCoupons) {
			if (type.equals(OrderType.ORDER_ACTIVITY.getCode())) {
				notifySendService.sendApplyFree(goodsId, businessId, coupons.getVerifyCode());
			}
		}
	}
}
