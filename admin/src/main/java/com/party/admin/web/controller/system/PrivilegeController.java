package com.party.admin.web.controller.system;

import com.google.common.base.Strings;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.output.system.PrivilegeOutput;
import com.party.common.paging.Page;
import com.party.core.model.member.Member;
import com.party.core.model.system.PrivilegeType;
import com.party.core.model.system.SysPrivilege;
import com.party.core.service.system.ISysPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 权限控制器
 * Created by wei.li
 *
 * @date 2017/6/27 0027
 * @time 14:10
 */

@Controller
@RequestMapping(value = "/system/privilege")
public class PrivilegeController {

    @Autowired
    private ISysPrivilegeService sysPrivilegeService;

    /**
     * 权限列表
     * @param sysPrivilege 权限
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "list")
    public ModelAndView list(SysPrivilege sysPrivilege, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("system/privilege/list");
        List<SysPrivilege> privilegeList = sysPrivilegeService.listPage(sysPrivilege, page);
        Map<Integer, String> types = PrivilegeType.convertMap();
        modelAndView.addObject("types", types);
        modelAndView.addObject("page", page);
        modelAndView.addObject("list", privilegeList);
        return modelAndView;
    }

    /**
     * 查询顶级权限
     * @param sysPrivilege 权限实体
     * @return 交互数据
     */
    @RequestMapping(value = "top/list")
    public ModelAndView topList(SysPrivilege sysPrivilege){
        ModelAndView modelAndView = new ModelAndView();
        List<SysPrivilege> list = sysPrivilegeService.findTop();
        modelAndView.addObject("list", list);
        return modelAndView;
    }

    /**
     * 查询子级权限
     * @param sysPrivilege 权限实体
     * @return 交互数据
     */
    @RequestMapping(value = "children/list")
    public ModelAndView childrenList(SysPrivilege sysPrivilege){
        ModelAndView modelAndView = new ModelAndView();
        List<SysPrivilege> childrentList = sysPrivilegeService.findByParent(sysPrivilege.getParentId());
        modelAndView.addObject("list", childrentList);
        return modelAndView;
    }


    /**
     * 权限视图
     * @param id 编号
     * @return 交互数据
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("system/privilege/view");
        SysPrivilege sysPrivilege;
        if (Strings.isNullOrEmpty(id)){
            sysPrivilege = new SysPrivilege();
        }
        else {
            sysPrivilege = sysPrivilegeService.get(id);
        }

        PrivilegeOutput output = PrivilegeOutput.transform(sysPrivilege);
        if (null != sysPrivilege.getParentId() && !sysPrivilege.getParentId().equals("0")){
            SysPrivilege parent = sysPrivilegeService.get(sysPrivilege.getParentId());
            output.setParentName(parent.getName());
        }
        else {
            output.setParentId("0");
            output.setParentName("根目录");
        }
        Map<Integer, String> types = PrivilegeType.convertMap();
        modelAndView.addObject("types", types);
        modelAndView.addObject("privilege", output);
        return modelAndView;
    }

    /**
     * 查看子
     * @param parentId 父级编号
     * @return 交互数据
     */
    @RequestMapping(value = "children/view")
    public ModelAndView childrenView(String parentId){
        ModelAndView modelAndView = new ModelAndView("system/privilege/view");
        SysPrivilege parent = sysPrivilegeService.get(parentId);
        PrivilegeOutput output = new PrivilegeOutput();
        output.setParentId(parentId);
        output.setParentName(parent.getName());
        Map<Integer, String> types = PrivilegeType.convertMap();
        modelAndView.addObject("types", types);
        modelAndView.addObject("privilege", output);
        return modelAndView;
    }


    /**
     * 保存权限
     * @param sysPrivilege 权限
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(SysPrivilege sysPrivilege){
        Member member = RealmUtils.getCurrentUser();
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(sysPrivilege.getId())){
            sysPrivilege.setCreateBy(member.getId());
            sysPrivilege.setUpdateBy(member.getId());
            sysPrivilegeService.insert(sysPrivilege);
        }
        else {
            sysPrivilege.setUpdateBy(member.getId());
            sysPrivilegeService.update(sysPrivilege);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 删除权限信息
     * @param id 编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        sysPrivilegeService.delete(id);
        return ajaxResult;
    }


    @RequestMapping(value = "select")
    public ModelAndView select(String id){
        ModelAndView modelAndView = new ModelAndView("system/privilege/select");
        List<SysPrivilege> privileges = sysPrivilegeService.list(new SysPrivilege());
        SysPrivilege parent = new SysPrivilege();
        parent.setId("0");
        parent.setParentId("");
        parent.setName("根目录");
        privileges.add(parent);

        SysPrivilege sysPrivilege = sysPrivilegeService.get(id);
        if (id.equals("0")){
            sysPrivilege = parent;
        }
        modelAndView.addObject("privileges", privileges);
        modelAndView.addObject("myPrivilege", sysPrivilege);
        return modelAndView;
    }
}
