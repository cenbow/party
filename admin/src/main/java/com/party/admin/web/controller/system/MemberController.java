package com.party.admin.web.controller.system;

import java.util.*;

import com.party.admin.biz.system.MemberMerchantBizService;
import com.party.core.exception.BusinessException;
import com.party.core.service.member.IThirdPartyUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.system.MemberBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.core.model.city.Area;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.message.MessageSet;
import com.party.core.model.system.MemberSysRole;
import com.party.core.model.system.SysRole;
import com.party.core.service.city.IAreaService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.message.IMessageService;
import com.party.core.service.message.IMessageSetService;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.core.service.system.ISysRoleService;
import com.party.notify.notifyPush.servce.INotifySendService;

@Controller
@RequestMapping(value = "/system/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IIndustryService industryService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    IMessageSetService messageSetService;

    @Autowired
    IMessageService messageService;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private IMemberSysRoleService memberSysRoleService;

    @Autowired
    private INotifySendService notifySendService;

    @Autowired
    private IMemberMerchantService memberMerchantService;

    @Autowired
    private MemberMerchantBizService memberMerchantBizService;

    @Autowired
    private IThirdPartyUserService thirdPartyUserService;

    protected static Logger logger = LoggerFactory.getLogger(MemberController.class);

    /**
     * 合作商列表
     *
     * @param member
     * @param page
     * @param commonInput
     * @param memberType
     * @param status
     * @return
     */
    @RequestMapping(value = "partnerList")
    public ModelAndView partnerList(Member member, Page page, CommonInput commonInput, String memberType, String status) {
        ModelAndView mv = new ModelAndView("system/member/partnerList");
        try {
            page.setLimit(20);
            member.setIsPartner(1);
            Map<String, Object> params = CommonInput.appendParams(commonInput);
            mv.addObject("input", commonInput);

            Set<String> statuss = new HashSet<String>();
            if (StringUtils.isNotEmpty(memberType)) {
                String[] str = memberType.split(":");
                if (str[0].equals("userStatus")) {
                    statuss.add(str[1]);
                    params.put(str[0], statuss);
                } else {
                    params.put(str[0], str[1]);
                }
            }

            if (StringUtils.isNotEmpty(status)) {
                for (String s : status.split(",")) {
                    statuss.add(s);
                }
                params.put("userStatus", statuss);
            }

            List<Member> members = memberService.listPage(member, params, page);
            mv.addObject("members", members);
            mv.addObject("page", page);

            mv.addObject("memberType", memberType);
            mv.addObject("status", status);
        } catch (Exception e) {
            logger.info("合作商列表异常", e);
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 分销商
     *
     * @param member
     * @param page
     * @param commonInput
     * @param memberType
     * @param status
     * @return
     */
    @RequestMapping(value = "distributorList")
    public ModelAndView distributorList(Member member, Page page, CommonInput commonInput, String memberType, String status) {
        ModelAndView mv = new ModelAndView("system/member/distributorList");
        try {
            page.setLimit(20);
            member.setIsDistributor(1);
            Map<String, Object> params = CommonInput.appendParams(commonInput);
            mv.addObject("input", commonInput);

            Set<String> statuss = new HashSet<String>();
            if (StringUtils.isNotEmpty(memberType)) {
                String[] str = memberType.split(":");
                if (str[0].equals("userStatus")) {
                    statuss.add(str[1]);
                    params.put(str[0], statuss);
                } else {
                    params.put(str[0], str[1]);
                }
            }

            if (StringUtils.isNotEmpty(status)) {
                for (String s : status.split(",")) {
                    statuss.add(s);
                }
                params.put("userStatus", statuss);
            }

            List<Member> members = memberService.listPage(member, params, page);
            mv.addObject("members", members);
            mv.addObject("page", page);

            mv.addObject("memberType", memberType);
            mv.addObject("status", status);
        } catch (Exception e) {
            logger.info("分销商列表异常", e);
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 用户列表
     *
     * @param member
     * @param page
     * @param commonInput
     * @param memberType
     * @param status
     * @return
     */
    @RequestMapping(value = "memberList")
    public ModelAndView publishList(Member member, Page page, CommonInput commonInput, String memberType, String status) {
        ModelAndView mv = new ModelAndView("system/member/memberList");
        try {
            page.setLimit(20);
            Map<String, Object> params = CommonInput.appendParams(commonInput);
            mv.addObject("input", commonInput);

            Set<String> statuss = new HashSet<String>();
            if (StringUtils.isNotEmpty(memberType)) {
                String[] str = memberType.split(":");
                if (str[0].equals("userStatus")) {
                    statuss.add(str[1]);
                    params.put(str[0], statuss);
                } else {
                    params.put(str[0], str[1]);
                }
            }

            if (StringUtils.isNotEmpty(status)) {
                for (String s : status.split(",")) {
                    statuss.add(s);
                }
                params.put("userStatus", statuss);
            }

            List<Member> members = memberService.listPage(member, params, page);
            mv.addObject("members", members);
            mv.addObject("page", page);

            mv.addObject("memberType", memberType);
            mv.addObject("status", status);
        } catch (Exception e) {
            logger.info("用户列表异常", e);
            e.printStackTrace();
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping("save")
    public AjaxResult save(Member member, String type) {
        try {
            member = memberBizService.saveBiz(member);
            memberBizService.saveExpert(member);
        } catch (Exception e) {
            logger.info("用户保存异常", e);
            return AjaxResult.error("用户保存异常");
        }
        return AjaxResult.success((Object) type);
    }

    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new AjaxResult(false);
            }
            memberService.delete(id);
        } catch (Exception e) {
            logger.info("删除异常", e);
            return new AjaxResult(false);
        }
        return new AjaxResult(true);
    }

    /**
     * 分销商
     *
     * @param member
     * @return
     */
    @RequestMapping(value = "distributorForm")
    public ModelAndView distributorForm(Member member) {
        ModelAndView mv = new ModelAndView("system/member/memberForm");
        mv.addObject("type", 1);
        toForm(member, mv);
        return mv;
    }

    /**
     * 合作商
     *
     * @param member
     * @return
     */
    @RequestMapping(value = "partnerForm")
    public ModelAndView parnterForm(Member member) {
        ModelAndView mv = new ModelAndView("system/member/memberForm");
        mv.addObject("type", 2);
        toForm(member, mv);
        return mv;
    }

    /**
     * 新增/编辑
     *
     * @param member
     * @return
     */
    @RequestMapping(value = "memberForm")
    public ModelAndView memberForm(Member member) {
        ModelAndView mv = new ModelAndView("system/member/memberForm");
        mv.addObject("type", 3);
        toForm(member, mv);
        return mv;
    }

    public void toForm(Member member, ModelAndView mv) {
        if (StringUtils.isNotEmpty(member.getId())) {
            member = memberService.get(member.getId());
            mv.addObject("member", member);

            if (StringUtils.isNotEmpty(member.getIndustry())) {
                Industry industry = industryService.get(member.getIndustry());
                mv.addObject("inParent", industry.getParentId());
            }

            if (StringUtils.isNotEmpty(member.getCity())) {
                Area area = areaService.get(member.getCity());
                mv.addObject("arParent", area.getParentId());
            }
        }

        Industry industry = new Industry();
        industry.setParentId("0");
        List<Industry> industries = industryService.list(industry);
        mv.addObject("industries", industries);

        Area area = new Area();
        area.setParentId("1");
        List<Area> areas = areaService.list(area);
        mv.addObject("areas", areas);
    }

    @RequestMapping(value = "memberView")
    public ModelAndView memberView(Member member) {
        ModelAndView mv = new ModelAndView("system/member/memberInfo");
        try {
            if (StringUtils.isNotEmpty(member.getId())) {
                member = memberService.get(member.getId());
                if (StringUtils.isNotEmpty(member.getIndustry())) {
                    Industry industry = industryService.get(member.getIndustry());
                    mv.addObject("industry", industry);
                }

                if (StringUtils.isNotEmpty(member.getCity())) {
                    Area area = areaService.get(member.getCity());
                    mv.addObject("city", area);
                }
                mv.addObject("member", member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 用户审核
     *
     * @param id
     * @param userStatus
     * @return
     */
    @ResponseBody
    @RequestMapping("verify")
    public AjaxResult verify(String id, Integer userStatus) {
        try {
            Member member = memberService.get(id);
            member.setUserStatus(userStatus);
            memberService.update(member);

            MessageSet messageSet = messageSetService.findByMemberId(id);
            if ((1 == messageSet.getSysTip()) && StringUtils.isNotBlank(member.getId())) {
                if (1 == member.getUserStatus()) {
                    notifySendService.sendMemberPass(member);
                } else if (2 == member.getUserStatus()) {
                    notifySendService.sendMemberRefuse(member);
                }
            }
        } catch (Exception e) {
            return new AjaxResult(false);
        }
        return new AjaxResult(true);
    }

    /**
     * 前端行业分类联动
     *
     * @param industryId
     * @return
     */
    @ResponseBody
    @RequestMapping("getIndustryByParentId")
    public List<Industry> getIndustryByParentId(String industryId) {
        Industry industry = new Industry();
        industry.setParentId(industryId);
        List<Industry> industries = industryService.list(industry);
        return industries;
    }

    /**
     * 前端城市联动
     *
     * @param cityId
     * @return
     */
    @ResponseBody
    @RequestMapping("getCityByParentId")
    public List<Area> getCityByParentId(String cityId) {
        Area area = new Area();
        area.setParentId(cityId);
        List<Area> areas = areaService.list(area);
        return areas;
    }

    /**
     * 验证用户名或者手机号
     *
     * @param property
     * @param userId
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("checkUniqueProperty")
    public boolean checkUniqueProperty(String property, String userId, String type) {
        List<Member> members = new ArrayList<Member>();
        if (StringUtils.isEmpty(userId)) {
            members = memberService.checkUserName(property, "", type);
        } else {
            members = memberService.checkUserName(property, userId, type);
        }
        if (members.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 分销开启开关
     *
     * @param id            会员编号
     * @param isDistributor 分销状态
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/distribution/switch")
    public AjaxResult distributionSwitch(String id, Integer isDistributor) {
        AjaxResult ajaxResult = new AjaxResult();
        Member member = memberService.get(id);
        if (null == member) {
            ajaxResult.setDescription("用户不存在");
            return ajaxResult;
        }
        member.setIsDistributor(isDistributor);
        SysRole sysRole = roleService.uniqueProperty("name", "分销商");
        if (sysRole != null) {
            if (isDistributor == 1) {
                MemberSysRole t = new MemberSysRole(member.getId(), sysRole.getId());
                memberSysRoleService.insert(t);
            } else {
                memberSysRoleService.deleteByRoleIdAndMemberId(sysRole.getId(), member.getId());
            }
        }
        memberService.update(member);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 修改密码
     *
     * @param member
     * @return
     */
    @ResponseBody
    @RequestMapping("updatePwd")
    public AjaxResult updatePwd(Member member) {
        try {
            if (StringUtils.isNotEmpty(member.getId())) {
                Member t = memberService.get(member.getId());

                if (StringUtils.isNotEmpty(member.getPassword())) {
                    String encryptPassword = RealmUtils.encryptPassword(member.getPassword());
                    t.setPassword(encryptPassword);
                    memberService.update(t);
                }
            }
        } catch (Exception e) {
            logger.info("修改密码异常", e);
            return new AjaxResult(false);
        }
        return new AjaxResult(true);
    }

    /**
     * 设置或取消设置管理员
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "setAdmin")
    public AjaxResult setAdmin(String id, Integer isAdmin) {
        try {
            SysRole sysRole = roleService.uniqueProperty("name", "管理员");
            Member member = memberService.get(id);
            if (isAdmin == 0) {
                member.setIsAdmin(0);
                memberService.update(member);
                memberSysRoleService.deleteByRoleIdAndMemberId(sysRole.getId(), member.getId());
            } else if (isAdmin == 1) {
                member.setIsAdmin(1);
                memberService.update(member);
                MemberSysRole t = new MemberSysRole(member.getId(), sysRole.getId());
                memberSysRoleService.insert(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 设置或取消设置达人
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "setExpert")
    public AjaxResult setExpert(String id, Integer isExpert) {
        try {
            Member member = memberService.get(id);
            SysRole sysRole = roleService.uniqueProperty("name", "达人");
            if (isExpert == 0) {
                member.setIsExpert(0);
                memberService.update(member);
                memberBizService.saveExpert(member);
                memberSysRoleService.deleteByRoleIdAndMemberId(sysRole.getId(), member.getId());
            } else if (isExpert == 1) {
                member.setIsExpert(1);
                memberService.update(member);
                memberBizService.saveExpert(member);
                MemberSysRole t = new MemberSysRole(member.getId(), sysRole.getId());
                memberSysRoleService.insert(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 新增/编辑
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "merchantForm")
    public ModelAndView merchantForm(String memberId) {
        ModelAndView mv = new ModelAndView("system/member/merchantForm");
        MemberMerchant memberMerchant = memberMerchantService.findByMemberId(memberId);
        mv.addObject("memberMerchant", memberMerchant);
        Member member = memberService.get(memberId);
        mv.addObject("member", member);
        return mv;
    }

    /**
     * 绑定商户信息
     *
     * @param memberMerchant
     * @return
     */
    @ResponseBody
    @RequestMapping("bindMerchant")
    public AjaxResult bindMerchant(MemberMerchant memberMerchant) {
        try {
            memberMerchantBizService.save(memberMerchant);
        } catch (BusinessException be) {
            return new AjaxResult(false, be.getMessage());
        } catch (Exception e) {
            logger.info("绑定商户信息异常", e);
            return new AjaxResult(false, "绑定商户信息异常");
        }
        return new AjaxResult(true);
    }

    /**
     * 根据用户手机或用户名查询
     *
     * @param property
     * @param value
     * @return
     */
    @ResponseBody
    @RequestMapping("findMemberByPhoneOrName")
    public AjaxResult bindMerchant(String property, String value) {
        try {
            List<Map<String, Object>> list = memberService.findMemberByPhoneOrName(property, value);
            return new AjaxResult().success(list);
        } catch (Exception e) {
            logger.info("根据用户手机或用户名查询失败", e);
            return new AjaxResult(false);
        }

    }

    @ResponseBody
    @RequestMapping("getMember")
    public AjaxResult getMember(String id) {

        Map<String, Object> map = new HashMap<String, Object>();

        Member member = memberService.get(id);
        String inParent = "";
        if (StringUtils.isNotEmpty(member.getIndustry())) {
            Industry industry = industryService.get(member.getIndustry());
            inParent = industry.getParentId();
        }

        String arParent = "";
        if (StringUtils.isNotEmpty(member.getCity())) {
            Area area = areaService.get(member.getCity());
            arParent = area.getParentId();
        }

        map.put("inParent", inParent);
        map.put("arParent", arParent);
        map.put("member", member);
        return AjaxResult.success(map);
    }

    /**
     * 获取第三方授权列表
     * @param memberId
     * @return
     */
    @RequestMapping("getAuthList")
    public ModelAndView getAuthList(String memberId) {
        ModelAndView mv = new ModelAndView("system/member/selAuthType");
        List<Map<String, Object>> authList = thirdPartyUserService.getAuthList(memberId);
        mv.addObject("authList", authList);
        return mv;
    }

    /**
     * 删除第三方授权信息
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("removeAuth")
    public AjaxResult removeAuth(String ids) {
        String[] split = ids.split(",");
        thirdPartyUserService.batchDelete(new HashSet(Arrays.asList(split)));
        return AjaxResult.success();
    }

    @ResponseBody
    @RequestMapping("updateMemberLogoToCloud")
    public AjaxResult updateMemberLogoToCloud(Integer limit) {
        memberBizService.updateMemberLogoToCloud(limit);
        return AjaxResult.success();
    }
}
