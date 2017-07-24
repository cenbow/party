package com.party.mobile.web.controller.member;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.service.member.impl.MemberActService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.order.Output.ActOrderDetailOutput;
import com.party.mobile.web.dto.order.Output.OrderDetailOutput;
import com.party.mobile.web.dto.order.Output.OrderOutput;
import com.party.common.utils.PartyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 订单控制层
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 18:05 16/11/10
 * @Modified by:
 */
@Controller
@RequestMapping(value = "member/orderForm")
public class OrderFormController {

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;
    
    @Autowired
    private MemberActService memberActService;

    /**
     * 获取订单支付信息
     * @param orderId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderPayInfo")
    @Authorization
    public AjaxResult getOrder(String orderId,HttpServletRequest request)
    {
    	OrderOutput orderOutput = orderBizService.getOrderById(orderId, request);   	
    	return AjaxResult.success(orderOutput);
    }
    /**
     * 我的预定列表（标准商品的订单列表）
     * @param pos 页面位置（0：全部，1：购买成功，2：未支付，3：无效订单）
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listGoodsOrder")
    @Authorization
    public AjaxResult listGoodsOrder(Integer pos, Page page, HttpServletRequest request)
    {
        if (null == pos || pos < 0 || pos > 3)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        //订单筛选条件
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(currentUser.getId());
        orderForm.setType(OrderType.ORDER_NOMAL.getCode());
        orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        Set<Integer> status = new HashSet<Integer>();
        if (0 == pos)
        {
        }
        else if (1 == pos)
        {
        	status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
        }
        else if (2 == pos)
        {
        	status.add(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
        }
        else
        {
        	status.add(OrderStatus.ORDER_STATUS_OTHER.getCode());
        	status.add(OrderStatus.ORDER_STATUS_REFUND.getCode());
        }

        List<OrderOutput> orderOutputList = orderBizService.listOrderOutput(orderForm, page, status);

        return AjaxResult.success(orderOutputList, page);

    }

    /**
     * 我的活动列表（活动订单列表）
     * @param pos 页面位置（0：审核中，1：待支付，2：报名成功，3：其它）
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listActOrder")
    @Authorization
    public AjaxResult listActOrder(Integer pos, Page page, HttpServletRequest request)
    {
        if (null == pos || pos < 0 || pos > 3)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        //订单筛选条件
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(currentUser.getId());
        orderForm.setType(OrderType.ORDER_ACTIVITY.getCode());
        orderForm.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        Set<Integer> status = new HashSet<Integer>();
        if (0 == pos)
        {
        	MemberAct memberAct = new MemberAct();
        	memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        	memberAct.setMemberId(currentUser.getId());
        	memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDITING.getCode());
        	List<OrderOutput> orderOutputList = orderBizService.listOrderOutput(memberAct, page);

            return AjaxResult.success(orderOutputList, page);
        }
        else if (1 == pos)
        {
        	status.add(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
        }
        else if (2 == pos)
        {
        	status.add(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
        }
        else
        {
        	status.add(OrderStatus.ORDER_STATUS_OTHER.getCode());
        	status.add(OrderStatus.ORDER_STATUS_REFUND.getCode());
        }

        List<OrderOutput> orderOutputList = orderBizService.listOrderOutput(orderForm, page, status);

        return AjaxResult.success(orderOutputList, page);

    }
    
    /**
     * 我的活动列表（活动订单列表）
     * @param pos 页面位置（0：全部，1：待支付，2：报名成功，3：其它）
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listActOrder2")
    @Authorization
	public AjaxResult listActOrder2(Integer pos, Page page, HttpServletRequest request) {
		if (null == pos || pos < 0 || pos > 3) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
		}

		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

		// 活动报名筛选条件
		MemberAct memberAct = new MemberAct();
		memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		memberAct.setMemberId(currentUser.getId());
		if (0 == pos) {
			// memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDITING.getCode());
		} else if (1 == pos) {
			memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode());
		} else if (2 == pos) {
			memberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode());
		} else {
			memberAct.setCheckStatus(ActStatus.ACT_STATUS_CANCEL.getCode());
		}

		List<OrderOutput> orderOutputList = orderBizService.listOrderOutput2(memberAct, page);

		return AjaxResult.success(orderOutputList, page);

	}

    /**
     * 获取标准订单详情
     * @param orderId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGoodsOrderDetail")
    @Authorization
    public AjaxResult getGoodsOrderDetail(String orderId, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(orderId))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        OrderDetailOutput output;
        try {
            output = orderBizService.getGoodsOrderDetail(orderId, request);
        } catch(BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

        return AjaxResult.success(output);
    }

    /**
     * 获取活动订单详情
     * @param orderId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActOrderDetail")
    @Authorization
    public AjaxResult getActOrderDetail(String orderId, HttpServletRequest request)
    {
        if (Strings.isNullOrEmpty(orderId))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        ActOrderDetailOutput output;
        try {
            output = orderBizService.getActOrderDetail(orderId, request);
        } catch(BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

        return AjaxResult.success(output);
    }

	/**
	 * 获取活动订单详情
	 * 
	 * @param orderId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getActOrderDetail2")
	@Authorization
	public AjaxResult getActOrderDetail2(String memberActId, String orderId, HttpServletRequest request) {
		try {
			ActOrderDetailOutput output = orderBizService.getActDetailByMemberActId(memberActId, orderId, request);
			return AjaxResult.success(output);
		} catch (BusinessException be) {
			return AjaxResult.error(be.getCode(), be.getMessage());
		}

	}
}
