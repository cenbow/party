package com.party.web.web.controller.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;
import com.party.pay.service.pay.PayOrderBizService;
import com.party.pay.service.refund.RefundOrderBizService;
import com.party.web.biz.activity.ApplyBizService;
import com.party.web.biz.refund.MessageOrderBizService;
import com.party.web.biz.refund.RefundBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.excel.ExportExcel;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.output.activity.MemberActOutput;

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
	INotifySendService notifySendService;

	@Autowired
	private ApplyBizService applyBizService;

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
	 * 报名管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "memberActList")
	public ModelAndView memberActList(MemberAct memberAct, Page page, @RequestParam(required = false) String distributionId, Integer timeType, String c_start, String c_end) {
		ModelAndView mv = new ModelAndView("activity/memberActList");
		page.setLimit(10);
		memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		if (StringUtils.isNotEmpty(memberAct.getActId())) {
			Activity activity = activityService.get(memberAct.getActId());
			mv.addObject("activity", activity);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		// 时间块处理
		if (timeType != null && timeType != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date ed = calendar.getTime(); // 结束时间
			if (timeType == 2) { // 本周内
				int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (day_of_week == 0) {
					day_of_week = 7;
				}
				calendar.add(Calendar.DATE, -day_of_week + 1);
			} else if (timeType == 3) { // 本月内
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			}
			Date sd = calendar.getTime(); // 开始时间
			String std = sdf.format(sd) + " 00:00:00";
			params.put("startDate", std);
			String end = sdf.format(ed) + " 23:59:59";
			params.put("endDate", end);

			mv.addObject("timeType", timeType);
		}
		// 时间段处理
		if (StringUtils.isNotEmpty(c_start)) {
			params.put("c_start", c_start);
			mv.addObject("c_start", c_start);
		}
		if (StringUtils.isNotEmpty(c_end)) {
			params.put("c_end", c_end);
			mv.addObject("c_end", c_end);
		}

		Map<Integer, String> checkStatus = Maps.newHashMap();
		checkStatus.put(ActStatus.ACT_STATUS_AUDITING.getCode(), ActStatus.ACT_STATUS_AUDITING.getValue());
		checkStatus.put(ActStatus.ACT_STATUS_AUDIT_PASS.getCode(), ActStatus.ACT_STATUS_AUDIT_PASS.getValue());
		checkStatus.put(ActStatus.ACT_STATUS_AUDIT_REJECT.getCode(), ActStatus.ACT_STATUS_AUDIT_REJECT.getValue());
		checkStatus.put(ActStatus.ACT_STATUS_CANCEL.getCode(), ActStatus.ACT_STATUS_CANCEL.getValue());
		checkStatus.put(ActStatus.ACT_STATUS_NO_JOIN.getCode(), ActStatus.ACT_STATUS_NO_JOIN.getValue());
		checkStatus.put(ActStatus.ACT_STATUS_PAID.getCode(), ActStatus.ACT_STATUS_PAID.getValue());
		mv.addObject("checkStatus", checkStatus);

		params.put("distributionId", distributionId);
		List<MemberAct> memberActs = memberActService.listPage(memberAct, params, page);
		List<MemberActOutput> memberActOutputs = LangUtils.transform(memberActs, input -> {
			MemberActOutput output = MemberActOutput.transform(input);
			Member member = memberService.get(input.getMemberId());
			output.setMember(member);
			return output;
		});
		mv.addObject("page", page);
		mv.addObject("distributionId", distributionId);
		mv.addObject("memberActs", memberActOutputs);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "memberActListJson")
	public Map<String, Object> memberActListJson(Page page, String actId) {
		page.setLimit(10);
		MemberAct memberAct = new MemberAct();
		memberAct.setDelFlag(MemberAct.DEL_FLAG_NORMAL);
		List<MemberAct> memberActs = Lists.newArrayList();
		if (StringUtils.isNotEmpty(actId)) {
			memberAct.setActId(actId);
			memberActs = memberActService.listPage(memberAct, page);
		} else {
			memberAct.setMemberId(RealmUtils.getCurrentUser().getId());
			memberActs = memberActService.activityMemberActs(memberAct, page);
		}

		List<MemberActOutput> memberActOutputs = LangUtils.transform(memberActs, input -> {
			MemberActOutput output = MemberActOutput.transform(input);
			Activity activity = activityService.get(input.getActId());
			int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
			activity.setJoinNum(joinNum);
			output.setActivity(activity);
			Member member = memberService.get(input.getMemberId());
			output.setMember(member);
			return output;
		});

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", memberActOutputs);
		map.put("page", page);
		return map;
	}

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
					applyBizService.setDistribution(entity.getId(), orderId);
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
	public String exportFile(MemberAct memberAct, Integer timeType, String c_start, String c_end, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "活动报名人员" + sdf.format(new Date()) + ".xlsx";
			Map<String, Object> params = new HashMap<String, Object>();
			// 时间块处理
			if (timeType != null && timeType != 0) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				Date ed = calendar.getTime(); // 结束时间
				if (timeType == 2) { // 本周内
					int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
					if (day_of_week == 0) {
						day_of_week = 7;
					}
					calendar.add(Calendar.DATE, -day_of_week + 1);
				} else if (timeType == 3) { // 本月内
					calendar.set(Calendar.DAY_OF_MONTH, 1);
				}
				Date sd = calendar.getTime(); // 开始时间
				String std = sdf.format(sd) + " 00:00:00";
				params.put("startDate", std);
				String end = sdf.format(ed) + " 23:59:59";
				params.put("endDate", end);
			}
			// 时间段处理
			if (StringUtils.isNotEmpty(c_start)) {
				params.put("c_start", c_start);
			}
			if (StringUtils.isNotEmpty(c_end)) {
				params.put("c_end", c_end);
			}
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
