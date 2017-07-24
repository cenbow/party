package com.party.admin.web.controller.advertise;

import com.party.admin.biz.advertise.AdvertiseBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.model.advertise.Advertise;
import com.party.core.model.system.Dict;
import com.party.core.service.advertise.IAdvertiseService;
import com.party.core.service.system.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 活动
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/ad")
public class AdvertiseController {

    @Autowired
    private IAdvertiseService advertiseService;
    @Autowired
    private AdvertiseBizService advertiseBizService;
    @Autowired
    private IDictService dictService;

    /**
     * 广告列表页面
     */
    @RequestMapping(value = {"list", ""})
    public ModelAndView list(Page page, Advertise ad) {
        page.setLimit(10);
        ModelAndView mv = new ModelAndView("ad/adList");
        List<Advertise> list = advertiseService.listPage(ad, page);
        Dict dict = new Dict();
        dict.setType("ad_type");
        mv.addObject("adTypes", dictService.list(dict));
        mv.addObject("list", list);
        mv.addObject("page", page);
        mv.addObject("ad", ad);
        return mv;
    }

    /**
     * 查看，增加，编辑广告表单页面
     */

    @RequestMapping(value = "form")
    public ModelAndView form(Advertise ad) {
        ModelAndView mv = new ModelAndView("ad/adForm");
        if(StringUtils.isNotBlank(ad.getId())){
            ad = advertiseService.get(ad.getId());
        }
        Dict dict = new Dict();
        dict.setType("ad_type");
        mv.addObject("adTypes", dictService.list(dict));
        dict.setType("ad_tag");
        mv.addObject("adTags", dictService.list(dict));
        mv.addObject("ad", ad);
        return mv;
    }

    /**
     * 保存广告
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(Advertise ad, BindingResult result) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
            return ajaxResult;
        }
        advertiseBizService.saveBiz(ad);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除广告
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            advertiseService.delete(id);
            return new AjaxResult(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        return new AjaxResult(false);
    }

}
