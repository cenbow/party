package com.party.admin.web.controller.circle;

import com.party.admin.biz.dynamic.DynamicBizService;
import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.circle.TopicOutput;
import com.party.admin.web.dto.output.dynamic.DynamicOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.CircleTopicTag;
import com.party.core.model.circle.TopicMap;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.dynamic.Dypics;
import com.party.core.model.member.Member;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.ICircleTopicService;
import com.party.core.service.circle.ICircleTopicTagService;
import com.party.core.service.circle.biz.CircleTopicBizService;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.IDypicsService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.service.member.IMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/circle/topic")
public class CircleTopicController {

    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private IDypicsService dypicsService;

    @Autowired
    private DynamicBizService dynamicBizService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private ICircleService circleService;

    @Autowired
    private ICircleTopicService circleTopicService;

    @Autowired
    private ICircleTopicTagService circleTopicTagService;

    @Autowired
    private CircleTopicBizService circleTopicBizService;

    @Autowired
    private DynamicBaseBizService dynamicBaseBizService;

    /**
     * 话题列表
     *
     * @param dynamic
     * @param page
     * @param commonInput
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView topicList(CircleTopic circleTopic, Dynamic dynamic, Page page, CommonInput commonInput) {
        ModelAndView mv = new ModelAndView("circle/topicList");
        Map<String, Object> params = CommonInput.appendParams(commonInput);
        mv.addObject("input", commonInput);
        circleTopic.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        List<TopicMap> dynamics = dynamicService.listCircleTopicPage(circleTopic,dynamic, params, page);
        List<TopicOutput> dynamicOutputs = transformTopicOutput(dynamics);
        CircleTopicTag search = new CircleTopicTag();
        search.setCircle(circleTopic.getCircle());
        List<CircleTopicTag> tags = circleTopicTagService.list(search);
        mv.addObject("tags", tags);
        mv.addObject("dynamic", dynamic);
        mv.addObject("dynamics", dynamicOutputs);
        mv.addObject("circle", circleService.get(circleTopic.getCircle()));
        mv.addObject("page", page);
        mv.addObject("topic", circleTopic);
        return mv;
    }

    /**
     * 跳转至发布 话题
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "form")
    public ModelAndView dynamicForm(String id, String cId,String mId) {
        ModelAndView mv = new ModelAndView("circle/topicForm");
        if (StringUtils.isNotEmpty(id)) {
            Dynamic dynamic = dynamicService.get(id);
            Member member = memberService.get(dynamic.getCreateBy());
            mv.addObject("member", member);
            mv.addObject("dynamic", dynamic);
            List<Dypics> dypics = dypicsService.findByDynamicId(id, null);
            mv.addObject("dypics", dypics);
            CircleTopic ctSearch = new CircleTopic();
            ctSearch.setCircle(cId);
            ctSearch.setDynamic(id);
            List<CircleTopic> list = circleTopicService.list(ctSearch);
            if (null != list && list.size() > 0) {
                mv.addObject("topic", list.get(0));
            }
        }else{
            Member member = memberService.get(mId);
            mv.addObject("member", member);
        }

        mv.addObject("circle", circleService.get(cId));
        CircleTopicTag search = new CircleTopicTag();
        search.setCircle(cId);
        List<CircleTopicTag> tags = circleTopicTagService.list(search);
        mv.addObject("tags", tags);
        return mv;
    }

    /**
     * 置顶
     *
     * @param topic
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "toTop")
    public AjaxResult toTop(CircleTopic topic) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        circleTopicBizService.toTopBiz(topic);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
    /**
     * 取消置顶
     *
     * @param topic
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "cancleTop")
    public AjaxResult cancleTop(CircleTopic topic) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        circleTopicBizService.cancleTopBiz(topic);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
    /**
     * 保存
     *
     * @param dynamic
     * @param result
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(String dyId, Dynamic dynamic, CircleTopic topic, BindingResult result) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        // 数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErros = result.getAllErrors();
            ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
            return ajaxResult;
        }
        dynamic.setId(dyId);
        dynamicBizService.saveBizTopic(dynamic, topic);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return new AjaxResult(false);
            }
            circleTopicBizService.delBiz(id);
            return new AjaxResult(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxResult(false);
    }

    /**
     * 异步删除动态图片
     */
    @ResponseBody
    @RequestMapping(value = "delDynamicPic")
    public AjaxResult delDynamicPic(String url, String dynamicId) {
        dynamicBaseBizService.delDynamicPic(url, dynamicId);
        return new AjaxResult(true);
    }


    /**
     * 创建话题输出视图
     * @param dynamics
     * @return
     */
    private List<TopicOutput> transformTopicOutput(List<TopicMap> dynamics) {
        List<TopicOutput> outputs = LangUtils.transform(dynamics, input -> {
            DynamicOutput dynamicOutput = dynamicBizService.transformCommon(input);
            TopicOutput output = TopicOutput.transform(dynamicOutput);
            output.setTagName(input.getTagName());
            output.setIsTop(input.getIsTop());
            return output;
        });
        return outputs;
    }
}
