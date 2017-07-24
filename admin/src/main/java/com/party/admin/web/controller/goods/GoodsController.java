package com.party.admin.web.controller.goods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.biz.goods.GoodsBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.core.model.BaseModel;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsDetail;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.city.ICityService;
import com.party.core.service.goods.ICategoryService;
import com.party.core.service.goods.IGoodsDetailService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.thirdParty.IThirdPartyService;

@Controller
@RequestMapping("/goods/goods")
public class GoodsController {

	@Autowired
	private FileBizService fileBizService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private ICityService cityService;

	@Autowired
	private IGoodsDetailService goodsDetailService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IThirdPartyService thirdPartyService;

	@Autowired
	private GoodsBizService goodsBizService;

	protected static Logger logger = LoggerFactory.getLogger(GoodsController.class);

	/**
	 * 新增/编辑标准商品表单页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("bzGoodsForm")
	public ModelAndView bzGoodsForm(String id) {
		ModelAndView mv = new ModelAndView("goods/bzGoodsForm");
		try {
			goodsBizService.goodsForm(id, mv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 新增/编辑定制商品表单页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("dzGoodsForm")
	public ModelAndView dzGoodsForm(String id) {
		ModelAndView mv = new ModelAndView("goods/dzGoodsForm");
		try {
			goodsBizService.goodsForm(id, mv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("goodsDetail")
	public ModelAndView goodsDetail(String id) {
		ModelAndView mv = new ModelAndView("goods/goodsDetail");
		try {
			Goods goods = goodsService.get(id);
			if (StringUtils.isNotEmpty(goods.getNotice())) {
				goods.setNotice(com.party.common.utils.StringUtils.stringtohtml(goods.getNotice()));
			}
			if (StringUtils.isNotEmpty(goods.getRecommend())) {
				goods.setRecommend(com.party.common.utils.StringUtils.stringtohtml(goods.getRecommend()));
			}
			GoodsDetail goodsDetail = goodsDetailService.getByRefId(id);
			if (StringUtils.isNotEmpty(goodsDetail.getContent())) {
				goodsDetail.setContent(com.party.common.utils.StringUtils.stringtohtml(goodsDetail.getContent()));
			}
			Set<Integer> status = new HashSet<Integer>();
			status.add(OrderStatus.ORDER_STATUS_OTHER.getCode()); // 其他
			int joinNum = orderFormService.calculateBuyNum(goods.getId(), null, false, OrderType.ORDER_NOMAL.getCode(), status);
			goods.setJoinNum(joinNum);
			mv.addObject("goodsDetail", goodsDetail);
			mv.addObject("goods", goods);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 获取供应商
	 * 
	 * @param thirdParty
	 * @param page
	 * @return
	 */
	@RequestMapping("selectThirdParty")
	public ModelAndView selectThirdParty(ThirdParty thirdParty, Page page) {
		ModelAndView mv = new ModelAndView("goods/selectThirdParty");
		thirdParty.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<ThirdParty> thirdParties = thirdPartyService.listPage(thirdParty, page);
		mv.addObject("thirdParties", thirdParties);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @param goods
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("save")
	public AjaxResult save(Goods goods, GoodsDetail goodsDetail, String areaSelect, String areaInput, BindingResult result, HttpServletRequest request) throws Exception {
		try {
			// 数据验证
			if (result.hasErrors()) {
				List<ObjectError> allErros = result.getAllErrors();
				return new AjaxResult(false, allErros.get(0).getDefaultMessage());
			}
			if (StringUtils.isNotEmpty(areaInput)) {
				goods.setArea(areaInput);
			}
			if (StringUtils.isNotEmpty(areaSelect)) {
				goods.setArea(areaSelect);
			}
			goodsBizService.saveGoodsBiz(goods, goodsDetail, request);
			return new AjaxResult(true);
		} catch (Exception e) {
			logger.info("商品保存异常", e);
		}
		return new AjaxResult(false);
	}

	/**
	 * 标准商品管理
	 * 
	 * @param goods
	 * @param page
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "bzGoodsList")
	public ModelAndView bzGoodsList(Goods goods, Page page, CommonInput commonInput, String startDate,
			String endDate) {
		ModelAndView mv = new ModelAndView("goods/bzGoodsList");
		goods.setType(GoodsType.GOODS_NOMAL.getCode());// 标准商品
		String path = RealmUtils.getCurrentUser().getId() + "/bzGoods/";
		String content = "order/custom_made_detail.html?id=";
		goodsBizService.commonList(goods, page, commonInput, mv, path, content, startDate, endDate);
		return mv;
	}

	/**
	 * 定制商品管理
	 * 
	 * @param goods
	 * @param page
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @param memberName
	 * @return
	 */
	@RequestMapping(value = "dzGoodsList")
	public ModelAndView dzGoodsList(Goods goods, Page page, CommonInput commonInput, String startDate,
			String endDate) {
		ModelAndView mv = new ModelAndView("goods/dzGoodsList");
		goods.setType(GoodsType.GOODS_CUSTOMIZED.getCode());// 定制商品
		String path = RealmUtils.getCurrentUser().getId() + "/dzGoods/";
		String content = "order/order_dz_detail.html?id=";
		goodsBizService.commonList(goods, page, commonInput, mv, path, content, startDate, endDate);
		return mv;
	}

	/**
	 * 审核
	 * 
	 * @param id
	 * @param checkStatus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "verify")
	public AjaxResult verify(String id, String checkStatus) {
		try {
			if (StringUtils.isEmpty(id)) {
				return new AjaxResult(false);
			}
			Goods entity = goodsService.get(id);
			if (entity != null) {
				entity.setCheckStatus(checkStatus);
				goodsService.update(entity);
			}
			return new AjaxResult(true);
		} catch (Exception e) {
			logger.info("商品审核异常", e);
		}
		return new AjaxResult(false);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String id) {
		try {
			if (StringUtils.isEmpty(id)) {
				return new AjaxResult(false);
			}

			OrderForm t = new OrderForm();
			t.setGoodsId(id);

			Set<Integer> status = new HashSet<Integer>();
			status.add(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
			status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
			status.add(OrderStatus.ORDER_STATUS_REFUND.getCode());

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			List<OrderForm> orderForms = orderFormService.webListPage(t, params, null);
			// 没有人报名可以直接删除
			if (orderForms.size() == 0) {
				goodsService.delete(id);
				return new AjaxResult(true);
			} else {
				return new AjaxResult(false, "已有人购买不能删除");
			}

		} catch (Exception e) {
			logger.info("商品删除异常", e);
		}
		return new AjaxResult(false);
	}

}
