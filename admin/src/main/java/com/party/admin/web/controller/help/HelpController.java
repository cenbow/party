package com.party.admin.web.controller.help;

import com.google.common.collect.Lists;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.help.Help;
import com.party.core.service.help.IHelpService;
import com.party.core.utils.MyBeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2017/7/20 0020 10:56
 */
@Controller
@RequestMapping("/help/help")
public class HelpController {
    @Autowired
    private IHelpService helpService;

    protected static Logger logger = LoggerFactory.getLogger(HelpController.class);

    /**
     * 列表
     *
     * @param help
     * @param commonInput
     * @param page
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(Help help, CommonInput commonInput, Page page) {
        page.setLimit(10);
        Map<String, Object> params = CommonInput.appendParams(commonInput);
        List<Help> helpList = helpService.webListPage(help, params, page);
        List<Help> newHelpList = Lists.newArrayList();
        for (Help hh : helpList) {
            if ("0".equals(hh.getParentId())) {
                newHelpList.add(hh); // 父节点
                continue;
            }
            if (newHelpList.size() > 0) {
                for (Help h : newHelpList) {
                    if (h.getId().equals(hh.getParentId())) {
                        if (h.getChildrens().indexOf(hh) == -1) {
                            h.getChildrens().add(hh);
                        }
                        continue;
                    }
                }
            } else {
                Help newHelpe = helpService.get(hh.getParentId());
                List<Help> childrens = helpService.list(new Help(newHelpe.getId()));
                newHelpe.setChildrens(childrens);
                newHelpList.add(newHelpe);
                continue;
            }
        }
        Integer count = newHelpList.size();
        for (Help h : newHelpList) {
            count += h.getChildrens().size();
            Collections.sort(h.getChildrens(), new Comparator<Help>() {
                @Override
                public int compare(Help o1, Help o2) {
                    return o1.getSort() > o2.getSort() ? 0 : -1;
                }
            });
        }
        // page.setTotalCount(count);
        ModelAndView mv = new ModelAndView("help/helpList");
        mv.addObject("helpList", helpList);
        mv.addObject("input", commonInput);
        mv.addObject("page", page);
        mv.addObject("help", help);
        return mv;
    }

    /**
     * 编辑
     *
     * @param id
     * @param parentId
     * @return
     */
    @RequestMapping("toForm")
    public ModelAndView toForm(String id, String parentId) {
        ModelAndView mv = new ModelAndView("help/helpForm");
        if (StringUtils.isNotEmpty(id)) {
            Help help = helpService.get(id);
            if (StringUtils.isNotEmpty(help.getContent())) {
                String content = StringUtils.stringtohtml(help.getContent());
                help.setContent(content);
            }
            mv.addObject("help", help);
        }
        if (StringUtils.isNotEmpty(parentId)) {
            // 父级
            if (parentId.equals("0")){
                Help parentHelp = new Help();
                parentHelp.setId("0");
                parentHelp.setTitle("根目录");
                mv.addObject("parentHelp", parentHelp);
                String outSort = getMaxSort(parentHelp.getId(), "");
                mv.addObject("sort", outSort);
            } else {
                Help parentHelp = helpService.get(parentId);
                mv.addObject("parentHelp", parentHelp);
                if (StringUtils.isEmpty(id) && parentHelp != null) {
                    String outSort = getMaxSort(parentId, parentHelp.getSerialNumber());
                    mv.addObject("sort", outSort);
                }
            }
        } else {
            Help parentHelp = new Help();
            parentHelp.setId("0");
            parentHelp.setTitle("根目录");
            mv.addObject("parentHelp", parentHelp);
            String outSort = getMaxSort(parentHelp.getId(), "");
            mv.addObject("sort", outSort);
        }
        return mv;
    }

    private String getMaxSort(String parentId, String serialNumber) {
        Integer sort = helpService.getMaxSort(new Help(parentId));
        sort = sort == null ? 1 : sort + 1;
        if (StringUtils.isNotEmpty(serialNumber)) {
            return serialNumber + "." + sort;
        }
        return sort.toString();
    }

    @ResponseBody
    @RequestMapping("getMaxSort")
    public AjaxResult getMaxSort(String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            return AjaxResult.error("父id不能为空");
        }
        String serialNumber = "";
        if (parentId.equals("0")) {
            serialNumber = getMaxSort(parentId, "");
        } else {
            Help parentHelp = helpService.get(parentId);
            serialNumber = getMaxSort(parentId, parentHelp.getSerialNumber());
        }
        return AjaxResult.success((Object) serialNumber);
    }

    /**
     * 保存
     *
     * @param help
     * @param result
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public AjaxResult save(Help help, BindingResult result) {
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            return AjaxResult.error(allErros.get(0).getDefaultMessage());
        }

        try {
            if (StringUtils.isNotEmpty(help.getContent())) {
                String content = StringEscapeUtils.escapeHtml4(help.getContent().trim());
                help.setContent(content);
            }

            if (help.getSerialNumber().lastIndexOf(".") != -1) {
                String sort = help.getSerialNumber().substring(help.getSerialNumber().lastIndexOf(".") + 1, help.getSerialNumber().length());
                help.setSort(Integer.parseInt(sort));
            } else {
                help.setSort(Integer.parseInt(help.getSerialNumber()));
            }

            if (StringUtils.isEmpty(help.getId())) {
                if (StringUtils.isNotEmpty(help.getParentId())) {
                    if (help.getParentId().equals("0")) {
                        help.setParentIds("0");
                    } else {
                        Help parentHelp = helpService.get(help.getParentId());
                        help.setParentIds(parentHelp.getParentIds() + "," + help.getParentId());
                    }
                }
                String memberId = RealmUtils.getCurrentUser().getId();
                help.setCreateBy(memberId);
                help.setUpdateBy(memberId);
                helpService.insert(help);
            } else {
                Help dbHelp = helpService.get(help.getId());
                MyBeanUtils.copyBeanNotNull2Bean(help, dbHelp);
                helpService.update(dbHelp);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            logger.error("保存教程异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("{id}/delete")
    public AjaxResult delete(@PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            return AjaxResult.error("主键不能为空");
        }
        List<Help> helpList = helpService.list(new Help(id));
        if (helpList.size() > 0) {
            return AjaxResult.error("11");
        } else {
            helpService.delete(id);
        }
        return AjaxResult.success();
    }

    /**
     * 选择父级
     *
     * @param parentId
     * @return
     */
    @RequestMapping("{parentId}/selectParent")
    public ModelAndView selectParent(@PathVariable("parentId") String parentId) {
        Help parentHelp = new Help();
        parentHelp.setId("0");
        parentHelp.setTitle("根目录");
        List<Help> helpList = helpService.list(new Help("0"));
        helpList.add(0, parentHelp);
        ModelAndView mv = new ModelAndView("help/selectParent");
        mv.addObject("helpList", helpList);
        mv.addObject("helpId", parentId);
        return mv;
    }
}
