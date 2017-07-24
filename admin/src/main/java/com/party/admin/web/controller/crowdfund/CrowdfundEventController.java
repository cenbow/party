package com.party.admin.web.controller.crowdfund;

import com.google.common.base.Strings;
import com.party.admin.biz.activity.ActivityBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.CrowdfundEvent;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.circle.biz.CircleBizService;
import com.party.core.service.circle.biz.CircleMemberBizService;
import com.party.core.service.crowdfund.ICrowdfundEventService;
import com.party.core.service.member.IMemberActService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 众筹事件控制器
 * Created by wei.li
 *
 * @date 2017/4/26 0026
 * @time 14:04
 */

@Controller
@RequestMapping(value = "crowdfund/event")
public class CrowdfundEventController {

    @Autowired
    private ICrowdfundEventService crowdfundEventService;
    
    @Autowired
    private IActivityService activityService;
    
    @Autowired
	private IMemberActService memberActService;
    
	@Autowired
	private ActivityBizService activityBizService;
	@Autowired
	private CircleMemberBizService circleMemberBizService;
	@Autowired
	private CircleBizService circleBizService;

	protected static Logger logger = LoggerFactory.getLogger(CrowdfundEventController.class);

    /**
     * 众筹事件列表
     * @param crowdfundEvent 众筹事件
     * @param page 分页参数
     * @return 事件列表
     */
    @RequestMapping(value = "list")
    public ModelAndView list(CrowdfundEvent crowdfundEvent, Page page){
        ModelAndView modelAndView = new ModelAndView("crowdfund/eventList");
        List<CrowdfundEvent> crowdfundEventList = crowdfundEventService.listPage(crowdfundEvent, page);
        modelAndView.addObject("list", crowdfundEventList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 众筹事件视图
     * @param id 事件编号
     * @return 众筹事件
     */
    @RequestMapping(value = "view")
    public ModelAndView view(String id){
        ModelAndView modelAndView = new ModelAndView("crowdfund/eventView");
        CrowdfundEvent crowdfundEvent = new CrowdfundEvent();
        if (!Strings.isNullOrEmpty(id)){
            crowdfundEvent = crowdfundEventService.get(id);
        }
        modelAndView.addObject("event", crowdfundEvent);
        return modelAndView;
    }


    /**
     * 保存众筹
     * @param crowdfundEvent 众筹事件
     * @return 保存结果
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxResult save(CrowdfundEvent crowdfundEvent){
        Member member = RealmUtils.getCurrentUser();
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(crowdfundEvent.getId())){
            crowdfundEvent.setCreateBy(member.getId());
            crowdfundEvent.setUpdateBy(member.getId());
            crowdfundEventService.insert(crowdfundEvent);
        }
        else {
            crowdfundEventService.update(crowdfundEvent);
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 删除众筹事件
     * @param id 事件编号
     * @return 删除结果
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public AjaxResult delete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        crowdfundEventService.delete(id);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
    
    /**
	 * 创建业务圈子
	 * 
	 * @param actId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createCircle")
	public AjaxResult createCircle(String eventId, String type) {
		CrowdfundEvent crowdfundEvent = crowdfundEventService.get(eventId);
		Activity activity = new Activity();
		activity.setEventId(eventId);
		List<Activity> activities = activityService.listPage(activity, null, null);
		List<MemberAct> memberActs = new ArrayList<MemberAct>();
		Activity publishActivity = new Activity();
		for (Activity act : activities) {
			if (act.getMember().equals(act.getPublisher())) {
				publishActivity = activityService.get(act.getId());
			}
			List<MemberAct> results = new ArrayList<MemberAct>();
			MemberAct memberAct = new MemberAct();
			memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
			memberAct.setActId(act.getId());
			if (type.equals("1")) { // 已成功
				memberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode());
				results = memberActService.list(memberAct);
			} else if (type.equals("2")) { // 全部
				Set<Integer> status = new HashSet<Integer>();
				status.add(ActStatus.ACT_STATUS_CANCEL.getCode());
				status.add(ActStatus.ACT_STATUS_NO_JOIN.getCode());
				results = memberActService.getSuccessMemberAct(memberAct, status);
			}

			memberActs.addAll(results);
		}

		List<String> memberIds = new ArrayList<String>();
		memberIds.add(publishActivity.getMember());

		String remarks = crowdfundEvent.getName() + "——" + (type.equals("1") ? "已众筹成功人员圈子" : "全部发起众筹人员圈子");
		publishActivity.setRemarks(remarks);
		publishActivity.setMember(crowdfundEvent.getCreateBy());
		String circleId = circleBizService.createCircleBusiness(eventId, publishActivity, type);
		circleMemberBizService.circleMemberManage(memberActs, circleId, type, memberIds,RealmUtils.getCurrentUser().getId());
		return AjaxResult.success();
	}
}
