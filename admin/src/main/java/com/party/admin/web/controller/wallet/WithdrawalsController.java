package com.party.admin.web.controller.wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.party.admin.biz.activity.OrderBizService;
import com.party.admin.biz.wallet.WithdrawalsBizService;
import com.party.core.model.wallet.WithdrawalStatus;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.wallet.WithdrawalOutput;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.member.IMemberService;
import com.party.core.service.member.impl.MemberBankService;
import com.party.core.service.wallet.IWithdrawalService;

/**
 * 提现申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/wallet/withdrawals")
public class WithdrawalsController {

	@Autowired
	private MemberBankService memberBankService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private OrderBizService orderBizService;

	@Autowired
	private IWithdrawalService withdrawalService;

	@Autowired
	private WithdrawalsBizService withdrawalsBizService;

	/**
	 * 提现记录
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("withdrawalList")
	public ModelAndView withdrawalList(Withdrawals withdrawal, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("wallet/withdrawalList");
		page.setLimit(15);
		withdrawal.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);

		Map<Integer, String> checkStatus = Maps.newHashMap();
		checkStatus.put(WithdrawalStatus.STATUS_HAVE_PAID.getCode(), WithdrawalStatus.STATUS_HAVE_PAID.getValue());
		checkStatus.put(WithdrawalStatus.STATUS_IN_REVIEW.getCode(), WithdrawalStatus.STATUS_IN_REVIEW.getValue());
		checkStatus.put(WithdrawalStatus.STATUS_AUDIT_REJECT.getCode(), WithdrawalStatus.STATUS_AUDIT_REJECT.getValue());
		mv.addObject("checkStatus", checkStatus);

		List<Withdrawals> withdrawals = withdrawalService.webListPage(withdrawal, params, page);
		List<WithdrawalOutput> channelOutputs = LangUtils.transform(withdrawals, input -> {
			WithdrawalOutput output = WithdrawalOutput.transform(input);

			String account = input.getAccountNumber();
			account = withdrawalsBizService.formatAccountNumber(account);
			output.setAccountNumber(account);

			// 手续费
			double serviceFee = BigDecimalUtils.mul(input.getPayment(), 0.006);
			serviceFee = BigDecimalUtils.round(serviceFee, 2);
			// 净额
			double netAmount = BigDecimalUtils.sub(input.getPayment(), serviceFee);

			output.setServiceFee(serviceFee);
			output.setNetAmount(netAmount);
			output.setMember(memberService.get(input.getCreateBy()));
			return output;
		});
		mv.addObject("withdrawals", channelOutputs);
		mv.addObject("withdrawal", withdrawal);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 审核
	 * 
	 * @param withdrawal
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verify")
	public AjaxResult verify(Withdrawals withdrawal, HttpServletRequest request) {
		if (StringUtils.isNotEmpty(withdrawal.getId())) {
			Integer status = withdrawal.getStatus();
			Withdrawals entity = withdrawalService.get(withdrawal.getId());
			if (entity == null) {
				return AjaxResult.error("审核失败，状态错误");
			}

			if (status.equals(WithdrawalStatus.STATUS_HAVE_PAID.getCode())) {
				// 通过
				entity.setStatus(status);
				entity.setUpdateDate(new Date());
				withdrawalService.update(entity);
				return new AjaxResult(true, "审核成功");
			} else if (status.equals(WithdrawalStatus.STATUS_AUDIT_REJECT.getCode())) {
				// 拒绝
				entity.setStatus(status);
				withdrawalService.update(entity);
				return new AjaxResult(true, "审核成功");
			}
		}
		return null;
	}

	/**
	 * 提现记录导出
	 * 
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportWithdrawal", method = RequestMethod.POST)
	public String exportWithdrawal(HttpServletRequest request, HttpServletResponse response) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "提现" + sdf.format(new Date()) + ".xlsx";
			Withdrawals withdrawal = new Withdrawals();
			withdrawal.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			List<Withdrawals> withdrawals = withdrawalService.listPage(withdrawal, null);
			List<WithdrawalOutput> withdrawalOutputs = LangUtils.transform(withdrawals, input -> {
				WithdrawalOutput output = WithdrawalOutput.transform(input);
				String account = input.getAccountNumber();
				account = withdrawalsBizService.formatAccountNumber(account);
				output.setAccountNumber(account);

				// 手续费
				double serviceFee = BigDecimalUtils.mul(input.getPayment(), 0.006);
				serviceFee = BigDecimalUtils.round(serviceFee, 2);
				// 净额
				double netAmount = BigDecimalUtils.sub(input.getPayment(), serviceFee);

				output.setServiceFee(serviceFee);
				output.setNetAmount(netAmount);

				String value = WithdrawalStatus.getValue(input.getStatus());
				output.setStatus2(value);

				return output;
			});
			new ExportExcel("", WithdrawalOutput.class).setDataList(withdrawalOutputs).write(response, fileName).dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
