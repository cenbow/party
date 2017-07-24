package com.party.mobile.web.controller.circle;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.circle.*;
import com.party.core.model.city.Area;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.system.Dict;
import com.party.core.service.circle.*;
import com.party.core.service.circle.biz.*;
import com.party.core.service.circle.impl.CircleService;
import com.party.core.service.circle.impl.CircleTagService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.subject.impl.SubjectService;
import com.party.core.service.system.IDictService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.utils.MyBeanUtils;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.circle.output.*;
import com.party.mobile.web.dto.dynamic.output.BusinessCardOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 圈子
 *
 * @author Juliana 2016-12-14
 */

@Controller
@RequestMapping(value = "/circle")
public class CircleController {
    @Autowired
    CircleBizService circleBizService;
    @Autowired
    CircleService circleService;
    @Autowired
    CircleMemberBizService circleMemberBizService;
    @Autowired
    ICircleMemberService circleMemberService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CircleTagBizService circleTagBizService;
    @Autowired
    CurrentUserBizService currentUserBizService;
    @Autowired
    VerifyCodeBizService verifyCodeBizService;
    @Autowired
    MemberBizService memberBizService;
    @Autowired
    CircleApplyBizService circleApplyBizService;
    @Autowired
    ICircleMemberTagService circleMemberTagService;
    @Autowired
    CircleMemberTagBizService circleMemberTagBizService;
    @Autowired
    CircleTagService circleTagService;
    @Autowired
    ICircleApplyService circleApplyService;
    @Autowired
    IMemberService memberService;
    @Autowired
    IAreaService areaService;
    @Autowired
    IIndustryService industryService;
    @Autowired
    private IDictService dictService;
    @Autowired
    private ICircleInviteService circleInviteService;
    @Autowired
    private CircleInviteBizService circleInviteBizService;
    @Autowired
    private ICircleTopicTagService circleTopicTagService;
    @Autowired
    private ICircleTopicService circleTopicService;

    /**
     * 分页查询圈子列表
     *
     * @param circle 查询参数
     * @param page   分页参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "circle/list")
    public AjaxResult list(Circle circle, Page page, String subjectId) {
        circle.setSubjectId(subjectId);
        List<Circle> circleList = circleService.listPage(circle, page);
        if (!CollectionUtils.isEmpty(circleList)) {
            List<CircleListOutput> circleOutputList = LangUtils.transform(circleList, input -> {
                CircleListOutput listOutput = CircleListOutput.transform(input);
                if (StringUtils.isNotEmpty(input.getCircleType())) {
                    listOutput.setCircleType(getCircleType(input.getCircleType()).getLabel());
                }
                return listOutput;
            });
            return AjaxResult.success(circleOutputList, page);
        }
        return AjaxResult.success(Collections.EMPTY_LIST, page);
    }

    /**
     * 圈子基本信息
     *
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/info")
    public AjaxResult mgrList(String id, HttpServletRequest request) {
        Circle search = new Circle();
        CurrentUser curUser = currentUserBizService.getCurrentUser(request);
        search.setCreateBy(curUser.getId());
        Circle circle = circleService.get(id);

        CircleApply searchApply = new CircleApply();
        searchApply.setCheckStatus(CircleCode.Apply_STATUS_AUDITING.getCode());
        searchApply.setCircle(id);
        Long count = circleApplyService.count(searchApply);
        CircleListOutput listOutput = CircleListOutput.transform(circle);
        if (null != circle.getArea() && StringUtils.isNotBlank(circle.getArea())) {
            Area area = areaService.get(circle.getArea());
            Area parent = areaService.get(area.getParentId());
            listOutput.setAreaId(area.getId());
            listOutput.setAreaName(parent.getName() + " " + area.getName());
        }
        listOutput.setCircleTypeId(circle.getCircleType());
        listOutput.setVerifyNum(count.intValue());
        return AjaxResult.success(listOutput);
    }

    /**
     * 分页查询审核列表
     *
     * @param search 查询参数
     * @param page   分页参数
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/verifyList")
    public AjaxResult verifyList(CircleApply search, Page page, HttpServletRequest request) {
        List<CircleApply> list = circleApplyService.listPage(search, page);
        if (!CollectionUtils.isEmpty(list)) {
            List<CircleApplyOutput> outputList = LangUtils.transform(list, input -> {
                CircleApplyOutput output = CircleApplyOutput.transform(input);
                Member member = memberService.get(input.getMember());
                output.setInfo(CircleMemberOutput.transform(member));
                output.setPhone(member.getMobile());
                //设置城市名字
                if (!Strings.isNullOrEmpty(member.getCity())) {
                    Area area = areaService.get(member.getCity());
                    if (null != area) {
                        output.setCityName(area.getName());
                    }
                }
                //设置行业名字
                if (!Strings.isNullOrEmpty(member.getIndustry())) {
                    Industry industry = industryService.get(member.getIndustry());
                    if (null != industry) {
                        output.setIndustryName(industry.getName());
                    }
                }
                return output;
            });
            return AjaxResult.success(outputList, page);
        }
        return AjaxResult.success(Collections.EMPTY_LIST, page);
    }

    /**
     * 圈子类型
     *
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/tagList")
    public AjaxResult tagList() {
        Dict dict = new Dict();
        dict.setType("circle_type");
        List<Dict> list = dictService.list(dict);
        return AjaxResult.success(list);
    }

    /**
     * 创建圈子
     *
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/save")
    public AjaxResult save(Circle circle, BindingResult result, HttpServletRequest request) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
            return ajaxResult;
        }
        if (StringUtils.isNotEmpty(circle.getId())) {//编辑表单保存
            Circle circleDb = circleService.get(circle.getId());
            MyBeanUtils.copyBeanNotNull2Bean(circle, circleDb);//将编辑表单中的非\NULL值覆盖数据库记录中的值
            circle.setUpdateBy(currentUserBizService.getCurrentUser(request).getId());
            circleService.update(circleDb);
            ajaxResult.setData(circle.getId());
        } else {
            String id = circleBizService.createCircleBiz(circle, currentUserBizService.getCurrentUser(request).getId());
            ajaxResult.setData(id);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 查询加入的列表
     *
     * @param request
     * @param page
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/joinList")
    public AjaxResult joinList(Circle circle, HttpServletRequest request, Page page) {
        CurrentUser curUser = currentUserBizService.getCurrentUser(request);
        Member member = memberService.get(curUser.getId());
        List<Circle> circleList = circleBizService.joinList(circle, member, page);
        List<CircleListOutput> circleOutputList = LangUtils.transform(circleList, input -> {
            CircleListOutput listOutput = CircleListOutput.transform(input);
            //查找审核数
            if (input.getCreateBy().equals(curUser.getId())) {
                CircleApply caSearch = new CircleApply();
                caSearch.setCheckStatus(CircleCode.Apply_STATUS_AUDITING.getCode());
                caSearch.setCircle(input.getId());
                Long verifyCount = circleApplyService.count(caSearch);
                listOutput.setVerifyNum(verifyCount.intValue());
            }
            if (StringUtils.isNotEmpty(input.getCircleType())) {
                listOutput.setCircleType(getCircleType(input.getCircleType()).getLabel());
            }
            return listOutput;
        });
        return AjaxResult.success(circleOutputList, page);
    }

    /**
     * 移出圈子
     *
     * @param circleMember
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/out")
    public AjaxResult out(CircleMember circleMember, HttpServletRequest request) {
        try {
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            circleMemberBizService.out(circleMember,currentUser.getId());
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 退出圈子
     *
     * @param circleMember
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/exit")
    public AjaxResult exit(CircleMember circleMember, HttpServletRequest request) {
        try {
            circleMemberBizService.out(circleMember,null);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 圈子详情
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "circle/detail")
    public AjaxResult detail(String id, HttpServletRequest request) {
        Map<String, Object> mapret = new HashMap<String, Object>();
        Circle circle = circleService.get(id);
        CircleOutput ret = CircleOutput.transform(circle);
        if (StringUtils.isNotEmpty(circle.getCircleType())) {
            ret.setCircleType(getCircleType(circle.getCircleType()).getLabel());
        }
        mapret.put("circle", ret);
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        circleBizService.getRole(id, mapret, null == currentUser ? null : currentUser.getId());
        circleBizService.getVerifyNum(id, mapret, null == currentUser ? null : currentUser.getId());
        circleBizService.getApplyCheckStatus(id, mapret, null == currentUser ? null : currentUser.getId());
        //访问数+1
        circle.setVisitNum((circle.getVisitNum() == null ? 0 : circle.getVisitNum()) + 1);
        circleService.update(circle);
        return AjaxResult.success(mapret);
    }


    /**
     * 圈子简介
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "circle/summary")
    public AjaxResult summary(String id, HttpServletRequest request) {
        Map<String, Object> mapret = new HashMap<String, Object>();
        Circle circle = circleService.get(id);
        CircleMember search = new CircleMember();
        search.setCircle(id);
        //获取10个用户头像显示
        Page page = new Page();
        page.setPage(1);
        page.setLimit(10);
        List<CircleMember> circleMemberList = circleMemberService.listPage(search, page);
        List<Map<String, Object>> memberList = new ArrayList<Map<String, Object>>();
        for (CircleMember circleMember : circleMemberList) {
            Map<String, Object> map = new HashMap<String, Object>();
            Member member = memberService.get(circleMember.getMember());
            if (null != member) {
                map.put("id", member.getId());
                map.put("logo", member.getLogo());
                map.put("realname", member.getRealname());
                memberList.add(map);
            }
        }
        //访问数+1
        circle.setVisitNum((circle.getVisitNum() == null ? 0 : circle.getVisitNum()) + 1);
        circleService.update(circle);

        CircleOutput ret = CircleOutput.transform(circle);
        if (StringUtils.isNotEmpty(circle.getCircleType())) {
            ret.setCircleType(getCircleType(circle.getCircleType()).getLabel());
        }
        ret.setMemberList(memberList);
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        circleBizService.getRole(id, mapret, null == currentUser ? null : currentUser.getId());
        circleBizService.getVerifyNum(id, mapret, null == currentUser ? null : currentUser.getId());
        circleBizService.getApplyCheckStatus(id, mapret, null == currentUser ? null : currentUser.getId());
        mapret.put("circle", ret);

        return AjaxResult.success(mapret);
    }

    /**
     * 获取圈子权限信息
     *
     * @param id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/authInfo")
    public AjaxResult authInfo(String id, HttpServletRequest request) {
        try {
            Map<String, Object> ret = new HashedMap();
            Circle circle = circleService.get(id);
            ret.put("isOpen", circle.getIsOpen());
            ret.put("noTypeOpen", circle.getNoTypeIsOpen());
            CircleTag search = new CircleTag();
            search.setCircle(circle.getId());
            List<CircleTag> tagList = circleTagService.list(search);
            List<Map<String, Object>> tagRets = new ArrayList<Map<String, Object>>();
            for (CircleTag tag : tagList) {
                Map<String, Object> tagRet = new HashedMap();
                tagRet.put("id", tag.getId());
                tagRet.put("name", tag.getTagName());
                tagRet.put("isOpen", tag.getIsOpen());
                tagRets.add(tagRet);
            }
            ret.put("tagAuth", tagRets);
            return AjaxResult.success(ret);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置隐私权限
     *
     * @param type    类型 circle,tag
     * @param id
     * @param value
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/setAuth")
    public AjaxResult authInfo(String type, String id, Integer value, HttpServletRequest request) {
        try {
            if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(id) && value != null) {
                if (type.equals("circle")) {
                    Circle circle = circleService.get(id);
                    circle.setIsOpen(value);
                    circleService.update(circle);
                } else if (type.equals("tag")) {
                    CircleTag circleTag = circleTagService.get(id);
                    circleTag.setIsOpen(value);
                    circleTagService.update(circleTag);
                } else if (type.equals("other")) {
                    Circle circle = circleService.get(id);
                    circle.setNoTypeIsOpen(value);
                    circleService.update(circle);
                }
            } else {
                throw new BusinessException("参数错误");
            }
            return AjaxResult.success();
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 解散圈子
     *
     * @param id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "circle/dissolve", method = RequestMethod.POST)
    public AjaxResult dissolveCircle(String id, HttpServletRequest request) {
        try {
            CurrentUser curUser = currentUserBizService.getCurrentUser(request);
            Circle circle = circleService.get(id);
            if (!circle.getCreateBy().equals(curUser.getId())) {
                throw new BusinessException(PartyCode.CIRCLE_NO_CREATOR, "当前用户不是该圈子的创建者");
            }
            circleBizService.dissolveCircle(id);
            return AjaxResult.success();
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

    }

    /**
     * 设置会员为管理员
     *
     * @param mobile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "member/setMgr")
    public AjaxResult setMgr(CircleMember circleMember, String mobile) {
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("mobile", mobile);
            CircleMember byMobile = circleMemberService.getByMobile(circleMember, params);
            if (null == byMobile)
                throw new BusinessException("手机号没有查询到对应的用户");
            CircleMember admin = circleMemberBizService.setMgrBiz(byMobile.getId());
            List<ListAllMember> list = circleMemberBizService.listAll(admin, Maps.newHashMap(), null);
            return AjaxResult.success(list);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 取消会员为管理员
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "member/cancelMgr")
    public AjaxResult cancelMgr(String id) {
        try {
            circleMemberBizService.cancelMgrBiz(id);
            return AjaxResult.success();
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 查询所有圈子会员列表
     *
     * @param circleMember
     * @param tags
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "member/list")
    public AjaxResult list(CircleMember circleMember, String orderBy, Boolean noType, String tags) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("orderBy", orderBy);
        params.put("noType", noType);
        List<ListAllMember> list = new ArrayList<ListAllMember>();

        if(orderBy.equals("type")) {
            list = circleMemberBizService.listAllByType(circleMember, params, tags);
        }else{
            list = circleMemberBizService.listAll(circleMember, params, tags);
        }
        Circle circle = circleService.get(circleMember.getCircle());
        List<ListAllMember> circleOutputList = LangUtils.transform(list, input -> {
//            Map<String, Object> role = circleBizService.getRole(circleMember.getCircle(), Maps.newHashMap(), input.getId());
//            input.setRole(role);
            if (null == input.getAreaId()) {
                input.setAreaName("全国");
                input.setAreaPY("QUANGUO");
            }
            if (null == input.getIndustryId()) {
                input.setIndustryName("其他");
                input.setIndustryPY("QITA");
            }
            return input;
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("circle", CircleListOutput.transform(circle));
        map.put("list", circleOutputList);
        return AjaxResult.success(map);
    }

    @ResponseBody
    @RequestMapping(value = "member/getBusinessCard")
    public AjaxResult getBusinessCard(CircleMember circleMember, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(circleMember.getCircle()) || Strings.isNullOrEmpty(circleMember.getMember())) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }
        try {
            CurrentUser curUser = currentUserBizService.getCurrentUser(request);
            Member curMember = null;
            if (curUser != null) {
                curMember = memberService.get(curUser.getId());
            }
            Member member = circleBizService.getBusinessCarInfo(circleMember, curMember);
            BusinessCardOutput output = BusinessCardOutput.transform(member);
            // 设置行业名
            if (!Strings.isNullOrEmpty(member.getIndustry())) {
                Industry industry = industryService.get(member.getIndustry());
                if (null != industry) {
                    output.setIndustryName(industry.getName());
                }
            }
            // 设置城市名字
            if (!Strings.isNullOrEmpty(member.getCity())) {
                Area area = areaService.get(member.getCity());
                if (null != area) {
                    output.setCityName(area.getName());
                }
            }
            return AjaxResult.success(output);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 用户标签列表
     *
     * @param circleTag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "tag/list")
    public AjaxResult list(CircleTag circleTag) {
        List<CircleTag> tags = circleTagBizService.list(circleTag);
        List<CircleTagOutput> circleOutputList = new ArrayList<CircleTagOutput>();
        if (!CollectionUtils.isEmpty(tags)) {
            circleOutputList = LangUtils.transform(tags, input -> {
                CircleTagOutput listOutput = CircleTagOutput.transform(input);
                return listOutput;
            });
        }
        return AjaxResult.success(circleOutputList);
    }

    /**
     * 添加用户标签
     *
     * @param tag
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "tag/add")
    public AjaxResult addTag(CircleTag tag) {
        String tagId = circleTagService.insert(tag);
        tag = circleTagService.get(tagId);
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("id", tag.getId());
        ret.put("tagName", tag.getTagName());
        return AjaxResult.success(ret);
    }

    /**
     * 删除用户标签
     *
     * @param circleTag
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "tag/remove")
    public AjaxResult removeTag(CircleTag circleTag) {
        circleTagBizService.remove(circleTag);
        return AjaxResult.success();
    }

    /**
     * 保存标签用户关系
     *
     * @param memberTag
     * @param tags
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "tag/saveTagMember")
    public AjaxResult saveTagMember(CircleMemberTag memberTag, String tags) {
        circleMemberTagBizService.saveBiz(memberTag, tags);
        List<Map<String,Object>> circleOutputList = new ArrayList<Map<String,Object>>();
        if (!Strings.isNullOrEmpty(tags)) {
            String[] tagArray = tags.split(",");
            for(String tag:tagArray){
                CircleTag circleTag = circleTagService.get(tag);
                if(null != circleTag) {
                    Map<String,Object> map = Maps.newHashMap();
                    map.put("id",circleTag.getId());
                    map.put("name",circleTag.getTagName());
                    circleOutputList.add(map);
                }
            }
        }
        return AjaxResult.success(circleOutputList);
    }

    /**
     * 审核拒绝
     *
     * @param id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "apply/reject")
    public AjaxResult reject(String id, HttpServletRequest request) {
        CurrentUser curUser = currentUserBizService.getCurrentUser(request);
        circleApplyBizService.reject(id, curUser.getId());
        return AjaxResult.success();
    }

    /**
     * 审核通过
     *
     * @param id
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "apply/pass")
    public AjaxResult pass(String id, HttpServletRequest request) {
        CurrentUser curUser = currentUserBizService.getCurrentUser(request);
        circleApplyBizService.pass(id, curUser.getId());
        return AjaxResult.success();
    }

    /**
     * 申请加入圈子
     *
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "circle/apply")
    public AjaxResult apply(CircleApply apply,String verifyCode, Member member, BindingResult result, HttpServletRequest request) {
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
//        MemberOutput memberOutput = applyInit(member, request);
        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        MemberOutput memberOutput = null;
        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (CurrentUser.isThirdparty(currentUser)) {
            //验证码验证
            boolean verifyResult = verifyCodeBizService.verify(member.getMobile(), verifyCode);
            if (verifyResult) {
                //绑定手机号
                memberOutput = memberBizService.bindPhone(member.getMobile(), member.getRealname(), member.getCompany(), member.getJobTitle(), member.getIndustry(), request);
                //当前登录用户需重新赋值
                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
            }
        }
        //如果字段为空 更新用户信息
        Member curMember = memberBizService.updateImportantInfo(member, memberService.get(currentUser.getId()));
        memberOutput = memberBizService.getLoginMember(curMember);

        try {
            circleApplyBizService.getApplyData(currentUser.getId(), apply);

            Map map = new HashMap();
            map.put("member", memberOutput);
            return AjaxResult.success(map);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 点击邀请
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "invite/init")
    public AjaxResult initInvite(CircleInvite circleInvite, HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        circleInvite.setCreateBy(currentUserBizService.getCurrentUser(request).getId());
        if (circleInvite.getIsVerify() != null && circleInvite.getIsVerify() == 0) {
            //设置审核过期时间24小时
            Date date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果
            circleInvite.setEndTime(date);
        }
        String id = circleInviteService.insert(circleInvite);
        ajaxResult.setData(id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 判断是否需要审核
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "invite/isVerify")
    public AjaxResult isVerify(String id, HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        HashMap<Object, Object> ret = Maps.newHashMap();
        try {
            CircleInvite circleInvite = circleInviteService.get(id);
            Boolean isVerify = circleInviteBizService.getIsVerify(circleInvite, currentUserBizService.getCurrentUser(request).getId());
            ret.put("isVerify", isVerify);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
        ajaxResult.setData(ret);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 邀请详情
     */
    @ResponseBody
    @RequestMapping(value = "invite/detail")
    public AjaxResult inviteDetail(String id, HttpServletRequest request) {
        CircleInvite circleInvite = circleInviteService.get(id);
        Circle circle = circleService.get(circleInvite.getCircle());
        CircleOutput ret = CircleOutput.transform(circle);
        if (StringUtils.isNotEmpty(circle.getCircleType())) {
            ret.setCircleType(getCircleType(circle.getCircleType()).getLabel());
        }
        Map<String, Object> mapRet = Maps.newHashMap();
        mapRet.put("circle", circle);
        mapRet.put("invite", circleInvite);
        Map<String, Object> creator = Maps.newHashMap();
        Member member = memberService.get(circleInvite.getCreateBy());
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        creator.put("logo", member.getLogo());
        creator.put("name", member.getRealname());
        mapRet.put("creator", creator);
        return AjaxResult.success(mapRet);
    }

    /**
     * 邀请加入圈子
     *
     * @param iId
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "invite/join")
    public AjaxResult inviteJoin(String iId, HttpServletRequest request) {
        try {
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            CircleInvite invite = circleInviteBizService.join(iId, currentUser.getId());
            return AjaxResult.success();
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }

    }

    /**
     * 邀请加入圈子完善信息
     *
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "invite/apply")
    public AjaxResult inviteApply(String iId,String verifyCode, @Validated Member member, BindingResult result, HttpServletRequest request) {
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        MemberOutput memberOutput = null;

        //如果当前登录用户是第三方授权用户，且手机号为空，则需要绑定手机号
        if (CurrentUser.isThirdparty(currentUser)) {
            //验证码验证
            boolean verifyResult = verifyCodeBizService.verify(member.getMobile(), verifyCode);
            if (verifyResult) {
                //绑定手机号
                memberOutput = memberBizService.bindPhone(member.getMobile(), member.getRealname(), member.getCompany(), member.getJobTitle(), member.getIndustry(), request);
                //当前登录用户需重新赋值
                currentUser = currentUserBizService.getCurrentUserByToken(memberOutput.getToken());
            }
        }
        //如果字段为空 更新用户信息
        Member curMember = memberBizService.updateImportantInfo(member, memberService.get(currentUser.getId()));
        memberOutput = memberBizService.getLoginMember(curMember);

        try {
            Map map = new HashMap();
            CircleInvite invite = circleInviteBizService.join(iId, currentUser.getId());
            map.put("member", memberOutput);
            map.put("cId", invite.getCircle());
            return AjaxResult.success(map);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
    }




    private Dict getCircleType(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        Dict dict = new Dict();
        dict.setType("circle_type");
        dict.setValue(id);
        List<Dict> list = dictService.list(dict);
        if (null != list && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }


}
