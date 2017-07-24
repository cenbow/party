package com.party.mobile.web.controller.circle;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.circle.*;
import com.party.core.model.city.Area;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.system.Dict;
import com.party.core.service.circle.*;
import com.party.core.service.circle.biz.*;
import com.party.core.service.circle.impl.CircleService;
import com.party.core.service.circle.impl.CircleTagService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.subject.impl.SubjectService;
import com.party.core.service.system.IDictService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.dynamic.DynamicBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.utils.MyBeanUtils;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.circle.input.TopicInput;
import com.party.mobile.web.dto.circle.output.*;
import com.party.mobile.web.dto.dynamic.output.BusinessCardOutput;
import com.party.mobile.web.dto.dynamic.output.DynamicOutput;
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
public class CircleTopicController {
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
    private ICircleTopicTagService circleTopicTagService;
    @Autowired
    private ICircleTopicService circleTopicService;
    @Autowired
    private IDynamicService dynamicService;
    @Autowired
    private DynamicBizService dynamicBizService;
    @Autowired
    private CircleTopicBizService circleTopicBizService;

    /**
     * 话题列表
     *
     * @param id      圈子id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "topic/list")
    public AjaxResult topicList(String id, Page page, HttpServletRequest request) {
        try {
            CircleTopic circleTopic = new CircleTopic();
            circleTopic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
            circleTopic.setCircle(id);
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            List<DynamicOutput> dynamicOutputs = dynamicBizService.listTopicNew(circleTopic,null,  null, page, null == currentUser ? "" : currentUser.getId());
            return AjaxResult.success(dynamicOutputs);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
    }


    /**
     * 话题标签列表
     *
     * @param id      圈子id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "topic/tagList")
    public AjaxResult tagList(String id, HttpServletRequest request) {
        try {
            CircleTopicTag circleTopicTag = new CircleTopicTag();
            circleTopicTag.setCircle(id);
            List<CircleTopicTag> list = circleTopicTagService.list(circleTopicTag);
            if (!CollectionUtils.isEmpty(list)) {
                List<CircleTopicTagOutput> outputList = LangUtils.transform(list, input -> {
                    CircleTopicTagOutput output = CircleTopicTagOutput.transform(input);
                    return output;
                });
                return AjaxResult.success(outputList);
            }
            return AjaxResult.success(Collections.EMPTY_LIST);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 保存话题
     *
     * @param input
     * @param result
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/topic/createTopic", method = RequestMethod.POST)
    public AjaxResult createTopic(@Validated TopicInput input, BindingResult result, HttpServletRequest request) throws Exception {
        //数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            //TODO 验证结果返回给前端
            allErros.forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        try {

            dynamicBizService.saveTopic(input, request);

            return AjaxResult.success();

        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

    }

    /**
     * 置顶列表
     *
     * @param id      圈子id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "topic/getTop")
    public AjaxResult getTop(String id,Page page, HttpServletRequest request) {
        try {
            CircleTopic circleTopic = new CircleTopic();
            circleTopic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
            circleTopic.setCircle(id);
            circleTopic.setIsTop(YesNoStatus.YES.getCode());
            List<TopicMap> dynamics = dynamicService.listCircleTopicPage(circleTopic, new Dynamic(), Maps.newHashMap(), page);
            CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
            List<DynamicOutput> outputs = dynamicBizService.getTopicList(dynamics, null == currentUser? "":currentUser.getId());
            return AjaxResult.success(outputs);
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 设置置顶
     *
     * @param topic  话题
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "topic/setUp")
    public AjaxResult setUp(CircleTopic topic, HttpServletRequest request) {
        try {
            List<CircleTopic> topics = circleTopicService.list(topic);
            if(null != topics && topics.size()>0){
                topic = topics.get(0);
            }
            circleTopicBizService.toTopBiz(topic);
            return AjaxResult.success();
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
    }

    /**
     * 取消置顶
     *
     * @param topic  话题
     * @param request
     * @return
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "topic/cancelUp")
    public AjaxResult cancelUp(CircleTopic topic, HttpServletRequest request) {
        try {
            List<CircleTopic> topics = circleTopicService.list(topic);
            if(null != topics && topics.size()>0){
                topic = topics.get(0);
            }
            circleTopicBizService.cancleTopBiz(topic);
            return AjaxResult.success();
        } catch (BusinessException e) {
            return AjaxResult.error(e.getCode(), e.getMessage());
        }
    }
}
