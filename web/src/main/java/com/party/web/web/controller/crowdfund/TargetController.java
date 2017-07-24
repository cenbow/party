package com.party.web.web.controller.crowdfund;

import com.party.core.model.notify.TargetTemplate;
import com.party.core.model.notify.TargetTemplateType;
import com.party.web.biz.crowdfund.TargetBizService;
import com.party.web.web.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 众筹项目控制器
 * Created by wei.li
 *
 * @date 2017/7/4 0004
 * @time 11:00
 */

@Controller
@RequestMapping(value = "/crowdfund/target")
public class TargetController {


    @Autowired
    private TargetBizService targetBizService;

    protected static Logger logger = LoggerFactory.getLogger(TargetController.class);
    /**
     * 消息发送视图
     * @param targetId 项目编号
     * @return 交互数据
     */
    @RequestMapping(value = "messageView")
    public ModelAndView messageView(String targetId, Integer type){
        ModelAndView modelAndView = new ModelAndView("crowdfund/messageView");
        TargetTemplate targetTemplate = targetBizService.get(targetId, type);
        Map<Integer, String> types = TargetTemplateType.convertMap();
        modelAndView.addObject("types", types);
        modelAndView.addObject("targetTemplate", targetTemplate);
        return modelAndView;
    }


    /**
     * 消息推送
     * @param targetTemplate 目标模板
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "send")
    public AjaxResult send(TargetTemplate targetTemplate){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            targetBizService.sendAndSave(targetTemplate);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription("发送异常");
            logger.error("消息推送异常", e);
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
