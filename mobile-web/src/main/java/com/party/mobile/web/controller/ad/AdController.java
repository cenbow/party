package com.party.mobile.web.controller.ad;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.service.advertise.IAdvertiseService;
import com.party.mobile.biz.ad.AdBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.ad.input.AdListInput;
import com.party.mobile.web.dto.ad.output.AdListOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 广告控制层
 * party
 * Created by Wesley
 * on 2016/11/2
 */

@Controller
@RequestMapping(value = "/ad/ad")
public class AdController {

    @Autowired
    IAdvertiseService advertiseService;

    @Autowired
    AdBizService adBizService;


    /**
     * 分页查询广告列表
     * @param adListInput 查询参数
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public AjaxResult list(AdListInput adListInput, Page page){
        if (null == adListInput || Strings.isNullOrEmpty(adListInput.getAdPos()))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "广告位置参数adPos为空");
        }
        List<AdListOutput> adListOutputList = adBizService.list(adListInput, page);
        return AjaxResult.success(adListOutputList, page);
    }
}
