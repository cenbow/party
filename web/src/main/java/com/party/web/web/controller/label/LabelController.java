package com.party.web.web.controller.label;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.label.Label;
import com.party.core.service.label.ILabelService;
import com.party.web.web.dto.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 标签控制器
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 15:55
 */

@Controller
@RequestMapping(value = "label/label")
public class LabelController {

    @Autowired
    private ILabelService labelService;


    /**
     * 标签列表
     * @param label 标签
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(Label label, Page page){
        ModelAndView modelAndView = new ModelAndView("label/list");
        List<Label> labelList = labelService.listPage(label, page);
        modelAndView.addObject("list", labelList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 标签视图
     * @param id 标签
     * @return 标签视图
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("label/view");
        Label label;
        if (Strings.isNullOrEmpty(id)){
            label = new Label();
        }
        else {
            label = labelService.get(id);
        }
        modelAndView.addObject("label", label);
        return modelAndView;
    }

    /**
     * 标签保存
     * @param label 标签
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(Label label){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(label.getId())){
            labelService.insert(label);
        }
        else {
            labelService.update(label);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            labelService.delete(id);
        } catch (BusinessException be) {
            be.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
