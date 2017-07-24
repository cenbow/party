package com.party.admin.web.controller.charge;

import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.Level;
import com.party.core.model.system.SysRole;
import com.party.core.service.charge.ILevelService;
import com.party.core.service.system.ISysRoleService;
import com.party.core.utils.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 等级
 */
@Controller
@RequestMapping("/charge/level")
public class LevelController {
    @Autowired
    private ILevelService levelService;
    @Autowired
    private ISysRoleService sysRoleService;

    protected static Logger logger = LoggerFactory.getLogger(LevelController.class);

    /**
     * 列表
     *
     * @param level
     * @param commonInput
     * @param page
     * @return
     */
    @RequestMapping("levelList")
    public ModelAndView levelList(Level level, CommonInput commonInput, Page page) {
        page.setLimit(20);
        level.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        Map<String, Object> params = CommonInput.appendParams(commonInput);
        List<Level> levelList = levelService.webListPage(level, params, page);
        for (Level dbLevel : levelList) {
            SysRole sysRole = sysRoleService.get(dbLevel.getSysRoleId());
            dbLevel.setSysRoleName(sysRole.getName());
        }
        ModelAndView modelAndView = new ModelAndView("charge/levelList");
        modelAndView.addObject("levels", levelList);
        modelAndView.addObject("page", page);
        modelAndView.addObject("level", level);
        modelAndView.addObject("input", commonInput);
        return modelAndView;
    }

    /**
     * 表单
     *
     * @param levelId
     * @return
     */
    @RequestMapping("{levelId}/toForm")
    public ModelAndView toForm(@PathVariable("levelId") String levelId) {
        ModelAndView mv = new ModelAndView("charge/levelForm");
        if (StringUtils.isNotEmpty(levelId) && !levelId.equals("0")) {
            Level level = levelService.get(levelId);
            SysRole sysRole = sysRoleService.get(level.getSysRoleId());
            level.setSysRoleName(sysRole.getName());
            mv.addObject("level", level);
        }
        return mv;
    }

    /**
     * 新增/编辑
     *
     * @param level
     * @param result
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public AjaxResult save(Level level, BindingResult result, String isFree) {
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            return AjaxResult.error(allErros.get(0).getDefaultMessage());
        }

        try {
            if (StringUtils.isEmpty(level.getId())) {
                if (isFree.equals("free")) {
                    level.setPrice(0f);
                }
                levelService.insert(level);
            } else {
                Level dbLevel = levelService.get(level.getId());
                MyBeanUtils.copyBeanNotNull2Bean(level, dbLevel);
                levelService.update(dbLevel);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            logger.error("等级保存异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param levelId
     * @return
     */
    @ResponseBody
    @RequestMapping("{levelId}/delete")
    public AjaxResult delete(@PathVariable("levelId") String levelId) {
        if (StringUtils.isEmpty(levelId)) {
            return AjaxResult.error("主键不能为空");
        }
        levelService.delete(levelId);
        return AjaxResult.success();
    }

    /**
     * 选择合作商角色
     *
     * @param sysRoleId
     * @return
     */
    @RequestMapping("{sysRoleId}/selectRole")
    public ModelAndView selectRole(@PathVariable("sysRoleId") String sysRoleId) {
        ModelAndView mv = new ModelAndView("charge/selectRole");
        SysRole t = new SysRole();
        t.setType(1);
        List<SysRole> roles = sysRoleService.list(t);
        mv.addObject("sysRoles", roles);
        mv.addObject("sysRoleId", sysRoleId);
        return mv;
    }
}
