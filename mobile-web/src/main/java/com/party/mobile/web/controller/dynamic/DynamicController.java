package com.party.mobile.web.controller.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.TopicMap;
import com.party.core.model.dynamic.DynamicType;
import com.party.core.model.fans.Fans;
import com.party.core.service.circle.biz.CircleBizService;
import com.party.core.service.circle.biz.CircleTopicBizService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.service.dynamic.impl.DynamicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.member.Member;
import com.party.core.service.member.impl.MemberService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.dynamic.DynamicBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.dynamic.output.BusinessCardOutput;
import com.party.mobile.web.dto.dynamic.output.DyCommentOutput;
import com.party.mobile.web.dto.dynamic.output.DyLoveOutput;
import com.party.mobile.web.dto.dynamic.output.DyMemberOutput;
import com.party.mobile.web.dto.dynamic.output.DynamicOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.common.utils.PartyCode;

/**
 * 动态控制层
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:06 16/11/8
 * @Modified by:
 */
@Controller
@RequestMapping(value = "/dynamic/dynamic")
public class DynamicController {

    @Autowired
    private DynamicBizService dynamicBizService;
    @Autowired
    private CurrentUserBizService currentUserBizService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private CircleTopicBizService circleTopicBizService;
    @Autowired
    private DynamicBaseBizService dynamicBaseBizService;
    @Autowired
    private CircleBizService circleBizService;

    /**
     * 获取动态详情
     *
     * @param id      主键id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDetails")
    public AjaxResult getDetails(String id, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }

        try {
            Dynamic dynamic = dynamicService.get(id);
            if (dynamic.getDynamicType().equals(DynamicType.COMMUNITY.getCode())) {
                DynamicOutput output = dynamicBizService.getDetails(dynamic, request);
                return AjaxResult.success(output);
            } else {
                DynamicOutput output = null;
                CircleTopic search = new CircleTopic();
                search.setDynamic(dynamic.getId());
                Page page = new Page();
                page.setLimit(1);
                List<TopicMap> dynamics = dynamicService.listCircleTopicPage(search, new Dynamic(), Maps.newHashMap(), page);
                if (null != dynamics && dynamics.size() > 0) {
                    TopicMap dy = dynamics.get(0);
                    output = dynamicBizService.getTopicDetails(dy, request);
                    CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
                    if(null != currentUser && !Strings.isNullOrEmpty(dy.getCircle())){
                        output.setRole(circleBizService.getRole(dy.getCircle(),Maps.newHashMap(),currentUser.getId()));
                    }
                }

                return AjaxResult.success(output);
            }

        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

    }

    /**
     * 热门动态列表，按照时间降序排序
     *
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listHotDynamic")
    public AjaxResult listHotDynamic(Page page, HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        //设置过滤条件
        Dynamic dynamic = new Dynamic();
        dynamic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        dynamic.setDynamicType(DynamicType.COMMUNITY.getCode());
        List<DynamicOutput> dynamicOutputList = dynamicBizService.listDynamicNew(dynamic, null,page, currentUser == null ? "" : currentUser.getId());

        return AjaxResult.success(dynamicOutputList, page);
    }

    /**
     * 达人动态列表，按时间降序排序
     *
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listExpertDynamicNew")
    public AjaxResult listExpertDynamic(Page page, HttpServletRequest request) {
        //设置过滤条件
        Member member = new Member();
        member.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        member.setIsExpert(1);
        List<List<DynamicOutput>> dynamicOutputList = dynamicBizService.listExpertDynamic(member, page, request);
        return AjaxResult.success(dynamicOutputList, page);
    }

    /**
     * 当前用户关注的人的动态列表，按时间降序排序
     *
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listFocusDynamic")
    public AjaxResult listFocusDynamic(Page page, HttpServletRequest request) {
        //设置过滤条件
        Dynamic dynamic = new Dynamic();
        dynamic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        dynamic.setDynamicType(DynamicType.COMMUNITY.getCode());
        List<DynamicOutput> dynamicOutputList = new ArrayList<>();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        try {
            if(currentUser == null){
                throw new BusinessException(PartyCode.LIST_FOCUS_DYNAMIC_NOT_LOGIN_ERROR,"没有登录，改为推荐达人动态列表");
            }
            Map<String,Object> params = Maps.newHashMap();
            Fans fans = new Fans();
            fans.setFollowerId(currentUser.getId());
            params.put("fans",fans);
            dynamicOutputList = dynamicBizService.listDynamicNew(dynamic,params, page, currentUser.getId());
            if(dynamicOutputList.size() == 0){
                throw new BusinessException(PartyCode.LIST_FOCUS_DYNAMIC_ERROR,"没有关注的人，改为推荐达人动态列表");
            }
        } catch (BusinessException be) {
            //没有关注，查达人
            Map<String,Object> params = Maps.newHashMap();
            params.put("isExpert",1);
            dynamicOutputList = dynamicBizService.listDynamicNew(dynamic,params, page, currentUser == null ?"":currentUser.getId());
            return AjaxResult.error(dynamicOutputList, be.getCode(), be.getMessage());
        }

        return AjaxResult.success(dynamicOutputList, page);
    }

    /**
     * 根据会员主键查看该会员的动态列表
     *
     * @param memberId 要查看的会员的主键memberId
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberDynamic")
    public AjaxResult listMemberDynamic(String memberId, Page page, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(memberId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        //设置过滤条件
        Dynamic dynamic = new Dynamic();
        dynamic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        dynamic.setCreateBy(memberId);
        List<DynamicOutput> dynamicOutputList = dynamicBizService.listDynamicNew(dynamic,null, page, currentUser == null ? "" : currentUser.getId());

        return AjaxResult.success(dynamicOutputList, page);
    }

    /**
     * 根据会员主键查看该会员的图片列表
     *
     * @param page
     * @param memberId 会员主键id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberPic")
    public AjaxResult listMemberPic(String memberId, Page page) {
        if (Strings.isNullOrEmpty(memberId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }
        List<String> picList = dynamicBizService.getPicList(memberId, page);
        return AjaxResult.success(picList, page);
    }


    /**
     * 动态点赞列表
     *
     * @param id      动态主键id
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listDynamicLove")
    public AjaxResult listDynamicLove(String id, Page page, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        List<DyLoveOutput> outputList = dynamicBizService.listDynamicLove(id, page, request);

        return AjaxResult.success(outputList, page);
    }

    /**
     * 动态评论列表
     *
     * @param id      动态主键id
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listDynamicComment")
    public AjaxResult listDynamicComment(String id, Page page, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        List<DyCommentOutput> outputList = dynamicBizService.listDynamicComment(id, page, request);

        return AjaxResult.success(outputList, page);
    }

    /**
     * 根据会议id，查询该会员的粉丝列表
     *
     * @param id      会员主键id
     * @param page    分页参数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listFans")
    public AjaxResult listFans(String id, Page page, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }

        List<DyMemberOutput> outputList = dynamicBizService.listFans(id, page, request);
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = memberService.get(id);
        map.put("member", member);
        map.put("outputList", outputList);
        return AjaxResult.success(map, page);
    }

    /**
     * 根据会员id，查询该会员的关注列表
     *
     * @param id      会员主键id
     * @param page    分页参数
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listFocus")
    public AjaxResult listFocus(String id, Page page, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }

        List<DyMemberOutput> outputList = dynamicBizService.listFocus(id, page, request);
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = memberService.get(id);
        map.put("member", member);
        map.put("outputList", outputList);
        return AjaxResult.success(map, page);
    }


    @ResponseBody
    @RequestMapping(value = "/getBusinessCard")
    public AjaxResult getBusinessCard(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }
        try {
            BusinessCardOutput businessCard = dynamicBizService.getBusinessCarInfo(id);
            return AjaxResult.success(businessCard);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/memberInfo")
    public AjaxResult memberInfo(String memberId, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(memberId)) {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数不合法");
        }
        try {
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            DyMemberOutput dyMember = dynamicBizService.getDyMember(memberId, currentUser != null ? currentUser.getId() : null);
            return AjaxResult.success(dyMember);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 删除
     *
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "delete")
    public AjaxResult delete(Dynamic dynamic) {
        try {
            if (StringUtils.isEmpty(dynamic.getId())) {
                return new AjaxResult(false);
            }
            if (dynamic.getDynamicType().equals(DynamicType.COMMUNITY.getCode())) {
                dynamicBaseBizService.delBiz(dynamic.getId());
            } else if (dynamic.getDynamicType().equals(DynamicType.CIRCLE.getCode())) {
                circleTopicBizService.delBiz(dynamic.getId());
            } else {
                throw new BusinessException("动态类型不能识别");
            }
            return new AjaxResult(true);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult(false);
    }
}
