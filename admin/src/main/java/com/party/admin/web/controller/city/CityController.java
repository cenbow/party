package com.party.admin.web.controller.city;

import com.alibaba.fastjson.JSONObject;
import com.party.admin.biz.activity.ActivityBizService;
import com.party.admin.biz.crowdfund.ProjectBizService;
import com.party.admin.biz.crowdfund.RepresentBizService;
import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.activity.ActivityOutput;
import com.party.admin.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.activity.CrowfundResources;
import com.party.core.model.city.City;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.distributor.WithCount;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderType;
import com.party.core.model.system.Dict;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.model.user.User;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.ICrowfundResourcesService;
import com.party.core.service.city.ICityService;
import com.party.core.service.crowdfund.impl.ProjectService;
import com.party.core.service.crowdfund.impl.TargetProjectService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.system.IDictService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.core.service.user.IUserService;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 开放城市
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @RequestMapping(value = "list")
    public ModelAndView list(City city, Page page) {
        ModelAndView mv = new ModelAndView("city/cityList");
        page.setLimit(20);
        city.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        List<City> list = cityService.listPage(city, page);
        mv.addObject("list", list);
        mv.addObject("city", city);
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping(value = "form")
    public ModelAndView form(String id, Page page) {
        ModelAndView mv = new ModelAndView("city/cityForm");
        City city = cityService.get(id);
        mv.addObject("city", city);
        return mv;
    }

    /**
     * 验证城市名称是否重复
     */
    @ResponseBody
    @RequestMapping(value = "validateName", method = RequestMethod.POST)
    public AjaxResult validateNameRepeate(City city) {
        AjaxResult ajaxResult = new AjaxResult();
        city.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        List<City> list = cityService.validateName(city);
        if (null != list && list.size() > 0) {
            ajaxResult.setSuccess(false);
        } else {
            ajaxResult.setSuccess(true);
        }
        return ajaxResult;
    }

    /**
     * 保存城市
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(City city, BindingResult result) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
            return ajaxResult;
        }
        city.setUpdateDate(new Date());
        city.setUpdateBy(RealmUtils.getCurrentUser().getId());
        if (StringUtils.isNotEmpty(city.getId())) {// 编辑表单保存
            cityService.update(city);
        } else {
            cityService.insert(city);
        }
        cityService.updateCityRedis();
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除开放城市
     */
    @ResponseBody
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public AjaxResult del(String id) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        if (StringUtils.isNotBlank(id)) {
            cityService.deleteLogic(id);
            cityService.updateCityRedis();
        } else {
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
