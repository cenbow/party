package com.party.mobile.biz.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.city.City;
import com.party.core.model.count.DataCount;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.model.counterfoil.CounterfoilBusiness;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.order.PaymentWay;
import com.party.core.model.store.StoreGoods;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.city.ICityService;
import com.party.core.service.count.IDataCountService;
import com.party.core.service.counterfoil.ICounterfoilBusinessService;
import com.party.core.service.counterfoil.ICounterfoilService;
import com.party.core.service.goods.IGoodsCouponsService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.store.IStoreGoodsService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.order.MessageOrderBizService;
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.biz.orderquery.QueryBizService;
import com.party.mobile.web.dto.activity.input.ApplyInput;
import com.party.mobile.web.dto.activity.output.ApplyOutput;
import com.party.mobile.web.dto.activity.output.RegisterCertificateOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.order.Output.OrderOutput;
import com.party.mobile.web.dto.order.input.BookOrderInput;
import com.party.pay.model.query.AliPayQueryResponse;
import com.party.pay.model.query.TradeStatus;
import com.party.pay.model.query.WechatPayQueryResponse;
import com.party.pay.service.pay.PayOrderBizService;

/**
 * 活动报名
 * party
 * Created by wei.li
 * on 2016/9/29 0029.
 */

@Service
public class MemberActBizService {
	
	@Autowired
	private PayOrderBizService payOrderBizService;

    @Autowired
    IMemberActService memberActService;

    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    IOrderFormService orderFormService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    IActivityService activityService;

    @Autowired
    IThirdPartyService thirdPartyService;

    @Autowired
    ICityService cityService;

    @Autowired
    IGoodsCouponsService goodsCouponsService;

    @Autowired
    IDataCountService dataCountService;
    
    @Autowired
    MessageOrderBizService messageOrderBizService;
    
    @Autowired
    QueryBizService queryBizService;
    
    @Autowired
    ICounterfoilService counterfoilService;
    
    @Autowired
    ICounterfoilBusinessService counterfoilBusinessService;
    
    protected static Logger logger = LoggerFactory.getLogger(MemberActBizService.class);

    /**
     * 插入活动报名
     * @param memberAct 活动报名
     * @return 活动报名实体
     */
    public String insert(MemberAct memberAct){
        String id = memberActService.insert(memberAct);

        return id;
    }

    /**
     * 活动报名
     * @param input 输入视图
     * @param memberId 会员编号
     * @return 报名输出视图
     */
    @Transactional
    public ApplyOutput apply(ApplyInput input, String memberId) {
        //生成报名数据
        MemberAct memberAct = new MemberAct();
        memberAct.setActId(input.getId());
        memberAct.setMemberId(memberId);
        memberAct.setName(input.getRealname());
        memberAct.setMobile(input.getMobile());
        memberAct.setCompany(input.getCompany());
        memberAct.setJobTitle(input.getTitle());
        memberAct.setExtra(input.getExtra());
        memberAct.setWechatNum(input.getWechatNum());
        Activity activity = activityService.get(input.getId());
        memberAct.setPayment(activity.getPrice());
        memberAct.setUpdateBy(memberId);
        memberAct.setJoinin(YesNoStatus.YES.getCode());//报名
        memberAct.setCollect(YesNoStatus.YES.getCode());
        memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDITING.getCode());

        BaseModel.preByInfo(memberId, memberAct);
        String memberActId = this.insert(memberAct);

        //统计报名量
        DataCount dataCount = dataCountService.findByTargetId(input.getId());
        dataCount.setApplyNum(dataCount.getApplyNum() + 1);
        dataCountService.update(dataCount);

        int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
        activity.setJoinNum(joinNum);
        activityService.update(activity);

        ApplyOutput applyOutput = new ApplyOutput();
        applyOutput.setMemberActId(memberActId);
        applyOutput.setActStatus(memberAct.getCheckStatus());

        return applyOutput;
    }
    
    /**
     * 活动报名
     * @param input 输入视图
     * @param memberId 会员编号
     * @return 报名输出视图
     */
    @Transactional
	public ApplyOutput apply2(ApplyInput input, String memberId) {
		// 生成订单数据
    	BookOrderInput bookOrderInput = orderBizService.createBookOrder(input);
		OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);

		// 生成报名数据
		MemberAct memberAct = new MemberAct();
		memberAct.setActId(input.getId());
		memberAct.setMemberId(memberId);
		memberAct.setName(input.getRealname());
		memberAct.setMobile(input.getMobile());
		memberAct.setCompany(input.getCompany());
		memberAct.setJobTitle(input.getTitle());
		memberAct.setExtra(input.getExtra());
		memberAct.setWechatNum(input.getWechatNum());
		memberAct.setPayment(input.getPayment());
		memberAct.setUpdateBy(memberId);
		memberAct.setJoinin(YesNoStatus.YES.getCode());// 报名
		memberAct.setCollect(YesNoStatus.YES.getCode());
		// 审核状态已审核
		memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode());

		// 将订单Id放入报名表
		memberAct.setOrderId(orderOutput.getId());

		BaseModel.preByInfo(memberId, memberAct);

		String memberActId = this.insert(memberAct);

		// 属于分享连接
		if (!Strings.isNullOrEmpty(input.getStoreGoodsId())) {
			// 统计成交量
			StoreGoods storeGoods = storeGoodsService.get(input.getStoreGoodsId());
			Integer num = storeGoods.getApplyNum();
			storeGoods.setApplyNum(num + 1);
			storeGoodsService.update(storeGoods);
		}
		ApplyOutput applyOutput = new ApplyOutput();
		applyOutput.setMemberActId(memberActId);
		applyOutput.setActStatus(memberAct.getCheckStatus());
		applyOutput.setOrderId(orderOutput.getId());

		return applyOutput;
	}
    
    /**
     * 活动报名
     * @param input 输入视图
     * @param memberId 会员编号
     * @return 报名输出视图
     */
    @Transactional
	public ApplyOutput apply3(ApplyInput input, String memberId) {
    	ApplyOutput applyOutput = new ApplyOutput();
    	
    	// 生成报名数据
		MemberAct memberAct = new MemberAct();
		memberAct.setActId(input.getId());
		memberAct.setMemberId(memberId);
		memberAct.setName(input.getRealname());
		memberAct.setMobile(input.getMobile());
		memberAct.setCompany(input.getCompany());
		memberAct.setJobTitle(input.getTitle());
		memberAct.setExtra(input.getExtra());
		memberAct.setWechatNum(input.getWechatNum());
		memberAct.setPayment(input.getPayment());
		memberAct.setUpdateBy(memberId);
		memberAct.setJoinin(YesNoStatus.YES.getCode());// 报名
		memberAct.setCollect(YesNoStatus.YES.getCode());
		// 审核状态已审核
		memberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode());
		BaseModel.preByInfo(memberId, memberAct);
		memberActService.insert(memberAct);
    	
		// 0元活动不生成订单
		if (input.getPayment() > new Float(0.0)) {
			// 生成订单数据
			BookOrderInput bookOrderInput = orderBizService.createBookOrder(input);
			OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
			// 将订单Id放入报名表
			memberAct.setOrderId(orderOutput.getId());
			memberActService.update(memberAct);
			applyOutput.setOrderId(orderOutput.getId());
		}

		// 属于分享连接
		if (!Strings.isNullOrEmpty(input.getStoreGoodsId())) {
			// 统计成交量
			StoreGoods storeGoods = storeGoodsService.get(input.getStoreGoodsId());
			Integer num = storeGoods.getApplyNum();
			storeGoods.setApplyNum(num + 1);
			storeGoodsService.update(storeGoods);
		}
		
		applyOutput.setMemberActId(memberAct.getId());
		applyOutput.setActStatus(memberAct.getCheckStatus());
		return applyOutput;
	}

    /**
     * 获取活动下单状态
     * @param memberActId 活动报名id
     * @return 活动下单状态
     */
    @Transactional
    public Integer getActStatus( String memberActId){
        Integer actStatus = ActStatus.ACT_STATUS_NO_JOIN.getCode();

        MemberAct dbMemberAct = memberActService.get(memberActId);


//        //获取活动参与状态
//        if (null != dbMemberAct) {
//            if (null == dbMemberAct.getJoinin() || 0 == dbMemberAct.getJoinin()) {
//                actStatus = ActStatus.ACT_STATUS_CANCEL.getCode();//已取消
//            } else {
//                if ("2".equals(dbMemberAct.getCheckStatus())) {
//                    actStatus = ActStatus.ACT_STATUS_AUDIT_REJECT.getCode();//审核未通过
//                } else {
//                    OrderFormController orderForm = orderFormService.get(dbMemberAct.getOrderId());
//
//                    if (null != orderForm) {
//                        if (orderForm.getStatus() == OrderStatus.ORDER_STATUS_IN_REVIEW.getCode())
//                            actStatus = ActStatus.ACT_STATUS_AUDITING.getCode();//审核中
//                        else if (orderForm.getStatus() == OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode())
//                            actStatus = ActStatus.ACT_STATUS_AUDIT_PASS.getCode();//审核通过，待付款
//                        else if (orderForm.getStatus() == OrderStatus.ORDER_STATUS_HAVE_PAID.getCode())
//                            actStatus = ActStatus.ACT_STATUS_AUDIT_PASS.getCode();//已付款，报名成功
//                    }
//                }
//            }
//        }
        if (null != dbMemberAct)
            actStatus = dbMemberAct.getCheckStatus();

        return actStatus;
    }

    /**
     * 获取活动下单状态
     * @param memberId 会员id
     * @param activityId 活动id
     * @return 获取活动下单状态
     */
    @Transactional
    public Integer getActStatus( String memberId, String activityId){
        Integer actStatus = ActStatus.ACT_STATUS_NO_JOIN.getCode();

        MemberAct dbMemberAct = memberActService.findByMemberAct(memberId, activityId);

        if (null != dbMemberAct)
            actStatus = dbMemberAct.getCheckStatus();

        return actStatus;
    }

    /**
     * 取消活动报名
     * @param memberActId 活动报名id
     * @return 报名输出视图
     */
    @Transactional
    public ApplyOutput joinCancel(String memberActId, HttpServletRequest request) throws BusinessException
    {
        ApplyOutput applyOutput = new ApplyOutput();

        MemberAct memberAct = memberActService.get(memberActId);
        if (null != memberAct)
        {
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

            //验证当前用户是否与报名用户为同一个人
            boolean validate = currentUser.getId().equals(memberAct.getMemberId());
            if (!validate)
            {
                throw new BusinessException(PartyCode.JOIN_ACT_MEMBER_ERROR, "取消订单，当前用户与报名用户不是同一个人");
            }

            // 等于空表示这是第一次报名，还未生成订单
            if (StringUtils.isNotEmpty(memberAct.getOrderId())) {
            	//取消报名，修改订单状态为其它
            	OrderForm orderForm = orderFormService.get(memberAct.getOrderId());
            	
            	if (null != orderForm)
            	{
            		if (orderForm.getStatus() == OrderStatus.ORDER_STATUS_HAVE_PAID.getCode())
            		{
            			throw new BusinessException(PartyCode.ORDER_HAVE_PAID, "订单已支付，不能取消");
            		}
            		orderForm.setStatus(OrderStatus.ORDER_STATUS_OTHER.getCode());
            		orderFormService.update(orderForm);
            	}
//            	else
//            	{
//            		throw new BusinessException(PartyCode.JOIN_ACT_MEMBERACT_ERROR, "取消报名失败，订单数据异常");
//            	}
			}

            memberAct.setJoinin(YesNoStatus.NO.getCode());//设置取消报名
            memberAct.setCheckStatus(ActStatus.ACT_STATUS_CANCEL.getCode());//设置取消报名

            applyOutput.setMemberActId(memberAct.getId());
            applyOutput.setActStatus(memberAct.getCheckStatus());

            memberActService.update(memberAct);//更新报名信息
            // 删除中间表数据
            counterfoilBusinessService.deleteByBusinessId(memberActId);
        }
        else
        {
            throw new BusinessException(PartyCode.JOIN_ACT_ORDER_ERROR, "取消报名失败，报名数据异常");
        }


        return applyOutput;
    }

    /**
     * 活动报名凭证业务
     * @param memberActId
     * @param request
     * @return
     * @throws BusinessException 活动报名凭证
     */
    @Transactional
    public RegisterCertificateOutput getRegisterCertification(String memberActId, HttpServletRequest request) throws BusinessException
    {
        RegisterCertificateOutput registerCertificateOutput = null;

        MemberAct memberAct = memberActService.get(memberActId);
        if (null != memberAct)
        {
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

            //验证当前用户是否与报名用户为同一个人
            boolean validate = currentUser.getId().equals(memberAct.getMemberId());
            if (!validate)
            {
                throw new BusinessException(PartyCode.JOIN_ACT_MEMBER_ERROR, "当前用户与报名用户不是同一个人");
            }

            if (memberAct.getCheckStatus() != ActStatus.ACT_STATUS_PAID.getCode())
            {
                throw new BusinessException(PartyCode.JOIN_ACT_STATUS_ERROR, "活动状态错误");
            }

            Activity activity = activityService.get(memberAct.getActId());
            //
            OrderForm orderForm = orderFormService.get(memberAct.getOrderId());
            if (null != orderForm && null != activity)
            {
                registerCertificateOutput = RegisterCertificateOutput.transform(activity);
                //查询城市
                City city = cityService.get(activity.getCityId());
                registerCertificateOutput.setCityName(city.getName());

                //获取活动提供方
                ThirdParty thirdParty = thirdPartyService.get(activity.getThirdPartyId());

                registerCertificateOutput.setThirdPartyName(thirdParty.getComName());
                registerCertificateOutput.setPhone(thirdParty.getTelephone());

                //核销码
                GoodsCoupons goodsCoupons = new GoodsCoupons();
                goodsCoupons.setGoodsId(activity.getId());
                goodsCoupons.setMemberId(currentUser.getId());

                List<GoodsCoupons> goodsCouponseslist = goodsCouponsService.list(goodsCoupons);

                List<String> list = new ArrayList<>();
                for (int i = 0; i < goodsCouponseslist.size(); i++)
                {
                    list.add(goodsCouponseslist.get(i).getVerifyCode());
                }
                registerCertificateOutput.setConsumerCode(list);
            }
            else
            {
                throw new BusinessException(PartyCode.JOIN_ACT_ORDER_ERROR, "活动订单数据异常");
            }
        }
        else
        {
            throw new BusinessException(PartyCode.JOIN_ACT_MEMBERACT_ERROR, "报名数据异常");
        }

        return registerCertificateOutput;
    }

    /**
     * 取出报名数据返回前端
     * @param memberId
     * @param applyInput
     * @return 报名数据
     */
    @Transactional
	public ApplyOutput getApplyData(String memberId, ApplyInput applyInput) throws BusinessException {
		MemberAct dbMemberAct = memberActService.findByMemberAct(memberId, applyInput.getId());
		ApplyOutput applyOutput = new ApplyOutput();
		if (null != dbMemberAct) {
			// 已经报名,取出报名信息
			if (ActStatus.ACT_STATUS_AUDITING.getCode() == dbMemberAct.getCheckStatus()
					|| ActStatus.ACT_STATUS_AUDIT_PASS.getCode() == dbMemberAct.getCheckStatus()
					|| ActStatus.ACT_STATUS_PAID.getCode() == dbMemberAct.getCheckStatus()) {
				throw new BusinessException(PartyCode.JOIN_ACT_STATUS_ERROR, "活动报名状态错误");
			}

			Activity activity = activityService.get(applyInput.getId()); // 对应活动
			if (StringUtils.isNotEmpty(dbMemberAct.getOrderId())) {
				OrderForm orderForm = orderFormService.get(dbMemberAct.getOrderId());
				if (orderForm != null) {
					// 订单状态不等于已退款，更改状态
					if (!orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
						orderForm.setLinkman(applyInput.getRealname());// 联系人
						orderForm.setPhone(applyInput.getMobile());// 手机
						float payment = activity.getPrice() * orderForm.getUnit(); // 金额
						orderForm.setPayment(payment);
						orderForm.setStatus(OrderStatus.ORDER_STATUS_IN_REVIEW.getCode()); // 更改为“审核中”状态
						orderForm.setTitle(activity.getTitle());
						orderFormService.update(orderForm);// 更新订单信息
					}
				}
			}
			int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
			activity.setJoinNum(joinNum);
			activityService.update(activity);
			dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDITING.getCode()); // 更改为“审核中”状态
			dbMemberAct.setJoinin(YesNoStatus.YES.getCode());
			dbMemberAct.setName(applyInput.getRealname()); // 联系人
			dbMemberAct.setMobile(applyInput.getMobile()); // 手机
			dbMemberAct.setCompany(applyInput.getCompany()); // 公司名称
			dbMemberAct.setJobTitle(applyInput.getTitle()); // 公司职位
			dbMemberAct.setExtra(applyInput.getExtra()); // 备注信息
			dbMemberAct.setPayment(activity.getPrice()); // 活动费用
			dbMemberAct.setUpdateDate(new Date());
			dbMemberAct.setWechatNum(applyInput.getWechatNum());
			memberActService.update(dbMemberAct);// 更新报名信息

			applyOutput.setMemberActId(dbMemberAct.getId());
			applyOutput.setActStatus(dbMemberAct.getCheckStatus());
			applyOutput.setJoinNum(activity.getJoinNum());
		} else {
			// 没有报名，则新建报名信息
			applyOutput = apply(applyInput, memberId);
		}
		return applyOutput;
	}
    
    /**
     * 取出报名数据返回前端
     * @param memberId
     * @param applyInput
     * @return 报名数据
     */
    @Transactional
	public ApplyOutput getApplyData2(String memberId, ApplyInput applyInput) throws BusinessException {
		MemberAct dbMemberAct = memberActService.findByMemberAct(memberId, applyInput.getId());
		Activity activity = activityService.get(applyInput.getId()); // 对应活动
		applyInput.setPayment(activity.getPrice());
		ApplyOutput applyOutput = new ApplyOutput();
		if (null != dbMemberAct) {
			// 已经报名,取出报名信息
			if (ActStatus.ACT_STATUS_AUDITING.getCode() == dbMemberAct.getCheckStatus()
					|| ActStatus.ACT_STATUS_AUDIT_PASS.getCode() == dbMemberAct.getCheckStatus()
					|| ActStatus.ACT_STATUS_PAID.getCode() == dbMemberAct.getCheckStatus()) {
				throw new BusinessException(PartyCode.JOIN_ACT_STATUS_ERROR, "活动报名状态错误");
			}

			// 订单号存在，订单记录不存在
			if (StringUtils.isNotEmpty(dbMemberAct.getOrderId())) {
				OrderForm orderForm = orderFormService.get(dbMemberAct.getOrderId());
				if (orderForm != null) {
					if (!orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
						boolean flag = false;
						try {
							Object responseObject = queryBizService.orderquery(orderForm.getId());
							if (responseObject != null) {
								if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
									WechatPayQueryResponse response = (WechatPayQueryResponse) responseObject;
									if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
										if (response.getTradeState().equals(TradeStatus.WX_NOTPAY.getCode())) {
											flag = true;
										}
									}
								} else if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
									AliPayQueryResponse response = (AliPayQueryResponse) responseObject;
									if (response.getCode().equals(Constant.ALI_SUCCESS_CODE)) {
										if (StringUtils.isNotEmpty(response.getTotalAmount())) {
											Float total = Float.valueOf(response.getTotalAmount());
											if (!total.equals(activity.getPrice())) {
												flag = true;
											}
										}
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (flag) {
							// 订单状态等于已退款，重新生成订单数据
							BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
							OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
							dbMemberAct.setOrderId(orderOutput.getId());
							applyOutput.setOrderId(orderOutput.getId());
						} else {
							// 订单状态不等于已退款，更改状态
							orderForm.setLinkman(applyInput.getRealname());// 联系人
							orderForm.setPhone(applyInput.getMobile());// 手机
							double payment = BigDecimalUtils.mul(activity.getPrice(), orderForm.getUnit()); // 金额
							double payment2 = BigDecimalUtils.round(payment, 2);
							orderForm.setPayment((float) payment2);
							orderForm.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()); // 更改为“待支付”状态
							orderForm.setTitle(activity.getTitle());
							orderFormService.update(orderForm);// 更新订单信息
							applyOutput.setOrderId(orderForm.getId());
						}
					} else {
						// 订单状态等于已退款，重新生成订单数据
						BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
						OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
						dbMemberAct.setOrderId(orderOutput.getId());
						applyOutput.setOrderId(orderOutput.getId());
					}
				} else {
					// 订单号存在，订单记录不存在 生成订单数据
					BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
					OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
					dbMemberAct.setOrderId(orderOutput.getId());
					applyOutput.setOrderId(orderOutput.getId());
				}
			}
			
			// 没有订单号
			if (StringUtils.isEmpty(dbMemberAct.getOrderId())) {
				// 生成订单数据
				BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
				OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
				dbMemberAct.setOrderId(orderOutput.getId());
				applyOutput.setOrderId(orderOutput.getId());
			}
			dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode()); // 更改为“已审核”状态
			dbMemberAct.setJoinin(YesNoStatus.YES.getCode());
			dbMemberAct.setName(applyInput.getRealname()); // 联系人
			dbMemberAct.setMobile(applyInput.getMobile()); // 手机
			dbMemberAct.setCompany(applyInput.getCompany()); // 公司名称
			dbMemberAct.setJobTitle(applyInput.getTitle()); // 公司职位
			dbMemberAct.setExtra(applyInput.getExtra()); // 备注信息
			dbMemberAct.setPayment(activity.getPrice()); // 活动费用
			dbMemberAct.setWechatNum(applyInput.getWechatNum()); // 微信号
			memberActService.update(dbMemberAct);// 更新报名信息

			applyOutput.setMemberActId(dbMemberAct.getId());
			applyOutput.setActStatus(dbMemberAct.getCheckStatus());
		} else {
			// 没有报名，则新建报名信息同时生成订单记录
			applyOutput = apply2(applyInput, memberId);
		}
		
		int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
		applyOutput.setJoinNum(joinNum);
		
		// 0元活动 自动报名成功
		if (activity.getPrice().equals(Float.valueOf("0.0"))) {
			try {
				orderBizService.updatePayBusiness(applyOutput.getOrderId(), applyInput.getFrom());
				messageOrderBizService.send(applyOutput.getOrderId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			dbMemberAct = memberActService.findByMemberAct(memberId, applyInput.getId());
			applyOutput.setActStatus(dbMemberAct.getCheckStatus());
			applyOutput.setActId(activity.getId());
			applyOutput.setFrom(applyInput.getFrom());
		}
		return applyOutput;
	}

    /**
     * 取出报名数据返回前端
     * @param memberId
     * @param applyInput
     * @return 报名数据
     */
	@Transactional
	public ApplyOutput getApplyData3(String memberId, ApplyInput applyInput, HttpServletRequest request) throws BusinessException {
		MemberAct dbMemberAct = memberActService.findByMemberAct(memberId, applyInput.getId());
		Counterfoil counterfoil = counterfoilService.get(applyInput.getCounterfoilId());
		applyInput.setPayment(counterfoil.getPayment());
		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		ApplyOutput applyOutput = new ApplyOutput();
		String memberActId = "";
		if (null != dbMemberAct) {
			memberActId = dbMemberAct.getId();
			// 已经报名,取出报名信息
			if (ActStatus.ACT_STATUS_AUDITING.getCode() == dbMemberAct.getCheckStatus()
					|| ActStatus.ACT_STATUS_AUDIT_PASS.getCode() == dbMemberAct.getCheckStatus()
					|| ActStatus.ACT_STATUS_PAID.getCode() == dbMemberAct.getCheckStatus()) {
				throw new BusinessException(PartyCode.JOIN_ACT_STATUS_ERROR, "活动报名状态错误");
			}
			
			dbMemberAct.setCheckStatus(ActStatus.ACT_STATUS_AUDIT_PASS.getCode()); // 更改为“已审核”状态
			dbMemberAct.setJoinin(YesNoStatus.YES.getCode());
			dbMemberAct.setName(applyInput.getRealname()); // 联系人
			dbMemberAct.setMobile(applyInput.getMobile()); // 手机
			dbMemberAct.setCompany(applyInput.getCompany()); // 公司名称
			dbMemberAct.setJobTitle(applyInput.getTitle()); // 公司职位
			dbMemberAct.setExtra(applyInput.getExtra()); // 备注信息
			dbMemberAct.setPayment(counterfoil.getPayment()); // 活动费用
			dbMemberAct.setWechatNum(applyInput.getWechatNum()); // 微信号
			memberActService.update(dbMemberAct);// 更新报名信息
			
			Activity activity = activityService.get(applyInput.getId());
			
			if (!counterfoil.getPayment().equals(new Float(0.0))) {
				// 订单号存在，订单记录不存在
				if (StringUtils.isNotEmpty(dbMemberAct.getOrderId())) {
					OrderForm orderForm = orderFormService.get(dbMemberAct.getOrderId());
					if (orderForm != null) {
						if (!orderForm.getStatus().equals(OrderStatus.ORDER_STATUS_REFUND.getCode())) {
							boolean flag = false;
							try {
								Object responseObject = queryBizService.orderquery(orderForm.getId());
								if (responseObject != null) {
									if (orderForm.getPaymentWay().equals(PaymentWay.WECHAT_PAY.getCode())) {
										WechatPayQueryResponse response = (WechatPayQueryResponse) responseObject;
										if (Constant.WECHAT_SUCCESS.equals(response.getReturnCode()) && Constant.WECHAT_SUCCESS.equals(response.getResultCode())) {
											if (response.getTradeState().equals(TradeStatus.WX_NOTPAY.getCode())) {
												flag = true;
											}
										}
									} else if (orderForm.getPaymentWay().equals(PaymentWay.ALI_PAY.getCode())) {
										AliPayQueryResponse response = (AliPayQueryResponse) responseObject;
										if (response.getCode().equals(Constant.ALI_SUCCESS_CODE)) {
											if (StringUtils.isNotEmpty(response.getTotalAmount())) {
												Float total = Float.valueOf(response.getTotalAmount());
												if (!total.equals(counterfoil.getPayment())) {
													flag = true;
												}
											}
										}
									}
								}
							} catch (Exception e) {
								logger.error("订单查询异常", e);
							}
							
							if (flag) {
								// 订单状态等于已退款，重新生成订单数据
								BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
								OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
								dbMemberAct.setOrderId(orderOutput.getId());
								applyOutput.setOrderId(orderOutput.getId());
							} else {
								// 订单状态不等于已退款，更改状态
								orderForm.setLinkman(applyInput.getRealname());// 联系人
								orderForm.setPhone(applyInput.getMobile());// 手机
								double payment = BigDecimalUtils.mul(counterfoil.getPayment(), orderForm.getUnit()); // 金额
								double payment2 = BigDecimalUtils.round(payment, 2);
								orderForm.setPayment((float) payment2);
								orderForm.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode()); // 更改为“待支付”状态
								orderForm.setTitle(activity.getTitle());
								orderFormService.update(orderForm);// 更新订单信息
								applyOutput.setOrderId(orderForm.getId());
							}
						} else {
							// 订单状态等于已退款，重新生成订单数据
							BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
							OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
							dbMemberAct.setOrderId(orderOutput.getId());
							applyOutput.setOrderId(orderOutput.getId());
						}
					} else {
						// 订单号存在，订单记录不存在 生成订单数据
						BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
						OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
						dbMemberAct.setOrderId(orderOutput.getId());
						applyOutput.setOrderId(orderOutput.getId());
					}
				}
				// 没有订单号
				if (StringUtils.isEmpty(dbMemberAct.getOrderId())) {
					// 生成订单数据
					BookOrderInput bookOrderInput = orderBizService.createBookOrder(applyInput);
					OrderOutput orderOutput = orderBizService.bookActOrder(bookOrderInput, memberId);
					dbMemberAct.setOrderId(orderOutput.getId());
					applyOutput.setOrderId(orderOutput.getId());
				}
				
				memberActService.update(dbMemberAct);
			}

			Map<String, Object> params = new HashMap<String,Object>();
	        params.put("actId", applyInput.getId());
	        params.put("counterfoilId", counterfoil.getId());
	        Integer joinNum = memberActService.getSuccessMemberAct(params);
			
			applyOutput.setMemberActId(dbMemberAct.getId());
			applyOutput.setActStatus(dbMemberAct.getCheckStatus());
			applyOutput.setJoinNum(joinNum);
		} else {
			// 没有报名，则新建报名信息同时生成订单记录
			applyOutput = apply3(applyInput, memberId);
			
			memberActId = applyOutput.getMemberActId();
		}
		
		CounterfoilBusiness search = new CounterfoilBusiness(counterfoil.getId(), memberActId);
		CounterfoilBusiness business = counterfoilBusinessService.getUnique(search);
		if (business == null) {
			search.setCreateBy(currentUser.getId());
			counterfoilBusinessService.insert(search);
		}
		
		// 0元活动 自动报名成功
		if (counterfoil.getPayment().equals(Float.valueOf("0.0"))) {
			try {
				orderBizService.updatePayBusiness2(OrderType.ORDER_ACTIVITY.getCode(), applyInput.getFrom(), applyInput.getId(), currentUser.getId());
				messageOrderBizService.sendFree(OrderType.ORDER_ACTIVITY.getCode(), applyInput.getId(), memberActId, currentUser.getId());
			} catch (Exception e) {
				logger.error("免费活动回调异常", e);
			}
			
			dbMemberAct = memberActService.findByMemberAct(memberId, applyInput.getId());
			applyOutput.setActStatus(dbMemberAct.getCheckStatus());
			applyOutput.setActId(applyInput.getId());
			applyOutput.setFrom(applyInput.getFrom());
		}
		return applyOutput;
	}
}
