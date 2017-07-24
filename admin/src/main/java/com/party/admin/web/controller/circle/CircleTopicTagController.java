package com.party.admin.web.controller.circle;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.circle.CircleInput;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.core.model.circle.*;
import com.party.core.model.city.Area;
import com.party.core.model.system.Dict;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.ICircleTopicTagService;
import com.party.core.service.circle.ICircleTopicTagService;
import com.party.core.service.circle.biz.CircleBizService;
import com.party.core.service.circle.biz.CircleTopicBizService;
import com.party.core.service.circle.impl.CircleService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.system.IDictService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 圈子话题类型
 */
@Controller
@RequestMapping(value = "/circle/topicTag")
public class CircleTopicTagController {

    @Autowired
    private CircleTopicBizService circleTopicBizService;

    @Autowired
    private ICircleTopicTagService circleTopicTagService;
    @Autowired
    private CircleService circleService;

    /**
     * 圈子话题标签列表页面
     */
    @RequestMapping(value = {"list", ""})
    public ModelAndView list(Page page, CircleTopicTag tag) {
        page.setLimit(20);
        ModelAndView mv = new ModelAndView("circle/topicTagList");
        Map params = new HashMap<String, Object>();
        List<CircleTopicTag> list = circleTopicTagService.list(tag);
        Circle circle = circleService.get(tag.getCircle());
        mv.addObject("circle", circle);
        mv.addObject("tag", tag);
        mv.addObject("list", list);
        mv.addObject("page", page);
        return mv;
    }

    /**
     * 跳转至发布
     *
     * @param circleId
     * @param id
     * @return
     */
    @RequestMapping(value = "form")
    public ModelAndView form(String circleId, String id) {
        ModelAndView mv = new ModelAndView("circle/topicTagForm");
        if (com.party.common.utils.StringUtils.isNotEmpty(id)) {
            CircleTopicTag circleTag = circleTopicTagService.get(id);
            mv.addObject("tag", circleTag);
        }
        mv.addObject("circleId", circleId);
        return mv;
    }

    /**
     * 删除类型
     */
    @ResponseBody
    @RequestMapping(value = "del")
    public AjaxResult del(CircleTopicTag circleTopicTag) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            circleTopicBizService.delTagBiz(circleTopicTag);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 添加类型
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult reject(CircleTopicTag tag) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (StringUtils.isNotEmpty(tag.getId())) {// 编辑表单保存
                CircleTopicTag dbTag = circleTopicTagService.get(tag.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tag, dbTag);// 将编辑表单中的非\NULL值覆盖数据库记录中的值
                circleTopicTagService.update(dbTag);
            } else {
                String tagId = circleTopicTagService.insert(tag);
                tag = circleTopicTagService.get(tagId);
                ajaxResult.setData(tag);
            }
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


}
