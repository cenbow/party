package com.party.admin.web.controller.charge;

import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.charge.PackageMember;
import com.party.core.model.charge.ProductPackage;
import com.party.core.model.system.SysRole;
import com.party.core.service.charge.IPackageMemberService;
import com.party.core.service.charge.IPackageService;
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
@RequestMapping("/charge/package")
public class PackageController {
    @Autowired
    private IPackageService packageService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private IPackageMemberService packageMemberService;

    protected static Logger logger = LoggerFactory.getLogger(PackageController.class);

    /**
     * 列表
     *
     * @param productPackage
     * @param commonInput
     * @param page
     * @return
     */
    @RequestMapping("packageList")
    public ModelAndView packageList(ProductPackage productPackage, CommonInput commonInput, Page page) {
        page.setLimit(20);
        productPackage.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        Map<String, Object> params = CommonInput.appendParams(commonInput);
        List<ProductPackage> packageList = packageService.webListPage(productPackage, params, page);
        for (ProductPackage dbPackage : packageList) {
            SysRole sysRole = sysRoleService.get(dbPackage.getSysRoleId());
            dbPackage.setSysRoleName(sysRole.getName());
        }
        ModelAndView mv = new ModelAndView("charge/packageList");
        mv.addObject("packages", packageList);
        mv.addObject("page", page);
        mv.addObject("level", productPackage);
        mv.addObject("input", commonInput);
        return mv;
    }

    /**
     * 表单
     *
     * @param levelId
     * @return
     */
    @RequestMapping("{levelId}/toForm")
    public ModelAndView toForm(@PathVariable("levelId") String levelId) {
        ModelAndView mv = new ModelAndView("charge/packageForm");
        if (StringUtils.isNotEmpty(levelId) && !levelId.equals("0")) {
            ProductPackage productPackage = packageService.get(levelId);
            SysRole sysRole = sysRoleService.get(productPackage.getSysRoleId());
            productPackage.setSysRoleName(sysRole.getName());
            mv.addObject("productPackage", productPackage);
        }
        return mv;
    }

    /**
     * 新增/编辑
     *
     * @param productPackage
     * @param result
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public AjaxResult save(ProductPackage productPackage, BindingResult result, String isFree) {
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            return AjaxResult.error(allErros.get(0).getDefaultMessage());
        }

        try {
            if (StringUtils.isEmpty(productPackage.getId())) {
                if (isFree.equals("free")) {
                    productPackage.setPrice(0f);
                }
                packageService.insert(productPackage);
            } else {
                ProductPackage dbProductPackage = packageService.get(productPackage.getId());
                MyBeanUtils.copyBeanNotNull2Bean(productPackage, dbProductPackage);
                packageService.update(dbProductPackage);
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
        PackageMember packageMember = new PackageMember();
        packageMember.setLevelId(levelId);
        List<PackageMember> packageMembers = packageMemberService.list(packageMember);
        if (packageMembers.size() == 0) {
            packageService.delete(levelId);
            return AjaxResult.success();
        } else {
            return AjaxResult.error("套餐已被购买");
        }
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
