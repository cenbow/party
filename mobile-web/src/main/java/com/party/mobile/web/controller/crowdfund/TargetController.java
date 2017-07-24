package com.party.mobile.web.controller.crowdfund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;
import com.party.authorization.annotation.Authorization;
import com.party.core.exception.BusinessException;
import com.party.core.model.member.Member;
import com.party.core.service.member.IMemberService;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.PartyCode;
import com.party.common.utils.StringUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.member.MemberMerchant;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.mobile.biz.crowdfund.TargetBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.crowdfund.output.TargetOutput;

/**
 * 众筹目标控制器
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 10:17
 */

@Controller
@RequestMapping(value = "/crowdfund/target")
public class TargetController {
	
	// 公众号编号
	@Value("#{pay_wechat_wwz['wechat.wwz.appId']}")
	private String appId;

    @Autowired
    private TargetBizService targetBizService;
    
    @Autowired
    private ITargetProjectService targetProjectService;
    
    @Autowired
    private IActivityService activityService;

    @Autowired
    private IMemberMerchantService memberMerchantService;

    @Autowired
    private IMemberService memberService;

    /**
     * 获取当前用户的众筹目标
     * @param page 分页参数
     * @param status 状态(0:进行中, 1:已经截止)
     * @param id 发布者编号
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public AjaxResult list(Page page , Integer status,String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        List<TargetOutput> targetOutputList = null;
        try {
            targetOutputList = targetBizService.list(page, status, id);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(targetOutputList);
        return ajaxResult;
    }
    
    /**
     * 根据项目ID或者众筹ID获取发布者商户信息
     * @param projectId
     * @param targetId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getTargetByProject")
    public AjaxResult getTargetByProject(String projectId, String targetId) {
    	//参数验证
        if (Strings.isNullOrEmpty(projectId) && Strings.isNullOrEmpty(targetId)){
        	return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "projectId和targetId不能同时为空");
        }
        
        String memberId = null;
        
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(projectId)) {
        	TargetProject targetProject = targetProjectService.findByProjectId(projectId);
        	if (targetProject.getType().equals(Constant.CROWD_FUND_TYPE_ACTIVITY)) {
        		Activity activity = activityService.get(targetProject.getTargetId());
        		memberId = activity.getMember();
        	}
		}
        if (StringUtils.isNotEmpty(targetId)) {
        	Activity activity = activityService.get(targetId);
        	memberId = activity.getMember();
		}
        
        if (StringUtils.isNotEmpty(memberId)) {
			MemberMerchant memberMerchant = memberMerchantService.findByMemberId(memberId);
			if (memberMerchant != null && memberMerchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
				String appid = memberMerchant.getOfficialAccountId();
				data.put("appId", appid);
			} else {
				data.put("appId", appId);
			}
			data.put("memberId", memberId);
		}
        
		return AjaxResult.success(data);
    }
    
    /**
     * 根据memberId获取商户信息
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAppIdByMember")
    public AjaxResult getAppIdByMember(String memberId) {
    	//参数验证
        if (Strings.isNullOrEmpty(memberId)){
        	return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "memberId不能为空");
        }
                
        if (StringUtils.isNotEmpty(memberId)) {
			MemberMerchant memberMerchant = memberMerchantService.findByMemberId(memberId);
			if (memberMerchant != null && memberMerchant.getOpenStatus().equals(YesNoStatus.YES.getCode())) {
				String appid = memberMerchant.getOfficialAccountId();
				return AjaxResult.success((Object)appid);
			} else {
				return AjaxResult.success((Object)appId);
			}
		}
		return null;
    }

    @ResponseBody
    @RequestMapping("/getTargetCreator")
    public AjaxResult getTargetCreator(String id) {
        try {
            Member member = memberService.get(id);
            Map<String,Object> map = Maps.newHashMap();
            map.put("authorName",member.getRealname());
            return AjaxResult.success(map);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }
}
