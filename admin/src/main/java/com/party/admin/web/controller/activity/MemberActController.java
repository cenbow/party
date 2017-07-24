package com.party.admin.web.controller.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.party.core.model.member.WithBuyer;
import com.party.notify.notifyPush.servce.INotifySendService;
import org.apache.ibatis.builder.BuilderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.party.admin.biz.refund.MessageOrderBizService;
import com.party.admin.biz.refund.RefundBizService;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.activity.MemberActOutput;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;
import com.party.pay.service.pay.PayOrderBizService;
import com.party.pay.service.refund.RefundOrderBizService;

@Controller
@RequestMapping(value = "/activity/memberAct")
public class MemberActController {

	@Autowired
	private IActivityService activityService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMemberActService memberActService;

	@Autowired
	private OrderActivityBizService orderActivityBizService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private INotifySendService notifySendService;

	@Autowired
	private PayOrderBizService payOrderBizService;

	@Autowired
	private RefundBizService refundBizService;

	@Autowired
	private MessageOrderBizService messageOrderBizService;

	@Autowired
	RefundOrderBizService refundOrderBizService;

	protected static Logger logger = LoggerFactory.getLogger(MemberActController.class);

	/**
	 * 报名列表
	 * 
	 * @param memberAct
	 * @param page
	 * @param distributionId
	 * @return
	 */
	@RequestMapping(value = "memberActList")
	public ModelAndView memberActList(MemberAct memberAct, Page page, @RequestParam(required = false) String distributionId, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("activity/memberActList");
		page.setLimit(10);
		memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		if (StringUtils.isNotEmpty(memberAct.getActId())) {
			Activity activity = activityService.get(memberAct.getActId());
			mv.addObject("activity", activity);
		}

		Map<String, Object> params = CommonInput.appendParams(commonInput);
		params.put("distributionId", distributionId);
		Map<Integer, String> checkStatus = ActStatus.convertMap();
		List<MemberAct> memberActs = memberActService.listPage(memberAct, params, page);
		List<MemberActOutput> memberActOutputs = LangUtils.transform(memberActs, input -> {
			MemberActOutput output = MemberActOutput.transform(input);
			Member member = memberService.get(input.getMemberId());
			output.setMember(member);
			return output;
		});

		mv.addObject("page", page);
		mv.addObject("input", commonInput);
		mv.addObject("checkStatus", checkStatus);
		mv.addObject("distributionId", distributionId);
		mv.addObject("memberActs", memberActOutputs);
		return mv;
	}

    /**
     * 报名列表
     * @param withBuyer 报名信息
     * @param page 分页参数
     * @param distributionId 分销商编号
     * @param commonInput 参数
     * @return 报名列表
     */
	@ResponseBody
    @RequestMapping(value = "applyList")
	public ModelAndView applyList(WithBuyer withBuyer, Page page, @RequestParam(required = false) String distributionId, CommonInput commonInput){
		page.setLimit(10);
		ModelAndView modelAndView = new ModelAndView("activity/applyList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		Map<Integer, String> checkStatus = ActStatus.convertMap();
        List<WithBuyer> withBuyerList =  memberActService.withActivityList(withBuyer, params, page);

        modelAndView.addObject("page", page);
        modelAndView.addObject("input", commonInput);
        modelAndView.addObject("checkStatus", checkStatus);
        params.put("distributionId", distributionId);
        modelAndView.addObject("list", withBuyerList);
		return modelAndView;
	}

	/**
	 * 审核
	 * 
	 * @param memberAct
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verify")
	public AjaxResult verify(MemberAct memberAct, HttpServletRequest request) {
		if (StringUtils.isNotEmpty(memberAct.getId())) {
			Integer checkStatus = memberAct.getCheckStatus();
			MemberAct entity = memberActService.get(memberAct.getId());
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
				// 审核通过
				orderStatus = OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode();
				try {
					notifySendService.sendApplyPass(activity, entity.getOrderId(), entity.getMobile(), member.getId(), orderStatus);
				} catch (Exception e) {
					logger.error("审核通过消息推送异常", e);
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
								messageOrderBizService.paySend(entity.getOrderId());
								return new AjaxResult(true, "审核成功");
							} catch (Exception e) {
								e.printStackTrace();
								return new AjaxResult(false, "审核失败");
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
							messageOrderBizService.paySend(entity.getOrderId());
							return new AjaxResult(true, "审核成功");
						} catch (Exception e) {
							e.printStackTrace();
							return new AjaxResult(false, "审核失败");
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
				entity.setJoinin(YesNoStatus.NO.getCode());// 设置取消报名
				entity.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_REJECT.getCode());// 设置审核拒绝
			}
			memberActService.update(entity);
		}
		return new AjaxResult(true, "审核成功");
	}

	@ResponseBody
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(MemberAct memberAct, CommonInput commonInput, HttpServletRequest request, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "活动报名人员" + sdf.format(new Date()) + ".xlsx";
			Map<String, Object> params = CommonInput.appendParams(commonInput);

			List<MemberAct> memberActs = memberActService.listPage(memberAct, params, null);
			List<MemberActOutput> memberActOutputs = LangUtils.transform(memberActs, input -> {
				MemberActOutput output = MemberActOutput.transform(input);
				output.setCreateTime(input.getCreateDate());
				Activity activity = activityService.get(input.getActId());
				output.setActivity(activity);
				Member member = memberService.get(input.getMemberId());
				output.setMember(member);
				return output;
			});
			new ExportExcel("活动报名人员", MemberActOutput.class).setDataList(memberActOutputs).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
