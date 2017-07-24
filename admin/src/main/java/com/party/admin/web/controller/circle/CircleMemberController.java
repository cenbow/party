package com.party.admin.web.controller.circle;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.system.MemberBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleMember;
import com.party.core.model.circle.CircleMemberTag;
import com.party.core.model.circle.CircleTag;
import com.party.core.model.city.Area;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.service.circle.ICircleMemberService;
import com.party.core.service.circle.ICircleMemberTagService;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.ICircleTagService;
import com.party.core.service.circle.biz.CircleMemberBizService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.member.IIndustryService;

/**
 * 圈子成员
 */
@Controller
@RequestMapping(value = "/circle/member")
public class CircleMemberController {

	@Autowired
	private ICircleService circleService;
	@Autowired
	private ICircleMemberService circleMemberService;
	@Autowired
	private CircleMemberBizService circleMemberBizService;
	@Autowired
	private ICircleMemberTagService circleMemberTagService;
	@Autowired
	private ICircleTagService circleTagService;
	@Autowired
	private IIndustryService industryService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private MemberBizService memberBizService;

	protected static Logger logger = LoggerFactory.getLogger(CircleMemberController.class);

	/**
	 * 圈子成员列表页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "list", "" })
	public ModelAndView list(Page page, String id, String realName, String mobile, String tagId, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("circle/memberList");
		CircleMember cMember = new CircleMember();
		cMember.setCircle(id);
		Map<String, Object> searchMap = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		searchMap.put("realName", realName);
		searchMap.put("mobile", mobile);
		searchMap.put("tagId", tagId);
		
		List<Map<String, Object>> circleMembers = circleMemberService.webListPage(cMember, searchMap, null);
		int totalCount = circleMembers.size();
		
		page.setLimit(20);
		circleMembers = circleMemberService.webListPage(cMember, searchMap, page);
		for (Map<String, Object> map : circleMembers) {
			CircleMemberTag t = new CircleMemberTag();
			t.setCircle(id);
			t.setMember(map.get("mId").toString());
			List<CircleMemberTag> circleMemberTags = circleMemberTagService.list(t);
			
			List<String> tagNameList = (List<String>) CollectionUtils.collect(circleMemberTags, new Transformer() {

				@Override
				public Object transform(Object input) {
					CircleMemberTag memberTag = (CircleMemberTag) input;
					CircleTag circleTag = circleTagService.get(memberTag.getTag());
					return circleTag.getTagName();
				}
			});
			
			String tagNames = tagNameList.toString().replace("[", "").replace("]", "");
			map.put("tagNames", tagNames);
		}
		
		CircleTag t = new CircleTag();
		t.setCircle(id);
		List<CircleTag> circleTags = circleTagService.list(t);
		mv.addObject("circleTags", circleTags);

		Circle circle = circleService.get(id);

		mv.addObject("list", circleMembers);
		mv.addObject("circle", circle);
		page.setTotalCount(totalCount);
		mv.addObject("page", page);
		mv.addObject("realName", realName);
		mv.addObject("mobile", mobile);
		mv.addObject("tagId", tagId);
		return mv;
	}

	/**
	 * 查看，增加，编辑圈子成员表单页面
	 */
	@RequestMapping(value = "form")
	public ModelAndView form(String id) {
		ModelAndView mv = new ModelAndView("circle/addCMember");
		Circle circle = circleService.get(id);
		mv.addObject("circle", circle);
		
		Industry industry = new Industry();
		industry.setParentId("0");
		List<Industry> industries = industryService.list(industry);
		mv.addObject("industries", industries);

		Area area = new Area();
		area.setParentId("1");
		List<Area> areas = areaService.list(area);
		mv.addObject("areas", areas);
		
		return mv;
	}

	/**
	 * 保存圈子成员
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(String cId, String ids, Member member) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (StringUtils.isNotEmpty(ids)) {
				circleMemberBizService.saveBiz(cId, ids, RealmUtils.getCurrentUser().getId(), 2); // 后台新增生成圈子成员
			} else {
				Member mm = memberBizService.saveBiz(member);
				circleMemberBizService.saveBiz(cId, mm.getId(), RealmUtils.getCurrentUser().getId(), 2); // 后台新增生成圈子成员
			}
		} catch (Exception e) {
			logger.info("添加圈子成员异常", e);
			ajaxResult.setSuccess(false);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 删除圈子成员
	 */
	@ResponseBody
	@RequestMapping(value = "del")
	public AjaxResult delete(String id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			CircleMember circleMember = circleMemberService.get(id);
			circleMemberBizService.out(circleMember,RealmUtils.getCurrentUser().getId());
			return new AjaxResult(true);
		} catch (Exception e) {
			logger.info("删除圈子成员异常", e);
			ajaxResult.setSuccess(false);
		}
		return new AjaxResult(false);
	}

	/**
	 * 设置管理员
	 * 
	 * @param id
	 *            circleMember的id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "setMgr" })
	public AjaxResult setMgr(String id) {
		try {
			circleMemberBizService.setMgrBiz(id);
		} catch (Exception e) {
			logger.info("设置管理员异常", e);
			return new AjaxResult(false, e.getMessage());
		}
		return new AjaxResult(true);
	}
	/**
	 * 设置圈主
	 *
	 * @param id
	 *            circleMember的id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "setCreator" })
	public AjaxResult setCreator(String id) {
		try {
			circleMemberBizService.setCreatorBiz(id);
		} catch (Exception e) {
			logger.info("设置圈主异常", e);
			return new AjaxResult(false, e.getMessage());
		}
		return new AjaxResult(true);
	}
	/**
	 * 取消管理员
	 * 
	 * @param id
	 *            circleMember的id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "cancelMgr" })
	public AjaxResult cancelMgr(String id) {
		try {
			circleMemberBizService.cancelMgrBiz(id);
		} catch (Exception e) {
			logger.info("取消管理员异常", e);
			return new AjaxResult(false);
		}
		return new AjaxResult(true);
	}
}
