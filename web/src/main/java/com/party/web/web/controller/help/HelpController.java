package com.party.web.web.controller.help;

import com.party.common.utils.StringUtils;
import com.party.core.model.help.Help;
import com.party.core.service.help.IHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 帮助教程
 *
 * @author Administrator
 * @date 2017/7/20 0020 18:28
 */
@Controller
@RequestMapping("/help/help")
public class HelpController {

    @Autowired
    private IHelpService helpService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list() {
        List<Help> helpList = helpService.webListPage(new Help(), new HashMap<String, Object>(), null);
        ModelAndView mv = new ModelAndView("help/helpList");
        mv.addObject("helpList", helpList);
        return mv;
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping("{id}/getDetail")
    public ModelAndView getDetail(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("help/helpDetail");
        Help help = helpService.get(id);
        if (StringUtils.isNotEmpty(help.getContent())) {
            String content = StringUtils.stringtohtml(help.getContent());
            help.setContent(content);
        }
        if (StringUtils.isNotEmpty(help.getFrontContent())) {
            String content = StringUtils.stringtohtml(help.getFrontContent());
            help.setFrontContent(content);
        }
        mv.addObject("currentHelp", help);
        String parentId = help.getParentId();
        if ("0".equals(parentId)) {
            Help pHelp = helpService.get(id);
            List<Help> childrens = helpService.list(new Help(pHelp.getId()));
            pHelp.setChildrens(childrens);
            mv.addObject("help", pHelp);
        } else if (!"0".equals(parentId)) {
            Help pHelp = helpService.get(parentId);
            List<Help> childrens = helpService.list(new Help(pHelp.getId()));
            pHelp.setChildrens(childrens);
            mv.addObject("help", pHelp);
        }
        return mv;
    }
}
