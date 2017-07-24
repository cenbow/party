package com.party.web.web.controller.gatherForm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.gatherForm.*;
import com.party.core.service.gatherForm.IGatherFormInfoService;
import com.party.core.service.gatherForm.IGatherFormOptionService;
import com.party.core.service.gatherForm.IGatherFormService;
import com.party.core.service.gatherForm.IGatherProjectService;
import com.party.core.utils.MyBeanUtils;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.gatherForm.GatherProjectOutput;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 表单项目
 *
 * @author Administrator
 */
@Controller
@RequestMapping("gatherForm/project")
public class GatherProjectController {
    @Autowired
    private IGatherProjectService gatherProjectService;
    @Autowired
    private IGatherFormInfoService gatherFormInfoService;
    @Autowired
    private IGatherFormOptionService gatherFormOptionService;
    @Autowired
    private IGatherFormService gatherFormService;
    @Autowired
    private FileBizService fileBizService;

    protected static Logger logger = LoggerFactory.getLogger(GatherProjectController.class);

    @RequestMapping("list")
    public ModelAndView list(GatherProject gatherProject, CommonInput commonInput, Page page) throws Exception {
        page.setLimit(20);
        gatherProject.setCreateBy(RealmUtils.getCurrentUser().getId());
        Map<String, Object> params = CommonInput.appendParams(commonInput);
        List<GatherProject> gatherProjects = gatherProjectService.webListPage(gatherProject, params, page);
        List<GatherProjectOutput> outputs = Lists.newArrayList();
        for (GatherProject input : gatherProjects) {
            GatherProjectOutput output = new GatherProjectOutput();
            MyBeanUtils.copyBeanNotNull2Bean(input, output);

            params = Maps.newHashMap();
            params.put("projectId", input.getId());
            List<Map<String, Object>> webListPage = gatherFormInfoService.webListPage(params, null);
            output.setTotalNum(webListPage.size());

            Integer fieldNum = gatherFormService.getCount(input.getId());
            output.setFieldNum(fieldNum);

            // 二维码
            String path = RealmUtils.getCurrentUser().getId() + "/gatherForm/form/";
            String content = "gather_form/form_info.html?projectId=" + input.getId();
            String baseQrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
            output.setQrCodeUrl(baseQrCodeUrl);
            outputs.add(output);
        }
        ModelAndView mv = new ModelAndView("gatherForm/projectList");
        mv.addObject("gatherProjects", outputs);
        mv.addObject("input", commonInput);
        mv.addObject("page", page);
        return mv;
    }

    @RequestMapping("toForm")
    public ModelAndView toForm(String id) {
        ModelAndView mv = new ModelAndView("gatherForm/projectForm");
        if (StringUtils.isNotEmpty(id)) {
            GatherProject gatherProject = gatherProjectService.get(id);
            if (StringUtils.isNotEmpty(gatherProject.getContent())) {
                String content = StringUtils.stringtohtml(gatherProject.getContent());
                gatherProject.setContent(content);
            }
            mv.addObject("project", gatherProject);

            List<GatherFormInfo> infos = gatherFormInfoService.list(new GatherFormInfo(id));
            mv.addObject("deleteStatus", infos.size() > 0 ? false : true);

            List<GatherForm> fields = gatherFormService.list(new GatherForm(id));
            for (GatherForm gatherForm : fields) {
                if (gatherForm.getType().equals(FormType.CHECKBOX.getValue()) || gatherForm.getType().equals(FormType.RADIO.getValue())
                        || gatherForm.getType().equals(FormType.SELECT.getValue())) {
                    List<GatherFormOption> subitems = gatherFormOptionService.list(new GatherFormOption(gatherForm.getId()));
                    gatherForm.setSubitems(subitems);
                }
            }
            mv.addObject("fields", fields);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public AjaxResult save(GatherProject gatherProject, BindingResult result) {
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            return AjaxResult.error(allErros.get(0).getDefaultMessage());
        }
        try {
            if (StringUtils.isNotEmpty(gatherProject.getContent())) {
                String content = StringEscapeUtils.escapeHtml4(gatherProject.getContent().trim());
                gatherProject.setContent(content);
            }

            String projectId = gatherProject.getId();
            String currentId = RealmUtils.getCurrentUser().getId();
            if (StringUtils.isEmpty(gatherProject.getShowPicture())) {
                gatherProject.setShowPicture("1");
            }
            if (StringUtils.isNotEmpty(gatherProject.getId())) {
                GatherProject t = gatherProjectService.get(gatherProject.getId());
                MyBeanUtils.copyBeanNotNull2Bean(gatherProject, t);
                gatherProjectService.update(t);
            } else {
                // 默认不通知
                gatherProject.setIsRemindMe("2");
                gatherProject.setCreateBy(currentId);
                gatherProject.setUpdateBy(currentId);
                projectId = gatherProjectService.insert(gatherProject);
            }
            if (null != gatherProject.getItems()) {
                for (GatherForm gatherForm : gatherProject.getItems()) {
                    if (StringUtils.isEmpty(gatherForm.getRequired())) {
                        gatherForm.setRequired("false");
                    }
                    String gatherFormId = gatherForm.getId();
                    if (StringUtils.isEmpty(gatherForm.getId())) {
                        gatherForm.setMaxIndex(new Integer(1));
                        gatherForm.setCreateBy(currentId);
                        gatherForm.setUpdateBy(currentId);
                        gatherForm.setProjectId(projectId);
                        gatherFormId = gatherFormService.insert(gatherForm);
                    } else {
                        GatherForm t = gatherFormService.get(gatherForm.getId());
                        MyBeanUtils.copyBeanNotNull2Bean(gatherForm, t);
                        gatherFormService.update(t);
                    }
                    if (null != gatherForm.getSubitems()) {
                        for (GatherFormOption option : gatherForm.getSubitems()) {
                            if (StringUtils.isEmpty(option.getId())) {
                                option.setCreateBy(currentId);
                                option.setFieldId(gatherFormId);
                                gatherFormOptionService.insert(option);
                            } else {
                                GatherFormOption t = gatherFormOptionService.get(option.getId());
                                MyBeanUtils.copyBeanNotNull2Bean(option, t);
                                gatherFormOptionService.update(t);
                            }
                        }
                    }
                }
            }
            return AjaxResult.success();
        } catch (Exception e) {
            logger.error("", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(String id) {
        List<GatherFormInfo> infos = gatherFormInfoService.list(new GatherFormInfo(id));
        if (infos.size() > 0) {
            return AjaxResult.error("已有收集信息，不能删除");
        }
        List<GatherForm> fields = gatherFormService.list(new GatherForm(id));
        for (GatherForm gatherForm : fields) {
            gatherFormOptionService.deleteByField(gatherForm.getId());
        }
        gatherFormService.deleteByProject(id);
        gatherProjectService.delete(id);
        return AjaxResult.success();
    }
}
