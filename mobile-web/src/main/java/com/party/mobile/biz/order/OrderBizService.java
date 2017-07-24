package com.party.mobile.biz.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.LangUtils;
import com.party.common.utils.PartyCode;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.exception.OrderException;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.city.City;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentState;
import com.party.core.model.order.PaymentWay;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.city.ICityService;
import com.party.core.service.count.IDataCountService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.message.IMessageService;
import com.party.core.service.message.IMessageSetService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.order.IOrderTradeService;
import com.party.core.service.store.IStoreGoodsService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.mobile.biz.crowdfund.ProjectBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.activity.input.ApplyInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.order.Output.ActOrderDetailOutput;
import com.party.mobile.web.dto.order.Output.OrderDetailOutput;
import com.party.mobile.web.dto.order.Output.OrderOutput;
import com.party.mobile.web.dto.order.input.BookOrderInput;
import com.party.pay.service.pay.PayOrderBizService;

/**
 * 订单逻辑业务服务
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */

@Service
public class OrderBizService {

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    IOrderTradeService orderTradeService;

    @Autowired
    IGoodsCouponsService goodsCouponsService;

    @Autowired
    MemberService memberService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IMessageService messageService;

    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    IMemberActService memberActService;

    @Autowired
    IActivityService activityService;

    @Autowired
    IThirdPartyService thirdPartyService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    ICityService cityService;

    @Autowired
    IMessageSetService messageSetService;

    @Autowired
    ProjectBizService projectBizService;

    @Autowired
    IProjectService projectService;

    @Autowired
    ISupportService supportService;

    @Autowired
    ITargetProjectService targetProjectService;

    @Autowired
    MessageOrderBizService messageOrderBizService;

	// 预约成功短信模板
	@Value("#{sms['success.member.template']}")
	private String memberTemplate;

    @Value("#{sms['success.member.crowdfund.template']}")
    private String cowdfundTemplate;

	// 预约成功短信模板
	@Value("#{sms['success.third.template']}")
	private String thirdTemplate;

    @Autowired
    private IDataCountService dataCountService;

    @Autowired
    private OrderTradeBizService orderTradeBizService;
    
	@Autowired
	private PayOrderBizService payOrderBizService;

	protected static Logger logger = LoggerFactory.getLogger(OrderBizService.class);

    private static final int SUPPORT_IS_PAY = 1;


    public OrderOutput getOrderById(String orderId, HttpServletRequest request){
    	OrderForm orderForm = orderFormService.get(orderId);
        //验证标准商品订单数据
        if (null == orderForm)
        {
            throw new BusinessException(PartyCode.ORDER_FORM_NOT_EXIT, "订单不存在");
        }

        //验证标准商品订单是否属于当前登录用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (!currentUser.getId().equals(orderForm.getMemberId()))
        {
            throw new BusinessException(PartyCode.ORDER_FORM_DATA_ERROR, "订单数据错误，下单人与当前用户不是同一人");
        }
        OrderOutput orderOutput = OrderOutput.transform(orderForm);
        if(orderForm.getType() == OrderType.ORDER_ACTIVITY.getCode()){
        	Activity act = activityService.get(orderForm.getGoodsId());
        	orderOutput.setDescription(act.getTitle());
        }else if(orderForm.getType() == OrderType.ORDER_NOMAL.getCode()){
        	Goods goods = goodsService.get(orderForm.getGoodsId());
        	orderOutput.setDescription(goods.getTitle());
        }       
        return orderOutput;
    }
    
    /**
     * 同行者下单
     * @param orderForm 订单信息
     * @param object 支付信息
     * @param paymentWay 支付方式
     * @param
     * @throws OrderException 订单异常
     */
    @Transactional
	public void payOrder(OrderForm orderForm, Object object, Integer paymentWay) throws OrderException {

		// 修改订单状态
		orderForm.setIsPay(PaymentState.IS_PAY.getCode());
		orderForm.setPaymentWay(paymentWay);
		orderForm.setStatus(OrderStatus.ORDER_STATUS_HAVE_PAID.getCode());
		orderFormService.update(orderForm);
		
		// 如果是活动支付成功，设置活动报名状态
        if (orderForm.getType().equals(OrderType.ORDER_ACTIVITY.getCode())) {
            MemberAct dbMemberAct = memberActService.findByOrderId(orderForm.getId());
            if (dbMemberAct != null) {
            	dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode()); // 已支付，报名成功
            	memberActService.update(dbMemberAct);
			}
        }

		//持久化交易信息
        orderTradeBizService.save(orderForm, object, paymentWay);

        //如果是众筹订单
        if (orderForm.getType().equals(OrderType.ORDER_CROWD_FUND.getCode())){
            this.paySupport(orderForm);
            return;
        }

        //发码
        sendCode(orderForm);

        //统计商品销售额
        dataCountService.countSales(orderForm.getGoodsId(), orderForm.getPayment());
	}

    /**
     * 订单发码
     * @param orderForm 订单信息
     */
	public void sendCode(OrderForm orderForm) {

		List<GoodsCoupons> goodsCouponss = goodsCouponsService.findByOrderId(orderForm.getId());
		if (goodsCouponss.size() == 0) {
			List<String> verifyCodeList = payOrderBizService.getVerifyCode(orderForm.getId(), orderForm.getUnit());
			for (String verifyCode : verifyCodeList) {
				
				//生成核销码
				GoodsCoupons goodsCoupons = new GoodsCoupons();
				goodsCoupons.setOrderId(orderForm.getId());
				goodsCoupons.setMemberId(orderForm.getMemberId());
				goodsCoupons.setGoodsId(orderForm.getGoodsId());
				goodsCoupons.setVerifyCode(verifyCode);
				goodsCoupons.setType(orderForm.getType());
				goodsCouponsService.insert(goodsCoupons);
			}
		}
		// 发送推送消息
        try {
            messageOrderBizService.send(orderForm.getId());
        } catch (Exception e) {
            logger.error("下单消息推送异常", e);
        }
	}

    /**
     * 订单预定
     * @param input 输入视图
     * @param memberId 会员主键
     * @return 订单信息
     */
    @Transactional
    public OrderDetailOutput bookOrder(BookOrderInput input, String memberId) throws BusinessException
    {
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(memberId);
        Goods goods = goodsService.get(input.getGoodsId());
        if (null == goods)
        {
            throw new BusinessException(PartyCode.GOODS_UNEXIST, "商品不存在");
        }
        
        orderForm.setGoodsId(input.getGoodsId());
        orderForm.setUnit(input.getUnit());
        orderForm.setLinkman(input.getLinkman());
        orderForm.setPhone(input.getPhone());
        orderForm.setType(input.getType());
        orderForm.setStatus(input.getStatus());
        orderForm.setIsPay(PaymentState.NO_PAY.getCode());
        orderForm.setRemarks(input.getRemarks());
        orderForm.setTitle(goods.getTitle());
        //如果是分享商品 统计
        if (!Strings.isNullOrEmpty(input.getStoreGoodsId())){
            orderForm.setStoreGoodsId(input.getStoreGoodsId());
        }
        double payment =  BigDecimalUtils.mul(goods.getPrice(), input.getUnit());
        double payment2 = BigDecimalUtils.round(payment, 2);
        orderForm.setPayment((float) payment2);
        orderForm.setInitiatorId(goods.getMemberId());
        orderFormService.insert(orderForm);
        
        if (goods.getLimitNum() != null) {
        	// 库存减下单份数
        	goods.setLimitNum(goods.getLimitNum() - input.getUnit());
        	goodsService.update(goods);
		}

        //输出商品订单信息
        OrderDetailOutput output = OrderDetailOutput.transform(orderForm);

        //设置城市
        City city = cityService.get(goods.getCityId());
        if (null != city) {
            output.setCityName(city.getName());
        }
        output.setAreaName(goods.getArea());
        output.setPlace(goods.getPlace());

        //设置商品信息
        output.setDescription(goods.getTitle());
        output.setLogo(goods.getPicsURL());
        output.setStartTime(goods.getStartTime());
        output.setEndTime(goods.getEndTime());

        //获取活动提供方
        Member member = memberService.get(goods.getMemberId());
        if(null != member){
            output.setThirdPartyName(member.getRealname());
            output.setTelephone(member.getMobile());
        }
        return output;
    }
    
    public BookOrderInput createBookOrder(ApplyInput applyInput) {
		BookOrderInput bookOrderInput = new BookOrderInput();
		bookOrderInput.setType(OrderType.ORDER_ACTIVITY.getCode());
		bookOrderInput.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());// 更改为“待支付”状态
		bookOrderInput.setPayment(applyInput.getPayment());
		bookOrderInput.setGoodsId(applyInput.getId());
		bookOrderInput.setUnit(1);
		bookOrderInput.setLinkman(applyInput.getRealname());
		bookOrderInput.setPhone(applyInput.getMobile());
		bookOrderInput.setStoreGoodsId(applyInput.getStoreGoodsId());
		return bookOrderInput;
	}

    /**
     * 订单预定
     * @param input 输入视图
     * @param memberId 会员主键
     * @return 订单信息
     */
    @Transactional
    public OrderOutput bookActOrder(BookOrderInput input, String memberId) throws BusinessException
    {
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(memberId);
        Activity activity = activityService.get(input.getGoodsId());
        if (null == activity)
        {
            throw new BusinessException(PartyCode.ACTIVITY_UNEXIST, "活动不存在");
        }
        orderForm.setGoodsId(input.getGoodsId());
        orderForm.setUnit(input.getUnit());
        orderForm.setLinkman(input.getLinkman());
        orderForm.setPhone(input.getPhone());
        orderForm.setType(input.getType());
        orderForm.setStatus(input.getStatus());
        orderForm.setIsPay(PaymentState.NO_PAY.getCode());
        //如果是分享商品 统计
        if (!Strings.isNullOrEmpty(input.getStoreGoodsId())){
            orderForm.setStoreGoodsId(input.getStoreGoodsId());
        }
        
        if (input.getPayment() == null) {
        	input.setPayment(activity.getPrice());
		}
        
        double payment =  BigDecimalUtils.mul(input.getPayment(), input.getUnit());
        double payment2 = BigDecimalUtils.round(payment, 2);
		orderForm.setPayment((float) payment2);
        orderForm.setTitle(activity.getTitle());
        orderForm.setInitiatorId(activity.getMember());
        orderFormService.insert(orderForm);

        //输出视图
        OrderOutput orderOutput = OrderOutput.transform(orderForm);
        orderOutput.setDescription(activity.getTitle());
        orderOutput.setLogo(activity.getPic());
        orderOutput.setStartTime(activity.getStartTime());
        orderOutput.setEndTime(activity.getEndTime());

        return orderOutput;
    }
    
    /**
     * 待审核活动列表，取活动报名数据
     * @param memberAct
     * @param page
     * @return
     */
    public List<OrderOutput> listOrderOutput(MemberAct memberAct, Page page) {
    	List<MemberAct> dbList = memberActService.listPage(memberAct, page);
    	if (!CollectionUtils.isEmpty(dbList)){
    		List<OrderOutput> orderOutputList = LangUtils.transform(dbList, input -> {
    			OrderOutput orderOutput = new OrderOutput();
    			orderOutput.setStatus(OrderStatus.ORDER_STATUS_IN_REVIEW.getCode()); // 審核中
    			orderOutput.setUnit(1);
    			orderOutput.setPayment(input.getPayment());
				Activity activity = activityService.get(input.getActId());
				if (null != activity) {
					//活动信息
					orderOutput.setDescription(activity.getTitle());
					orderOutput.setLogo(activity.getPic());
					orderOutput.setStartTime(activity.getStartTime());
					orderOutput.setEndTime(activity.getEndTime());
					orderOutput.setGoodsId(activity.getId());
				}
    			return orderOutput;
    		});
    		return orderOutputList;
    	}
    	return Collections.EMPTY_LIST;
    }

    /**
     * 订单列表
     * @param orderForm 订单条件过滤实体
     * @param page
     * @param status 
     * @return 订单信息
     */
    @Transactional
    public List<OrderOutput> listOrderOutput(OrderForm orderForm, Page page, Set<Integer> status)
    {

        List<OrderForm> dbList = orderFormService.listPage(orderForm, page, status);

        if (!CollectionUtils.isEmpty(dbList)){
            List<OrderOutput> orderOutputList = LangUtils.transform(dbList, input -> {
                OrderOutput orderOutput = OrderOutput.transform(input);

                //设置订单的商品信息或活动信息
                if (input.getType() == OrderType.ORDER_NOMAL.getCode())
                {
                    Goods goods = goodsService.get(input.getGoodsId());
                    if (null != goods)
                    {
                        //商品信息
                        orderOutput.setDescription(goods.getTitle());
                        orderOutput.setLogo(goods.getPicsURL());
                        orderOutput.setStartTime(goods.getStartTime());
                        orderOutput.setEndTime(goods.getEndTime());
                    }
                }
                else if (input.getType() == OrderType.ORDER_ACTIVITY.getCode())
                {
                    Activity activity = activityService.get(input.getGoodsId());
                    if (null != activity)
                    {
                        //活动信息
                        orderOutput.setDescription(activity.getTitle());
                        orderOutput.setLogo(activity.getPic());
                        orderOutput.setStartTime(activity.getStartTime());
                        orderOutput.setEndTime(activity.getEndTime());
                    }
                }
                return orderOutput;
            });
            return orderOutputList;
        }
        return Collections.EMPTY_LIST;

    }


    public OrderDetailOutput getGoodsOrderDetail(String orderId, HttpServletRequest request) throws BusinessException
    {
        OrderForm orderForm = orderFormService.get(orderId);
        //验证标准商品订单数据
        if (null == orderForm)
        {
            throw new BusinessException(PartyCode.ORDER_FORM_NOT_EXIT, "订单不存在");
        }
        else if (orderForm.getType() != OrderType.ORDER_NOMAL.getCode())
        {
            throw new BusinessException(PartyCode.ORDER_FORM_GOODS_NOT_EXIT, "订单数据错误，商品或活动不存在");
        }

        //验证标准商品订单是否属于当前登录用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (!currentUser.getId().equals(orderForm.getMemberId()))
        {
            throw new BusinessException(PartyCode.ORDER_FORM_DATA_ERROR, "订单数据错误，下单人与当前用户不是同一人");
        }

        //验证标准商品订单关联的商品数据是否存在
        Goods goods = goodsService.get(orderForm.getGoodsId());
        if (null == goods)
        {
            throw new BusinessException(PartyCode.ORDER_FORM_GOODS_NOT_EXIT, "订单数据错误，商品或活动不存在");
        }

        //输出标准商品订单信息
        OrderDetailOutput output = OrderDetailOutput.transform(orderForm);

        //设置城市
        City city = cityService.get(goods.getCityId());
        if (null != city) {
            output.setCityName(city.getName());
        }
        output.setAreaName(goods.getArea());
        output.setPlace(goods.getPlace());

        //设置商品信息
        output.setDescription(goods.getTitle());
        output.setLogo(goods.getPicsURL());
        output.setStartTime(goods.getStartTime());
        output.setEndTime(goods.getEndTime());

        //获取活动提供方
        Member member = memberService.get(goods.getMemberId());
        if(null != member){
            output.setThirdPartyName(member.getRealname());
            output.setTelephone(member.getMobile());
        }

        // 已支付
        if (orderForm.getIsPay().equals(PaymentState.IS_PAY.getCode())) {
        	//设置核销码
        	GoodsCoupons goodsCoupons = new GoodsCoupons();
        	goodsCoupons.setGoodsId(goods.getId());
        	goodsCoupons.setOrderId(orderForm.getId());
        	goodsCoupons.setMemberId(currentUser.getId());
        	
        	List<GoodsCoupons> goodsCouponseslist = goodsCouponsService.list(goodsCoupons);
        	
        	List<String> list = new ArrayList<>();
        	for (int i = 0; i < goodsCouponseslist.size(); i++)
        	{
        		list.add(goodsCouponseslist.get(i).getVerifyCode());
        	}
        	output.setConsumerCode(list);
		}

        return output;
    }

    /**
     * 根据订单ID获取活动详情
     * @param orderId 订单ID
     * @param request
     * @return
     * @throws BusinessException
     */
    public ActOrderDetailOutput getActOrderDetail(String orderId, HttpServletRequest request) throws BusinessException
    {
        OrderForm orderForm = orderFormService.get(orderId);
        //验证活动订单数据
        if (null == orderForm)
        {
            throw new BusinessException(PartyCode.ORDER_FORM_NOT_EXIT, "订单不存在");
        }
        else if (orderForm.getType() != OrderType.ORDER_ACTIVITY.getCode())
        {
            throw new BusinessException(PartyCode.ORDER_FORM_GOODS_NOT_EXIT, "订单数据错误，商品或活动不存在");
        }

        //验证活动订单是否属于当前登录用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (!currentUser.getId().equals(orderForm.getMemberId()))
        {
            throw new BusinessException(PartyCode.ORDER_FORM_DATA_ERROR, "订单数据错误，下单人与当前用户不是同一人");
        }

        //验证活动订单关联的商品数据是否存在
        Activity activity = activityService.get(orderForm.getGoodsId());
        if (null == activity)
        {
            throw new BusinessException(PartyCode.ORDER_FORM_GOODS_NOT_EXIT, "订单数据错误，商品或活动不存在");
        }

        //输出活动订单信息
        ActOrderDetailOutput output = ActOrderDetailOutput.transform(orderForm);

        //设置城市
        City city = cityService.get(activity.getCityId());
        if (null != city) {
            output.setCityName(city.getName());
        }
        output.setAreaName(activity.getArea());
        output.setPlace(activity.getPlace());

        //设置活动信息
        output.setDescription(activity.getTitle());
        output.setLogo(activity.getPic());
        output.setStartTime(activity.getStartTime());
        output.setEndTime(activity.getEndTime());
        //设置活动报名信息
        MemberAct dbMemberAct = memberActService.findByMemberAct(currentUser.getId(), activity.getId());
        if (null != dbMemberAct)
        {
            output.setMeberActId(dbMemberAct.getId());
            output.setActStatus(dbMemberAct.getCheckStatus());
//            output.setStatus(dbMemberAct.getCheckStatus());
        }


        //获取活动提供方
        Member member = memberService.get(activity.getMember());
        if(null != member){
            output.setThirdPartyName(member.getRealname());
            output.setTelephone(member.getMobile());
        }

        //设置核销码
        GoodsCoupons goodsCoupons = new GoodsCoupons();
        goodsCoupons.setGoodsId(activity.getId());
        goodsCoupons.setOrderId(orderForm.getId());
        goodsCoupons.setMemberId(currentUser.getId());

        List<GoodsCoupons> goodsCouponseslist = goodsCouponsService.list(goodsCoupons);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < goodsCouponseslist.size(); i++)
        {
            list.add(goodsCouponseslist.get(i).getVerifyCode());
        }
        output.setConsumerCode(list);

        return output;
    }
    
    /**
     * 根据活动报名ID获取活动详情
     * @param memberActId 报名ID
     * @param orderId 订单ID
     * @param request
     * @return
     * @throws BusinessException
     */
	public ActOrderDetailOutput getActDetailByMemberActId(String memberActId, String orderId, HttpServletRequest request) throws BusinessException {
		OrderForm orderForm = orderFormService.get(orderId);
		MemberAct memberAct = memberActService.get(memberActId);
		
		if (orderForm == null && memberAct == null) {
			throw new BusinessException(PartyCode.PARAMETER_ILLEGAL, "参数错误或者数据不存在");
		}
		
		if (StringUtils.isNotEmpty(orderId) && orderForm != null) {
			return getActOrderDetail(orderId, request);
		}
		
		// 验证活动订单是否属于当前登录用户
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

		// 验证活动订单关联的商品数据是否存在
		Activity activity = activityService.get(memberAct.getActId());
		if (null == activity) {
			throw new BusinessException(PartyCode.ORDER_FORM_GOODS_NOT_EXIT, "订单数据错误，商品或活动不存在");
		}
		
		orderForm = new OrderForm();
		orderForm.setStatus(memberAct.getCheckStatus());
		orderForm.setUnit(1);
		orderForm.setPayment(memberAct.getPayment());
		orderForm.setCreateDate(memberAct.getCreateDate());

		// 输出活动订单信息
		ActOrderDetailOutput output = ActOrderDetailOutput.transform(orderForm);

		// 设置城市
		City city = cityService.get(activity.getCityId());
		if (null != city) {
			output.setCityName(city.getName());
		}
		output.setAreaName(activity.getArea());
		output.setPlace(activity.getPlace());

		// 设置活动信息
		output.setDescription(activity.getTitle());
		output.setLogo(activity.getPic());
		output.setStartTime(activity.getStartTime());
		output.setEndTime(activity.getEndTime());
		// 设置活动报名信息
		output.setMeberActId(memberAct.getId());
		output.setActStatus(memberAct.getCheckStatus());
		
		Member member = memberService.get(activity.getMember());
		if (null != member) {
            output.setThirdPartyName(member.getRealname());
            output.setTelephone(member.getMobile());
		}

		// 设置核销码
		GoodsCoupons goodsCoupons = new GoodsCoupons();
		goodsCoupons.setGoodsId(activity.getId());
		goodsCoupons.setMemberId(currentUser.getId());

		List<GoodsCoupons> goodsCouponseslist = goodsCouponsService.list(goodsCoupons);

		List<String> list = new ArrayList<>();
		for (int i = 0; i < goodsCouponseslist.size(); i++) {
			list.add(goodsCouponseslist.get(i).getVerifyCode());
		}
		output.setConsumerCode(list);

		return output;
	}
    
    /**
	 * 处理支付业务
	 * 
	 * @param orderId
	 * @param from 
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void updatePayBusiness(String orderId, String from) throws Exception {
		payOrderBizService.freeOrder(orderId, from);
		payOrderBizService.updateJoinNum(orderId);
	}
	
	/**
	 * 免费活动/商品处理业务
	 * @param type
	 * @param from
	 * @param goodsId
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void updatePayBusiness2(Integer type, String from, String goodsId, String memberId) throws Exception {
		payOrderBizService.freeOrder2(type, from, goodsId, memberId);
	}
	
	/**
	 * 处理支付业务
	 * @param orderForm
	 * @param object
	 * @param paymentWay
	 */
	@Transactional(readOnly = false)
	public void updatePayBusiness(OrderForm orderForm, Object object, Integer paymentWay){

	    //订单支付
		payOrder(orderForm, object, paymentWay);
		payOrderBizService.updateJoinNum(orderForm.getId());
	}


    /**
     * 支持订单支付成功回调
     * @param orderForm 订单信息
     */
    @Transactional
    public void paySupport(OrderForm orderForm){

        //修改支持状态
        Support support = supportService.findByOrderId(orderForm.getId());
        support.setPayStatus(SUPPORT_IS_PAY);
        supportService.update(support);

        //更改众筹
        Project project = projectService.get(support.getProjectId());
        //支持前百分比
        int beforePercent = this.getPercent(project);

        TargetProject targetProject = targetProjectService.findByProjectId(project.getId());
        int favorerNum = project.getFavorerNum() + 1;
        float actualAmount = supportService.sumByProjectId(project.getId());

        //实时已筹集金额
        project.setRealTimeAmount(actualAmount);
        //已筹金额是否大于目标金额
        actualAmount = actualAmount > project.getTargetAmount() ? project.getTargetAmount() : actualAmount;
        project.setFavorerNum(favorerNum);
        project.setActualAmount(actualAmount);

        //更改项目
        Activity activity = activityService.get(targetProject.getTargetId());

        //如果众筹成功
        boolean isSueccess = projectService.isSuccess(project.getTargetAmount(), project.getActualAmount());
        if ((!project.getIsSuccess().equals(Constant.IS_SUCCESS)) && isSueccess){
            //修改众筹主订单
            OrderForm mainOrder = orderFormService.get(targetProject.getOrderId());
            this.payOrder(mainOrder, null, PaymentWay.CROWD_FUND_PAY.getCode());
            project.setIsSuccess(Constant.IS_SUCCESS);

            // 商品正在众筹人数减
            activity.setCrowdfundedNum(activity.getCrowdfundedNum() + 1);
            Integer beCrowdfundNum = activity.getBeCrowdfundNum() -1 <= 0 ? 0 : activity.getBeCrowdfundNum() -1;
            activity.setBeCrowdfundNum(beCrowdfundNum);
        }
        projectService.update(project);

        //支持后百分比
        int afterPercent = this.getPercent(project);

        int actFavorerNum = activity.getFavorerNum() + 1;
        activity.setFavorerNum(actFavorerNum);
        activityService.update(activity);

        try {
            messageOrderBizService.projectSend(project, beforePercent, afterPercent);
            //messageOrderBizService.supportSend(orderForm, project);
            messageOrderBizService.supportWechatSend(orderForm, project);
        } catch (Exception e) {
            logger.error("消息推送异常", e);
        }
    }

	public List<OrderOutput> listOrderOutput2(MemberAct memberAct, Page page) {
		List<MemberAct> dbList = memberActService.listPageTwo(memberAct, page);
    	if (!CollectionUtils.isEmpty(dbList)){
    		List<OrderOutput> orderOutputList = LangUtils.transform(dbList, input -> {
    			OrderOutput orderOutput = new OrderOutput();
    			orderOutput.setId(input.getOrderId());
    			orderOutput.setStatus(input.getCheckStatus());
    			orderOutput.setUnit(1);
    			orderOutput.setPayment(input.getPayment());
				Activity activity = activityService.get(input.getActId());
				if (null != activity) {
					//活动信息
					orderOutput.setDescription(activity.getTitle());
					orderOutput.setLogo(activity.getPic());
					orderOutput.setStartTime(activity.getStartTime());
					orderOutput.setEndTime(activity.getEndTime());
					orderOutput.setGoodsId(activity.getId());
				}
    			return orderOutput;
    		});
    		return orderOutputList;
    	}
    	return Collections.EMPTY_LIST;
	}

    /**
     * 获取众筹百分比
     * @param project 众筹项目
     * @return 百分比
     */
	public int getPercent(Project project){
        //支持前百分比
        double percent = BigDecimalUtils.div(project.getActualAmount(), project.getTargetAmount(), 2);
        int intPercent = BigDecimalUtils.amplify2int(percent, 1);
        return intPercent;
    }
}
