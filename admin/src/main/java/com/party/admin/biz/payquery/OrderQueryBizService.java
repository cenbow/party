package com.party.admin.biz.payquery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.admin.biz.crowdfund.SupportBizService;
import com.party.admin.biz.order.OrderFormBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.common.constant.Constant;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.DateUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.service.crowdfund.IProjectRefundService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportRefundService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormInfoService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderTradeService;
import com.party.pay.model.pay.ali.PayResponse;
import com.party.pay.model.pay.wechat.NotifyRequest;
import com.party.pay.model.query.AliPayQueryResponse;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.query.WechatPayQueryResponse;
import com.party.pay.model.refund.AliPayRefundResponse;
import com.party.pay.model.refund.WechatPayRefundResponse;
import com.party.pay.service.pay.PayOrderBizService;

/**
 * 订单查询校正
 * 
 * @author Administrator
 *
 */
@Service
public class OrderQueryBizService {

	@Autowired
	IOrderFormInfoService orderFormInfoService;

	@Autowired
	IOrderFormService orderFormService;

	@Autowired
	OrderFormBizService orderFormBizService;

	@Autowired
	PayOrderBizService payOrderBizService;

	@Autowired
	IOrderTradeService orderTradeService;

	@Autowired
	SupportBizService supportBizService;

	@Autowired
	IProjectService projectService;

	@Autowired
	ISupportService supportService;

	@Autowired
	IMemberActService memberActService;
	
	@Autowired
	ISupportRefundService supportRefundService;

	@Autowired
	IProjectRefundService projectRefundService;

	@Autowired
	QueryBizService queryBizService;

	/**
	 * 更新订单相关信息
	 * 
	 * @param payment
	 *            金额
	 * @param orderId
	 *            订单编号
	 * @param transactionId
	 *            交易编号
	 * @param tradeState
	 *            交易状态
	 * @param mchId
	 *            商户号
	 */
	public OrderForm orderCb(Float payment, String orderId, String transactionId, String tradeState, String mchId) {
		OrderForm t = orderFormService.get(orderId);
		if (payment != null) {
			t.setPayment(payment);
		}
		t.setTransactionId(transactionId);
		t.setTradeState(tradeState);
		t.setMerchantId(mchId);
		orderFormService.update(t);

		if (t.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
			MemberAct memberAct = memberActService.findByOrderId(orderId);
			if (memberAct != null && payment != null) {
				memberAct.setPayment(payment);
				memberActService.update(memberAct);
			}
		}
		return t;
	}

	/**
	 * 交易状态NOTPAY，订单状态等于已支付或者已退款，更改为待支付
	 * 
	 * @param orderId
	 */
	public void updateNotPay(String orderId) {
		OrderForm t = orderFormService.get(orderId);
		t.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
		orderFormService.update(t);
		if (t.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
			MemberAct memberAct = memberActService.findByOrderId(orderId);
			if (memberAct != null) {
				memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode());
				memberActService.update(memberAct);
			}
		}
	}

	/**
	 * 微信订单查询校正
	 * 
	 * @param queryResponse
	 * @param orderForm
	 * @throws Exception
	 */
	public void wxCallback(WechatPayQueryResponse queryResponse, OrderForm orderForm, List<OrderForm> orderForms) throws Exception {
		// 更新金额
		Float payment = null;
		if (StringUtils.isNotEmpty(queryResponse.getTotalFee())) {
			int totalFee = Integer.valueOf(queryResponse.getTotalFee());
			double total = BigDecimalUtils.div(Double.valueOf(totalFee), Double.valueOf(100), 2);
			String value = String.valueOf(total);
			payment = Float.valueOf(value);
		}
		OrderForm t = orderCb(payment, orderForm.getId(), queryResponse.getTransactionId(), queryResponse.getTradeState(), queryResponse.getMchId());

		if (queryResponse.getTradeState().equals(TradeStatus.WX_SUCCESS.getCode())
				&& !orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode())) {
			// 微信交易状态SUCCESS，订单状态不是已支付，手动回调
			NotifyRequest notifyRequest = new NotifyRequest();
			MyBeanUtils.copyBean2Bean(notifyRequest, queryResponse);
			orderFormBizService.updatePayBusiness(orderForm, notifyRequest, PaymentWay.WECHAT_PAY.getCode());
			orderForms.add(t);
		} else if (queryResponse.getTradeState().equals(TradeStatus.WX_REFUND.getCode())
				&& !orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
			// 微信交易状态REFUND，订单状态不是已退款，手动回调
			WechatPayRefundResponse response = new WechatPayRefundResponse();
			MyBeanUtils.copyBean2Bean(response, queryResponse);
			if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())) {
				// 如果是众筹支持订单，单独处理业务
				Support support = supportService.findByOrderId(orderForm.getId());
				if (support == null) {
					support = new Support();
					support.setFavorerId(orderForm.getMemberId());
					support.setPayStatus(0); // 待支付
					support.setOrderId(orderForm.getId());
					support.setProjectId(orderForm.getGoodsId());
					supportService.insert(support);
				}
				supportRefundService.refund(support, response);
				orderForms.add(t);
			} else {
				orderFormBizService.updateRefundBusiness(orderForm.getId(), response, PaymentWay.WECHAT_PAY.getCode());
				orderForms.add(t);
			}
		} else if (queryResponse.getTradeState().equals(TradeStatus.WX_NOTPAY.getCode())) {
			if (orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode())
					|| orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
				// 微信交易状态NOTPAY，订单状态等于已支付或者已退款，更改为待支付
				updateNotPay(orderForm.getId());
				orderForms.add(t);
			}
		}
	}

	/**
	 * 支付宝订单查询校正
	 * 
	 * @param queryResponse
	 * @param orderForm
	 * @throws Exception
	 */
	public void aliCallback(AliPayQueryResponse queryResponse, OrderForm orderForm, List<OrderForm> orderForms) throws Exception {
		// 更新金额
		Float payment = null;
		if (StringUtils.isNotEmpty(queryResponse.getTotalAmount())) {
			payment = Float.valueOf(queryResponse.getTotalAmount());
		}
		OrderForm t = orderCb(payment, orderForm.getId(), queryResponse.getTradeNo(), queryResponse.getTradeStatus(), null);
		t.setTradeType(Constant.CLIENT_ALI_APP);
		orderFormService.update(t);
		if (queryResponse.getTradeStatus().equals(TradeStatus.ALI_TRADE_SUCCESS.getCode())
				&& !orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode())) {
			// 支付宝交易状态TRADE_SUCCESS，订单状态不是已支付，手动回调
			PayResponse notifyRequest = new PayResponse();
			notifyRequest.setTotalFee(Float.valueOf(queryResponse.getTotalAmount()));
			MyBeanUtils.copyBean2Bean(notifyRequest, queryResponse);
			orderFormBizService.updatePayBusiness(orderForm, notifyRequest, PaymentWay.ALI_PAY.getCode());
			orderForms.add(t);
		} else if (queryResponse.getTradeStatus().equals(TradeStatus.ALI_TRADE_CLOSED.getCode())
				&& (orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()) || orderForm.getStatus().equals(
						OrderStatus.ORDER_STATUS_HAVE_PAID.getCode()))) {
			// 支付宝交易状态TRADE_CLOSED，订单状态不是已退款，手动回调
			AliPayRefundResponse response = new AliPayRefundResponse();
			MyBeanUtils.copyBean2Bean(response, queryResponse);
			response.setRefundFee(queryResponse.getTotalAmount());
			orderFormBizService.updateRefundBusiness(orderForm.getId(), response, PaymentWay.ALI_PAY.getCode());
			orderForms.add(t);
		} else if (queryResponse.getTradeStatus().equals(TradeStatus.ALI_WAIT_BUYER_PAY.getCode())) {
			if (orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode())
					|| orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
				// 支付宝交易状态WAIT_BUYER_PAY，订单状态等于已支付或者已退款，更改为待支付
				updateNotPay(orderForm.getId());
				orderForms.add(t);
			}
		}
	}

	/**
	 * 查询订单校正
	 * 
	 * @param orderForm
	 * @throws Exception
	 */
	public void doUpdateBusiness(OrderForm orderForm, List<OrderForm> orderForms) throws Exception {
		OrderForm t = orderFormService.get(orderForm.getId());
		Object responseObject = queryBizService.orderquery(orderForm.getId());
		if (responseObject != null) {
			if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
				WechatPayQueryResponse response = (WechatPayQueryResponse) responseObject;
				if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
					wxCallback(response, orderForm, orderForms);
				} else {
					t.setMerchantId(null);
					t.setTradeState(response.getErrCode());
					orderFormService.update(t);
				}
			} else if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
				AliPayQueryResponse response = (AliPayQueryResponse) responseObject;
				if (response.getCode().equals(Constant.ALI_SUCCESS_CODE)) {
					aliCallback(response, orderForm, orderForms);
				} else {
					t.setTradeState(response.getSubCode());
					t.setMerchantId(null);
					orderFormService.update(t);
				}
			}
		}
	}

	/**
	 * 导出Excel
	 * 
	 * @param exportOrderForms
	 * @param exportPath
	 * @throws IOException
	 */
	public void exportExcel(List<OrderForm> exportOrderForms, String exportPath) throws IOException {
		String date = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
		File file = new File(exportPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String filename = exportPath + File.separator + date + ".xlsx";
		FileOutputStream outputStream = new FileOutputStream(filename);
		Workbook wb = new XSSFWorkbook();
		Sheet sheet0 = wb.createSheet("校正订单导出");
		appendCell(exportOrderForms, sheet0, wb);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		params.put("payment", 0);
		params.put("tradeState", "1");
		Set<String> tradeStatus = new HashSet<String>();
		tradeStatus.add(TradeStatus.WX_ORDERNOTEXIST.getCode());
		tradeStatus.add(TradeStatus.ALI_TRADE_NOT_EXIST.getCode());
		params.put("tradeStatus", tradeStatus);
		List<OrderForm> orderForms = orderFormService.webListPage(new OrderForm(), params, null);

		Sheet sheet1 = wb.createSheet("对账异常订单导出");
		appendCell(orderForms, sheet1, wb);
		wb.write(outputStream);
	}

	public void appendCell(List<OrderForm> exportOrderForms, Sheet sheet, Workbook wb) {
		String[] titles = new String[] { "订单编号", "交易单号", "商户号", "订单名称", "下单者", "订单类型", "订单金额", "订单状态", "交易状态", "支付方式", "下单时间" };
		Row titleRow = sheet.createRow(0);
		for (int i = 0; i < titles.length; i++) {
			Cell cell = titleRow.createCell(i);
			cell.setCellValue(titles[i]);
		}

		for (int i = 0; i < exportOrderForms.size(); i++) {
			Row row = sheet.createRow(i + 1);
			OrderForm orderForm = exportOrderForms.get(i);
			Cell c0 = row.createCell(0);
			c0.setCellValue(orderForm.getId());
			Cell c1 = row.createCell(1);
			c1.setCellValue(orderForm.getTransactionId());
			Cell c2 = row.createCell(2);
			c2.setCellValue(orderForm.getMerchantId());
			Cell c3 = row.createCell(3);
			c3.setCellValue(orderForm.getTitle());
			Cell c4 = row.createCell(4);
			c4.setCellValue(orderForm.getMember() != null ? orderForm.getMember().getRealname() : "");
			Cell c5 = row.createCell(5);
			c5.setCellValue(OrderType.getValue(orderForm.getType()));

			CellStyle style1 = wb.createCellStyle();
			DataFormat format = wb.createDataFormat();
			style1.setDataFormat(format.getFormat("0.00"));
			Cell c6 = row.createCell(6);
			c6.setCellValue(orderForm.getPayment());
			c6.setCellStyle(style1);

			Cell c7 = row.createCell(7);
			c7.setCellValue(OrderStatus.getValue(orderForm.getStatus()));
			Cell c8 = row.createCell(8);
			c8.setCellValue(orderForm.getTradeState());
			Cell c9 = row.createCell(9);
			c9.setCellValue(PaymentWay.getValue(orderForm.getPaymentWay()));
			Cell c10 = row.createCell(10);
			c10.setCellValue(DateUtils.formatDate(orderForm.getCreateDate(), "yyyy-MM-dd HH:mm"));
		}
	}
}
