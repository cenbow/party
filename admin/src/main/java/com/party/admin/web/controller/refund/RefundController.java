package com.party.admin.web.controller.refund;

import com.party.core.service.crowdfund.ISupportRefundService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.party.admin.biz.crowdfund.SupportBizService;
import com.party.admin.biz.refund.MessageOrderBizService;
import com.party.admin.biz.refund.RefundBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.constant.Constant;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.order.IOrderFormService;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;
import com.party.pay.service.refund.RefundOrderBizService;

/**
 * 
 * 此类描述的是：退款控制层
 * 
 * @author: Administrator
 * @version: 2017年2月4日 下午6:25:48
 */

@Controller
@RequestMapping(value = "/refund/")
public class RefundController {

	@Autowired
	private RefundBizService refundBizService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	RefundOrderBizService refundOrderBizService;

	@Autowired
	MessageOrderBizService messageOrderBizService;
	
	@Autowired
    private ISupportService supportService;

	@Autowired
	private ISupportRefundService supportRefundService;
	
	@Autowired
    private IProjectService projectService;

	protected static Logger logger = LoggerFactory.getLogger(RefundController.class);

	@ResponseBody
	@RequestMapping("refund")
	public AjaxResult refund(String orderId) {
		if (StringUtils.isEmpty(orderId)) {
			return new AjaxResult(false, "订单号不能为空");
		}
		try {
			OrderForm orderForm = orderFormService.get(orderId);
			Object responseData = refundBizService.refund(orderId);
			if (responseData != null) {
				if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
					AliPayRefundResponse response = (AliPayRefundResponse) responseData;
					// 状态码等于10000表示成功
					if (response.getCode().equals(Constant.ALI_SUCCESS_CODE)) {
						return orderRefundCallback(orderId, response, PaymentWay.ALI_PAY.getCode());
					} else {
						logger.info(response.getSubMsg());
						return new AjaxResult(false, response.getSubMsg());
					}
				} else if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
					WechatPayRefundResponse response = (WechatPayRefundResponse) responseData;
					// 退款成功
					if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
						return orderRefundCallback(orderId, response, PaymentWay.WECHAT_PAY.getCode());
					} else {
						logger.info(response.getErrCodeDes());
						return new AjaxResult(false, response.getErrCodeDes());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 订单退款回调
	 * 
	 * @param orderId
	 * @param refundResponse
	 * @param paymentWay
	 * @return
	 */
	public AjaxResult orderRefundCallback(String orderId, Object refundResponse, Integer paymentWay) {
		OrderForm orderForm = orderFormService.get(orderId);
		if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())) {
			WechatPayRefundResponse response = null;
			if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
				response = (WechatPayRefundResponse) refundResponse;
			}
			if (StringUtils.isNotEmpty(orderForm.getGoodsId()) && response != null) {
				try {
					Project project = projectService.get(orderForm.getGoodsId());
					Support support = supportService.findByOrderId(orderId);
					logger.info("准备处理众筹支持退款业务");
					supportRefundService.refund(support, response);
					logger.info("处理退款众筹支持业务成功");
				} catch (Exception e) {
					logger.info("处理退款众筹支持业务出错{}", e);
					return new AjaxResult(false, "退款失败");
				}
			}
		} else {
			try {
				logger.info("准备处理退款业务");
				refundOrderBizService.updateRefundBusiness(orderId, refundResponse, paymentWay);
				logger.info("处理退款业务成功");
			} catch (Exception e) {
				logger.info("处理退款业务出错{}", e);
				return new AjaxResult(false, "退款失败");
			}
			try {
				logger.info("准备退款消息推送");
				messageOrderBizService.refundSend(orderId);
				logger.info("退款消息推送成功");
			} catch (Exception e) {
				logger.info("退款消息推送异常{}", e);
				return new AjaxResult(false, "退款失败");
			}
		}
		logger.info("退款成功");
		return new AjaxResult(true, "退款成功");
	}
}