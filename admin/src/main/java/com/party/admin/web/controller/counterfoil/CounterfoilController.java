package com.party.admin.web.controller.counterfoil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.party.admin.web.dto.AjaxResult;
import com.party.common.constant.Constant;
import com.party.common.utils.StringUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.model.counterfoil.CounterfoilBusiness;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.counterfoil.ICounterfoilBusinessService;
import com.party.core.service.counterfoil.ICounterfoilService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;

/**
 * 票据
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/counterfoil/counterfoil")
public class CounterfoilController {

	@Autowired
	IActivityService activityService;
	@Autowired
	ICounterfoilService counterfoilService;
	@Autowired
	ICounterfoilBusinessService counterfoilBusinessService;
	@Autowired
	IMemberActService memberActService;
	@Autowired
	IOrderFormService orderFormService;

	/**
	 * 迁移票据数据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCounterfoil")
	public AjaxResult updateCounterfoil() {
		List<Activity> activities = activityService.list(new Activity());
		for (Activity activity : activities) {
			Float price = activity.getPrice();
			String name = price + "";

			Counterfoil search = new Counterfoil();
			search.setName(name);
			search.setBusinessId(activity.getId());
			search.setBusinessType(Constant.BUSINESS_TYPE_ACTIVITY);
			Counterfoil counterfoil = counterfoilService.getUnique(search);
			if (counterfoil == null) {
				counterfoil = new Counterfoil();
			}
			counterfoil.setPayment(price);
			counterfoil.setLimitNum(activity.getLimitNum() == null ? 0 : activity.getLimitNum());
			counterfoil.setBusinessId(activity.getId());
			counterfoil.setMaxBuyNum(1);
			counterfoil.setBusinessType(Constant.BUSINESS_TYPE_ACTIVITY);
			counterfoil.setName(name);

			if (StringUtils.isNotEmpty(counterfoil.getId())) {
				counterfoilService.update(counterfoil);
			} else {
				counterfoilService.insert(counterfoil);
			}
		}
		return AjaxResult.success();
	}

	/**
	 * 迁移票据购买数据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCounterfoilBusiness")
	public AjaxResult updateCounterfoilBusiness() {
		List<MemberAct> memberActs = memberActService.list(new MemberAct());
		for (MemberAct memberAct : memberActs) {
			Counterfoil search = new Counterfoil();
			search.setBusinessId(memberAct.getActId());
			search.setBusinessType(Constant.BUSINESS_TYPE_ACTIVITY);

			Float payment = memberAct.getPayment();
			if (StringUtils.isNotEmpty(memberAct.getOrderId())) {
				OrderForm orderForm = orderFormService.get(memberAct.getOrderId());
				if (orderForm != null) {
					payment = orderForm.getPayment();
				}
			}
			String name = payment + "";
			search.setName(name);
			Counterfoil counterfoil = counterfoilService.getUnique(search);
			if (counterfoil == null) {
				counterfoil = new Counterfoil();
				counterfoil.setPayment(payment);
				counterfoil.setLimitNum(0);
				counterfoil.setBusinessId(memberAct.getActId());
				counterfoil.setMaxBuyNum(1);
				counterfoil.setBusinessType(Constant.BUSINESS_TYPE_ACTIVITY);
				counterfoil.setName(name);
				counterfoilService.insert(counterfoil);
			}

			CounterfoilBusiness business = counterfoilBusinessService.getUnique(new CounterfoilBusiness(counterfoil.getId(), memberAct.getId()));
			if (business == null) {
				business = new CounterfoilBusiness();
				business.setCounterfoilId(counterfoil.getId());
				business.setBusinessId(memberAct.getId());
				business.setCreateBy(memberAct.getMemberId());
				counterfoilBusinessService.insert(business);
			}
		}
		return AjaxResult.success();
	}
}
