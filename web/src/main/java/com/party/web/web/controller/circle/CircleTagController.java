package com.party.web.web.controller.circle;

import com.party.common.paging.Page;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleMemberTag;
import com.party.core.model.circle.CircleTag;
import com.party.core.service.circle.ICircleMemberTagService;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.ICircleTagService;
import com.party.core.service.circle.biz.CircleMemberTagBizService;
import com.party.core.service.circle.biz.CircleTagBizService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.web.dto.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 圈子成员类型
 */
@Controller
@RequestMapping(value = "/circle/tag")
public class CircleTagController {

    @Autowired
    private ICircleService circleService;
    @Autowired
    private ICircleMemberTagService circleMemberTagService;
    @Autowired
    private ICircleTagService circleTagService;
    @Autowired
    private CircleTagBizService circleTagBizService;
    @Autowired
    private CircleMemberTagBizService circleMemberTagBizService;

    /**
     * 圈子成员标签列表页面
     */
    @RequestMapping(value = {"list", ""})
    public ModelAndView list(Page page, CircleTag tag) {
        page.setLimit(20);
        ModelAndView mv = new ModelAndView("circle/tagList");
        Map params = new HashMap<String, Object>();
        List<CircleTag> list = circleTagBizService.list(tag);
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
        ModelAndView mv = new ModelAndView("circle/tagForm");
        if (com.party.common.utils.StringUtils.isNotEmpty(id)) {
            CircleTag circleTag = circleTagService.get(id);
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
    public AjaxResult del(CircleTag circleTag) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            circleTagBizService.remove(circleTag);
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
    public AjaxResult reject(CircleTag tag) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (StringUtils.isNotEmpty(tag.getId())) {// 编辑表单保存
                CircleTag dbTag = circleTagService.get(tag.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tag, dbTag);// 将编辑表单中的非\NULL值覆盖数据库记录中的值
                circleTagService.update(dbTag);
            }else {
                String tagId = circleTagService.insert(tag);
                tag = circleTagService.get(tagId);
                ajaxResult.setData(tag);
            }
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 分配类型
     */
    @RequestMapping(value = {"setTag"})
    public ModelAndView getTag(CircleMemberTag cmTag) {
        ModelAndView mv = new ModelAndView("circle/setTag");
        CircleTag search = new CircleTag();
        search.setCircle(cmTag.getCircle());
        List<CircleTag> tagList = circleTagService.list(search);
        String cmTagIds = "";
        List<CircleMemberTag> cmTagList = circleMemberTagService.list(cmTag);
        for (CircleMemberTag t : cmTagList) {
            cmTagIds += t.getTag() + ",";
        }
        mv.addObject("tagList", tagList);
        mv.addObject("cmTagIds", cmTagIds);
        mv.addObject("cmTag", cmTag);
        return mv;
    }

    /**
     * 分配类型
     */
    @ResponseBody
    @RequestMapping(value = "saveMemberTag")
    public AjaxResult saveMemberTag(String ids, CircleMemberTag cmTag) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            circleMemberTagBizService.saveBiz(cmTag, ids);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
