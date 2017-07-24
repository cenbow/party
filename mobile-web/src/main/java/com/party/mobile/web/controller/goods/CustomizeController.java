package com.party.mobile.web.controller.goods;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.common.redis.StringJedis;
import com.party.core.exception.BusinessException;
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
import com.party.mobile.biz.order.OrderBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.goods.CustomizeOutput;
import com.party.mobile.web.dto.goods.input.ListInput;
import com.party.mobile.web.dto.goods.output.CustomizeListOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.mobile.web.dto.order.Output.OrderDetailOutput;
import com.party.mobile.web.dto.order.input.BookOrderInput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定制商品控制层
 * party
 * Created by Wesley
 * on 2016/11/1.
 */
@Controller
@RequestMapping("/goods/customize")
public class CustomizeController {

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

    /**
     * 查询定制商品列表
     * @param listInput 商品查询参数
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult list(ListInput listInput, Page page){
        listInput.setType(GoodsType.GOODS_CUSTOMIZED.getCode());//获定制准商品
        List<CustomizeListOutput> listOutputList = goodsBizService.customizeList(listInput, page);
        return AjaxResult.success(listOutputList, page);
    }

    /**
     * 定制商品详情
     * @param id 定制商品编号
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

        CustomizeOutput customizeOutput = goodsBizService.getCustomized(goods, request);

        return AjaxResult.success(customizeOutput);
    }


    /**
     * 定制商品订单
     * @param bookOrderInput 定制商品输入视图
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


        //设置订单类型为定制商品订单
        bookOrderInput.setType(OrderType.ORDER_CUSTOMIZED.getCode());
        //设置定制商品订单状态为其它
        bookOrderInput.setStatus(OrderStatus.ORDER_STATUS_OTHER.getCode());

        OrderDetailOutput orderOutput = null;
        try {
            orderOutput = orderBizService.bookOrder(bookOrderInput, currentUser.getId());
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

        Map map = new HashMap();
//        map.put("order", orderOutput);
        map.put("order", null);//定制订单不用支付，给下单成功的状态即可
        map.put("member", memberOutput);
        return AjaxResult.success(map);
    }



}
