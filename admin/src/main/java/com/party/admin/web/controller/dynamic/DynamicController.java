package com.party.admin.web.controller.dynamic;

import com.party.admin.biz.dynamic.DynamicBizService;
import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.dynamic.DynamicOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.dynamic.DynamicType;
import com.party.core.model.dynamic.Dypics;
import com.party.core.model.member.Member;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.IDypicsService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.service.member.IMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dynamic/dynamic")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private IDypicsService dypicsService;

    @Autowired
    private DynamicBizService dynamicBizService;

    @Autowired
    private FileBizService fileBizService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private DynamicBaseBizService dynamicBaseBizService;

    /**
     * 动态列表
     *
     * @param dynamic
     * @param page
     * @param commonInput
     * @return
     */
    @RequestMapping(value = "dynamicList")
    public ModelAndView activityList(Dynamic dynamic, Page page, CommonInput commonInput) {
        ModelAndView mv = new ModelAndView("dynamic/dynamicList");
        Map<String, Object> params = CommonInput.appendParams(commonInput);
        mv.addObject("input", commonInput);
        dynamic.setDynamicType(DynamicType.COMMUNITY.getCode());
        List<Dynamic> dynamics = dynamicService.webListPage(dynamic, params, page);
        List<DynamicOutput> dynamicOutputs = LangUtils.transform(dynamics, input -> {
            return dynamicBizService.transformCommon(input);
        });
        mv.addObject("dynamics", dynamicOutputs);
        mv.addObject("page", page);
        return mv;
    }

    /**
     * 跳转至发布 动态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "dynamicForm")
    public ModelAndView dynamicForm(String id) {
        ModelAndView mv = new ModelAndView("dynamic/dynamicForm");
        if (StringUtils.isNotEmpty(id)) {
            /** 活动 **/
            Dynamic dynamic = dynamicService.get(id);
            Member member = memberService.get(dynamic.getCreateBy());
            mv.addObject("member", member);
            mv.addObject("dynamic", dynamic);
            List<Dypics> dypics = dypicsService.findByDynamicId(id, null);
            mv.addObject("dypics", dypics);
        }

        return mv;
    }

    /**
     * 跳转至代发 动态
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "helpPublish")
    public ModelAndView helpPublish(String memberId) {
        ModelAndView mv = new ModelAndView("dynamic/dynamicForm");
        if (StringUtils.isNotEmpty(memberId)) {
            Member member = memberService.get(memberId);
            Dynamic dynamic = new Dynamic();
            dynamic.setCreateBy(memberId);
            mv.addObject("member", member);
            mv.addObject("dynamic", dynamic);
        }
        return mv;
    }

    /**
     * 保存
     *
     * @param dynamic
     * @param result
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(Dynamic dynamic, BindingResult result) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
            return ajaxResult;
        }
        dynamic.setDynamicType(DynamicType.COMMUNITY.getCode());
        dynamicBaseBizService.saveBizDynamic(dynamic,RealmUtils.getCurrentUser().getId());
        ajaxResult.setSuccess(true);
        return ajaxResult;
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
            dynamicBaseBizService.delBiz(id);
            return new AjaxResult(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult(false);
    }

    /**
     * 异步删除动态图片
     */
    @ResponseBody
    @RequestMapping(value = "delDynamicPic")
    public AjaxResult delDynamicPic(String url, String dynamicId){
        dynamicBaseBizService.delDynamicPic(url, dynamicId);
        return new AjaxResult(true);
    }


}
