package com.party.web.web.controller.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.member.MemberBank;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.member.impl.MemberBankService;
import com.party.core.service.wallet.IWithdrawalService;
import com.party.web.biz.order.OrderBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.utils.WithdrawalStatus;
import com.party.web.web.dto.AjaxResult;

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
	private OrderBizService orderBizService;

	@Autowired
	private IWithdrawalService withdrawalService;

	/**
	 * 提现申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "withdrawalForm")
	public ModelAndView withdrawalForm() {
		ModelAndView mv = new ModelAndView("wallet/applyWithdrawal");

		// 余额
		double totalAccount = orderBizService.getTotalAccount(null);
		mv.addObject("totalPayment", totalAccount);

		String memberId = RealmUtils.getCurrentUser().getId();
		// 绑定的银行卡信息
		MemberBank memberBank = memberBankService.findByMemberId(memberId);
		mv.addObject("memberBank", memberBank);
		mv.addObject("from", "withdrawalForm");
		return mv;
	}

	/**
	 * 校验提现金额
	 * 
	 * @param payment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkPayment")
	public AjaxResult checkPayment(double payment) {
		if (payment == 0.0f) {
			return AjaxResult.error("请输入0.01元以上的金额。");
		}
		double totalAccount = orderBizService.getTotalAccount(null);
		totalAccount = BigDecimalUtils.round(totalAccount, 2);

		double sub = BigDecimalUtils.sub(totalAccount, payment);
		if (sub < 0) {
			return AjaxResult.error("您的余额只有" + totalAccount + "元。");
		}
		return AjaxResult.success();
	}

	/**
	 * 保存提现记录
	 * 
	 * @param withdrawal
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Withdrawals withdrawal, double payment) {
		if (withdrawal.getPayment() == 0.0f) {
			return AjaxResult.error("请输入0.01元以上的金额。");
		}

		double totalAccount = orderBizService.getTotalAccount(null);
		totalAccount = BigDecimalUtils.round(totalAccount, 2);

		double sub = BigDecimalUtils.sub(totalAccount, payment);
		if (sub < 0) {
			return AjaxResult.error("您的余额只有" + totalAccount + "元。");
		}

		try {
			if (StringUtils.isEmpty(withdrawal.getId())) {
				withdrawal.setAccountNumber(withdrawal.getAccountNumber().replaceAll(" ", ""));
				String memberId = RealmUtils.getCurrentUser().getId();
				// 绑定的银行卡信息
				MemberBank t = memberBankService.findByMemberId(memberId);
				if (t == null) {
					t = new MemberBank();
					t.setName(withdrawal.getName());
					t.setPhone(withdrawal.getPhone());
					t.setAccountNumber(withdrawal.getAccountNumber());
					t.setBankName(withdrawal.getBankName());
					t.setOpenedPlace(withdrawal.getOpenedPlace());
					t.setMemberId(memberId);
					t.setCreateBy(memberId);
					t.setUpdateBy(memberId);
					memberBankService.insert(t);
				} else {
					MyBeanUtils.copyBeanNotNull2Bean(withdrawal, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
					memberBankService.update(t);
				}
				withdrawal.setCreateBy(memberId);
				withdrawal.setUpdateBy(memberId);
				withdrawal.setStatus(WithdrawalStatus.STATUS_IN_REVIEW.getCode()); // 处理中
				withdrawalService.insert(withdrawal);
			}
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("异常");
		}
	}

	public static void main(String[] args) {
		double d1 = 0.5;
		double d2 = 0.6;

		double sub = BigDecimalUtils.sub(d2, d1);
		System.out.println(sub);
	}
}
