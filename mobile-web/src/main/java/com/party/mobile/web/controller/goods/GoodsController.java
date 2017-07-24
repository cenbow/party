package com.party.mobile.web.controller.goods;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.party.mobile.web.dto.distributor.input.GetDistributorInput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.common.redis.StringJedis;
import com.party.core.exception.BusinessException;
import com.party.core.exception.OrderException;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.service.city.ICityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.user.IUserService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.goods.GoodsBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.order.MessageOrderBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.goods.GoodsOutput;
import com.party.mobile.web.dto.goods.input.ListInput;
import com.party.mobile.web.dto.goods.output.ListOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.mobile.web.dto.order.Output.OrderOutput;
import com.party.mobile.web.dto.order.input.BookOrderInput;
import com.party.common.utils.PartyCode;
import com.party.pay.service.pay.PayOrderBizService;

/**
 * 标准商品控制层
 * party
 * Created by wei.li
 * on 2016/9/23 0023.
 */
@Controller
@RequestMapping("/goods/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    ICityService cityService;

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    IMemberService memberService;

    @Autowired
    IUserService userService;

    @Autowired
    GoodsBizService goodsBizService;

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    CurrentUserBizService currentUserBizService;
    
	@Autowired
	private PayOrderBizService payOrderBizService;
	
	@Autowired
	private MessageOrderBizService messageOrderBizService;

    /**
     * 查询标准商品列表
     * @param listInput 标准商品查询参数
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult list(ListInput listInput, Page page){
        listInput.setType(GoodsType.GOODS_NOMAL.getCode());//获取标准商品
        List<ListOutput> listOutputList = goodsBizService.list(listInput, page);
        return AjaxResult.success(listOutputList, page);
    }

    /**
     * 标准商品详情
     * @param id 标准商品编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getDetails")
    public AjaxResult getDetails(String id, HttpServletRequest request){

        //数据验证
        if (Strings.isNullOrEmpty(id)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "商品编号不能为空");
        }

        Goods goods = goodsService.get(id);
        if (null == goods){
            return AjaxResult.error(PartyCode.GOODS_UNEXIST, "该商品不存在");
        }

        GoodsOutput goodsOutput = goodsBizService.get(goods, request);
        
        CurrentUser user = currentUserBizService.getCurrentUser(request);
        if (user != null) {
        	// 个人有效订单量
			int total = orderFormService.calculateBuyNum(id, user.getId(), false, OrderType.ORDER_NOMAL.getCode());
        	goodsOutput.setMemberBuyNum(total);
		}
        return AjaxResult.success(goodsOutput);
    }


    /**
     *
     * @param input 输入视图
     * @param request 请求参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "distribution/detail")
    public AjaxResult getDistributionDetail(GetDistributorInput input, HttpServletRequest request ){
        //数据验证
        if (Strings.isNullOrEmpty(input.getTargetId())){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "商品编号不能为空");
        }

        Goods goods = goodsService.get(input.getTargetId());
        if (null == goods){
            return AjaxResult.error(PartyCode.GOODS_UNEXIST, "该商品不存在");
        }

        GoodsOutput goodsOutput = goodsBizService.getDistribution(goods, request, input);

        CurrentUser user = currentUserBizService.getCurrentUser(request);
        if (user != null) {
            // 个人有效订单量
            int total = orderFormService.calculateBuyNum(input.getTargetId(), user.getId(), false, OrderType.ORDER_NOMAL.getCode());
            goodsOutput.setMemberBuyNum(total);
        }
        return AjaxResult.success(goodsOutput);

    }


    /**
     * 标准商品订单
     * @param bookOrderInput 标准商品输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/bookOrder")
    public AjaxResult bookOrder(@Validated BookOrderInput bookOrderInput, BindingResult result, HttpServletRequest request){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //获取登陆用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        MemberOutput memberOutput = null;

        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (!Strings.isNullOrEmpty(currentUser.getOpenId()) && Strings.isNullOrEmpty(currentUser.getLoginName())) {
            //验证手机验证码
            String verifyCode = stringJedis.getValue(bookOrderInput.getPhone());
            if (null == verifyCode || !verifyCode.equals(bookOrderInput.getVerifyCode())){
                return AjaxResult.error(PartyCode.VERIFY_CODE_ERROR, "手机验证码错误");
            }

            //绑定手机号
            memberOutput = memberBizService.bindPhone(bookOrderInput.getPhone(), bookOrderInput.getLinkman(), null, null, null, request);

            //当前登录用户需重新赋值
            currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
        }

        if (bookOrderInput.getType() == null) {
        	//设置订单类型为标准商品订单
        	bookOrderInput.setType(OrderType.ORDER_NOMAL.getCode());
		}
        //设置标准商品订单状态为待支付
        bookOrderInput.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());

        OrderOutput orderOutput = null;
        try {
        	// 个人有效订单量
        	int total = orderFormService.calculateBuyNum(bookOrderInput.getGoodsId(), currentUser.getId(), false, OrderType.ORDER_NOMAL.getCode());
        	// 商品对应的订单量
			Goods goods = goodsService.get(bookOrderInput.getGoodsId());
			if (goods.getLimitNum() != null) {
				if (goods.getLimitNum() == 0) {
					// 已经超出库存
					return AjaxResult.error(PartyCode.GOODS_NUM_MORE,"库存不够");
				}
				if (goods.getMaxBuyNum() != null && total > goods.getMaxBuyNum()) {
					// 已经超出每人限购
					return AjaxResult.error(PartyCode.GOODS_NUM_MEMBER_MORE,"已经超出限购数额");
				}
			}
			if (goods.getLimitNum() == null) {
				if (goods.getMaxBuyNum() != null && total >= goods.getMaxBuyNum()) {
					// 已经超出每人限购
					return AjaxResult.error(PartyCode.GOODS_NUM_MEMBER_MORE,"已经超出每人限购");
				}
			}
    		orderOutput = orderBizService.bookOrder(bookOrderInput, currentUser.getId());
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

        Map<String,Object> map = Maps.newHashMap();
        map.put("order", orderOutput);
        map.put("member", memberOutput);

        return AjaxResult.success(map);
    }

    @ResponseBody
    @RequestMapping(value = "/freeGoodsAcceptNotify")
	public AjaxResult freeGoods(String orderId) {
		try {
			payOrderBizService.updatePayBusiness(orderId);
			messageOrderBizService.send(orderId);
		} catch (OrderException e) {
			return AjaxResult.error(e.getCode(), e.getMessage());
		} catch (Exception e) {
			return AjaxResult.error();
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("orderId", orderId);
		return AjaxResult.success(map);
	}


}
