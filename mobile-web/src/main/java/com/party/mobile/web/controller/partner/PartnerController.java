package com.party.mobile.web.controller.partner;

import com.google.common.base.Strings;
import com.party.core.exception.BusinessException;
import com.party.mobile.biz.partner.PartnerBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.partner.output.GetDetailsOutput;
import com.party.mobile.web.dto.partner.output.GetGoodsOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 合作商控制层
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */

@Controller
@RequestMapping(value = "/partner/goods")
public class PartnerController {


    @Autowired
    PartnerBizService partnerBizService;

    /**
     * 获取合作商商品
     * @param id 商铺商品编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getGoods/{storeGoodsId}")
    public AjaxResult getGoods(@PathVariable(value = "storeGoodsId") String id){

        //数据验证
        if (Strings.isNullOrEmpty(id)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数验证不通过");
        }

        GetGoodsOutput getGoodsOutput;
        try {
            getGoodsOutput = partnerBizService.getGoods(id);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
        return AjaxResult.success(getGoodsOutput);
    }


    /**
     * 获取合作商商品详情
     * @param id 商铺商品主键
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getDetails/{storeGoodsId}")
    public AjaxResult getDetails(@PathVariable(value = "storeGoodsId") String id){

        //数据验证
        if (Strings.isNullOrEmpty(id)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数验证不通过");
        }

        GetDetailsOutput getDetailsOutput;
        try {
             getDetailsOutput = partnerBizService.getDetails(id);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
        return AjaxResult.success(getDetailsOutput);
    }
}
