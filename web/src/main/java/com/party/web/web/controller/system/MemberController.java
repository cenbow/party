package com.party.web.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderType;
import com.party.core.model.wallet.Withdrawals;
import com.party.core.service.order.IOrderFormService;
import com.party.web.biz.order.OrderBizService;
import com.party.web.biz.wallet.WithdrawalsBizService;
import com.party.web.web.dto.output.order.OrderFormOutput;
import com.party.web.web.dto.output.wallet.WithdrawalOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.party.core.model.city.Area;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberMerchant;
import com.party.core.service.city.IAreaService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.IMemberService;
import com.party.web.biz.system.member.MemberBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;

/**
 * 会员控制器 Created by wei.li
 *
 * @date 2017/3/14 0014
 * @time 17:52
 */

@Controller
@RequestMapping(value = "/system/member")
public class MemberController {

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IIndustryService industryService;

	@Autowired
	private MemberBizService memberBizService;

	@Autowired
	private IAreaService areaService;
	
	@Autowired
	private IMemberMerchantService memberMerchantService;
	@Autowired
	private OrderBizService orderBizService;
	@Autowired
	private WithdrawalsBizService withdrawalsBizService;
	@Autowired
	private IOrderFormService orderFormService;

	protected static Logger logger = LoggerFactory.getLogger(MemberController.class);

	/**
	 * 查看会员信息
	 * 
	 * @param id
	 *            会员编号
	 * @return 会员信息
	 */
	@RequestMapping(value = "memberView")
	public ModelAndView memberView(String id) {
		ModelAndView modelAndView = new ModelAndView("system/member/memberInfo");

		Member member = null;
		try {
			member = memberService.get(id);
			if (!Strings.isNullOrEmpty(member.getIndustry())) {
				Industry industry = industryService.get(member.getIndustry());
				modelAndView.addObject("industry", industry);
			}
			if (!Strings.isNullOrEmpty(member.getCity())) {
				Area area = areaService.get(member.getCity());
				modelAndView.addObject("city", area);
			}
		} catch (Exception e) {
			logger.error("查询会员信息异常", e);
		}
		modelAndView.addObject("member", member);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping("save")
	public AjaxResult save(Member member) {
		try {
			member = memberBizService.saveBiz(member);
		} catch (Exception e) {
			logger.info("用户保存异常", e);
			return new AjaxResult(false);
		}
		return new AjaxResult(true);
	}

	/**
	 * 修改密码
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatePwd")
	public AjaxResult updatePwd(Member member) {
		try {
			if (StringUtils.isNotEmpty(member.getId())) {
				Member t = memberService.get(member.getId());

				if (StringUtils.isNotEmpty(member.getPassword())) {
					String encryptPassword = RealmUtils.encryptPassword(member.getPassword());
					t.setPassword(encryptPassword);
					memberService.update(t);
				}
			}
		} catch (Exception e) {
			logger.info("修改密码异常", e);
			return new AjaxResult(false);
		}
		return new AjaxResult(true);
	}
	
	/**
	 * 绑定手机号码
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping("bindPhone")
	public AjaxResult bindPhone(Member member) {
		try {
			if (StringUtils.isNotEmpty(member.getId())) {
				Member t = memberService.get(member.getId());

				if (StringUtils.isNotEmpty(member.getMobile())) {
					t.setMobile(member.getMobile());
					memberService.update(t);
				}
			}
		} catch (Exception e) {
			logger.info("绑定手机异常", e);
			return new AjaxResult(false);
		}
		return new AjaxResult(true);
	}

	/**
	 * 绑定手机号码
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping("bindMerchant")
	public AjaxResult bindMerchant(MemberMerchant memberMerchant) {
		try {
			if (StringUtils.isNotEmpty(memberMerchant.getId())) {
				MemberMerchant t = memberMerchantService.get(memberMerchant.getId());
				MyBeanUtils.copyBeanNotNull2Bean(memberMerchant, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
				memberMerchantService.update(t);
			} else {
				String memberId = RealmUtils.getCurrentUser().getId();
				memberMerchant.setCreateBy(memberId);
				memberMerchant.setUpdateBy(memberId);
				memberMerchantService.insert(memberMerchant);
			}
		} catch (Exception e) {
			logger.info("绑定商户信息异常", e);
			return new AjaxResult(false);
		}
		return new AjaxResult(true);
	}
	/**
	 * 新增/编辑
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "memberForm")
	public ModelAndView memberForm() {
		ModelAndView mv = new ModelAndView("system/member/memberForm");
		Member member = RealmUtils.getCurrentUser();
		member = memberService.get(member.getId());
		mv.addObject("member", member);

		if (StringUtils.isNotEmpty(member.getIndustry())) {
			Industry industry = industryService.get(member.getIndustry());
			mv.addObject("inParent", industry.getParentId());
		}

		if (StringUtils.isNotEmpty(member.getCity())) {
			Area area = areaService.get(member.getCity());
			mv.addObject("arParent", area.getParentId());
		}
		
		MemberMerchant memberMerchant = memberMerchantService.findByMemberId(member.getId());
		mv.addObject("memberMerchant", memberMerchant);

		Industry industry = new Industry();
		industry.setParentId("0");
		List<Industry> industries = industryService.list(industry);
		mv.addObject("industries", industries);

		Area area = new Area();
		area.setParentId("1");
		List<Area> areas = areaService.list(area);
		mv.addObject("areas", areas);
		return mv;
	}

	/**
	 * 前端行业分类联动
	 * 
	 * @param cityId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getIndustryByParentId")
	public List<Industry> getIndustryByParentId(String industryId) {
		Industry industry = new Industry();
		industry.setParentId(industryId);
		List<Industry> industries = industryService.list(industry);
		return industries;
	}

	/**
	 * 前端城市联动
	 * 
	 * @param cityId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCityByParentId")
	public List<Area> getCityByParentId(String cityId) {
		Area area = new Area();
		area.setParentId(cityId);
		List<Area> areas = areaService.list(area);
		return areas;
	}

	/**
	 * 验证用户名或者手机号
	 * 
	 * @param property
	 * @param userId
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkUniqueProperty")
	public boolean checkUniqueProperty(String property, String userId, String type) {
		List<Member> members = new ArrayList<Member>();
		if (StringUtils.isEmpty(userId)) {
			members = memberService.checkUserName(property, "", type);
		} else {
			members = memberService.checkUserName(property, userId, type);
		}
		if (members.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 验证用户密码
	 * 
	 * @param property
	 * @param userId
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkPassword")
	public boolean checkUniqueProperty(String password, String userId) {
		Member member = memberService.get(userId);
		return RealmUtils.validatePassword(password, member.getPassword());
	}
	
	/**
	 * 根据用户手机或用户名查询
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findMemberByPhoneOrName")
	public AjaxResult bindMerchant(String property, String value) {
		try {
			List<Map<String,Object>> list = memberService.findMemberByPhoneOrName(property, value);
			return AjaxResult.success(list);
		} catch (Exception e) {
			logger.info("根据用户手机或用户名查询失败", e);
			return new AjaxResult(false);
		}
	}
	
	@ResponseBody
	@RequestMapping("getMember")
	public AjaxResult getMember(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Member member = memberService.get(id);
		String inParent = "";
		if (StringUtils.isNotEmpty(member.getIndustry())) {
			Industry industry = industryService.get(member.getIndustry());
			inParent = industry.getParentId();
		}

		String arParent = "";
		if (StringUtils.isNotEmpty(member.getCity())) {
			Area area = areaService.get(member.getCity());
			arParent = area.getParentId();
		}
		
		map.put("inParent", inParent);
		map.put("arParent", arParent);
		map.put("member", member);
		return AjaxResult.success(map);
	}

	@RequestMapping("memberIndex")
	public String memberIndex(){
		return "redirect:/system/member/tradeList.do";
	}


	@RequestMapping("tradeList")
	public ModelAndView tradeList(Page page, OrderForm orderForm) {
		ModelAndView mv = new ModelAndView("system/member/tradeList");
		page.setLimit(10);
		orderForm.setMemberId(RealmUtils.getCurrentUser().getId());
		orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isCrowdfund", "0");
		List<OrderForm> orderForms = orderFormService.webListPage(orderForm, params, page);
		List<OrderFormOutput> orderFormOutputs = LangUtils.transform(orderForms, input -> {
			OrderFormOutput orderFormOutput = OrderFormOutput.transform(input);
			String label = OrderType.getValue(input.getType());
			orderFormOutput.setTypeName(label);
			return orderFormOutput;
		});
		mv.addObject("orderForms", orderFormOutputs);

		memberIndexCommon(mv);

		Map<Integer, String> orderTypes = Maps.newHashMap();
		orderTypes.put(OrderType.ORDER_ACTIVITY.getCode(), OrderType.ORDER_ACTIVITY.getValue());
		orderTypes.put(OrderType.ORDER_CROWD_FUND.getCode(), OrderType.ORDER_CROWD_FUND.getValue());
		orderTypes.put(OrderType.ORDER_LEVEL.getCode(), OrderType.ORDER_LEVEL.getValue());
		mv.addObject("orderTypes", orderTypes);
		return mv;
	}

	/**
	 * 个人中心 收益明细
	 * @return
	 */
	@RequestMapping("orderList")
	public ModelAndView orderList(Page page) {
		ModelAndView mv = new ModelAndView("system/member/orderList");
		page.setLimit(10);
		List<OrderFormOutput> orderFormOutputs = orderBizService.memberOrderList(page);
		mv.addObject("orderForms", orderFormOutputs);
		mv.addObject("page", page);

		// 订单总额
		Double orderTotal = orderBizService.getOrderTotal(false);
		if (orderTotal != null) {
			mv.addObject("orderTotal", orderTotal);
		} else {
			mv.addObject("orderTotal", 0);
		}

		memberIndexCommon(mv);
		return mv;
	}

	/**
	 * 个人中心 提现明细
	 * @param page
	 * @return
	 */
	@RequestMapping("withdrawList")
	public ModelAndView withdrawList(Page page) {
		ModelAndView mv = new ModelAndView("system/member/withdrawList");
		page.setLimit(10);
		List<WithdrawalOutput> withdrawalOutputs = withdrawalsBizService.withdrawList(page);
		mv.addObject("withdrawals", withdrawalOutputs);
		mv.addObject("page", page);

		// 提现总额
		Double withdrawalTotal = orderBizService.getWithdrawalTotal();
		if (withdrawalTotal != null) {
			mv.addObject("withdrawalTotal", withdrawalTotal);
		} else {
			mv.addObject("withdrawalTotal", 0);
		}

		memberIndexCommon(mv);
		return mv;
	}

	private void memberIndexCommon(ModelAndView mv){
		String memberId = RealmUtils.getCurrentUser().getId();
		Member member = memberService.get(memberId);
		mv.addObject("member", member);
		// 余额
		double totalAccount = orderBizService.getTotalAccount();
		mv.addObject("totalPayment", totalAccount);
	}
}
