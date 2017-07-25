package com.party.admin.web.controller.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.party.admin.biz.asynexport.IAsynExportService;
import com.party.common.redis.StringJedis;
import com.party.core.model.crowdfund.CrowdfundEvent;
import com.party.core.service.crowdfund.ICrowdfundEventService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.activity.ActivityBizService;
import com.party.admin.biz.crowdfund.ProjectBizService;
import com.party.admin.biz.crowdfund.RepresentBizService;
import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.activity.ActivityOutput;
import com.party.admin.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.activity.CrowfundResources;
import com.party.core.model.city.Area;
import com.party.core.model.city.City;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.model.distributor.WithCount;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderType;
import com.party.core.model.system.Dict;
import com.party.core.model.user.User;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.activity.ICrowfundResourcesService;
import com.party.core.service.activity.OrderActivityBizService;
import com.party.core.service.activity.biz.ActivityDetailBizService;
import com.party.core.service.activity.biz.CrowfundResourceBizService;
import com.party.core.service.circle.biz.CircleBizService;
import com.party.core.service.circle.biz.CircleMemberBizService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.city.ICityService;
import com.party.core.service.counterfoil.ICounterfoilBusinessService;
import com.party.core.service.counterfoil.ICounterfoilService;
import com.party.core.service.counterfoil.biz.CounterfoilBizService;
import com.party.core.service.crowdfund.impl.ProjectService;
import com.party.core.service.crowdfund.impl.TargetProjectService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.system.IDictService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.core.service.user.IUserService;
import com.party.notify.notifyPush.servce.INotifySendService;

/**
 * 活动
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/activity/activity")
public class ActivityController {

	@Autowired
	private IActivityService activityService;

	@Autowired
	private IActivityDetailService activityDetailService;

	@Autowired
	private IDictService dictService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IMemberActService memberActService;

	@Autowired
	private ICityService cityService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private FileBizService fileBizService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ActivityBizService activityBizService;

	@Autowired
	private ICrowfundResourcesService crowfundResourcesService;
	
	@Autowired
	private ProjectBizService projectBizService;

	@Autowired
	private RepresentBizService representBizService;

	@Autowired
	private INotifySendService notifySendService;
	@Autowired
	private ActivityDetailBizService activityDetailBizService;
	@Autowired
	private CrowfundResourceBizService crowfundResourceBizService;
	@Autowired
	private CounterfoilBizService counterfoilBizService;
	@Autowired
	private ICounterfoilBusinessService counterfoilBusinessService;
	@Autowired
	private ICounterfoilService counterfoilService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private CircleMemberBizService circleMemberBizService;
	@Autowired
	private CircleBizService circleBizService;
	@Autowired
	private OrderActivityBizService orderActivityBizService;

	@Autowired
	private ICrowdfundEventService crowdfundEventService;

	@Autowired
	private StringJedis stringJedis;

	@Resource(name = "representAsynExportService")
	private IAsynExportService asynExportService;

	@Resource(name = "projectAsynExportService")
	private IAsynExportService projectAsynExportService;

	protected static Logger logger = LoggerFactory.getLogger(ActivityController.class);

	/**
	 * 跳转至发布 普通活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "activityForm")
	public ModelAndView activityForm(String id) {
		ModelAndView mv = new ModelAndView("activity/activityForm");
		City city = new City();
		city.setIsOpen(1);
		mv.addObject("citys", cityService.list(city));
		Dict dict = new Dict();
		dict.setType("activity_type");
		mv.addObject("types", dictService.list(dict));
		if (StringUtils.isNotEmpty(id)) {
			Activity activity = activityService.get(id);
			mv.addObject("cityName", cityService.get(activity.getCityId()).getName());
			ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
			if (activityDetail != null) {
				activityDetailBizService.castToHTML(activityDetail);
			}
			mv.addObject("activity", activity);
			mv.addObject("activityDetail", activityDetail);

			Counterfoil t = new Counterfoil();
			t.setBusinessId(id);
			List<Counterfoil> counterfoils = counterfoilService.list(t);
			for (Counterfoil counterfoil : counterfoils) {
				if (counterfoil.getJoinNum() > 0) {
					counterfoil.setHasBuy(true);
				}
			}
			mv.addObject("counterfoils", counterfoils);
		}
		mv.addObject("isGoods", false);
		return mv;
	}

	/**
	 * 跳转至发布 普通活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "zcActivityForm")
	public ModelAndView zcActivityForm(String id) {
		ModelAndView mv = new ModelAndView("activity/zcActivityForm");
		City city = new City();
		city.setIsOpen(1);
		mv.addObject("citys", cityService.list(city));
		Dict dict = new Dict();
		dict.setType("activity_type");
		mv.addObject("types", dictService.list(dict));
		List<CrowdfundEvent> crowdfundEventList = crowdfundEventService.list(new CrowdfundEvent());
		if (StringUtils.isNotEmpty(id)) {
			/** 活动 **/
			Activity activity = activityService.get(id);
			ActivityOutput activityOutput = ActivityOutput.transform(activity);
			City c = cityService.get(activity.getCityId());
			activityOutput.setCity(c);
			mv.addObject("cityName", c.getName());
			// 跑马灯
			List<CrowfundResources> picList = crowfundResourcesService.findByRefId(activity.getId(), "1");
			activityOutput.setPicList(picList);
			// 视频
			List<CrowfundResources> videoList = crowfundResourcesService.findByRefId(activity.getId(), "2");
			if (videoList.size() > 0) {
				activityOutput.setVideo(videoList.get(0));
			}
			mv.addObject("activity", activityOutput);

			/** 活动详情 **/
			ActivityDetail activityDetail = activityDetailService.getByRefId(activity.getId());
			if (activityDetail != null) {
				activityDetailBizService.castToHTML(activityDetail);
			}
			mv.addObject("activityDetail", activityDetail);
			mv.addObject("crowdfundEventList", crowdfundEventList);
		}
		return mv;
	}

	/**
	 * 跳转至详情 普通活动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "activityDetail")
	public ModelAndView activityDetail(String id) {
		ModelAndView mv = new ModelAndView("activity/activityDetail");
		Activity activity = activityService.get(id);
		mv.addObject("activity", activity);
		ActivityDetail activityDetail = activityDetailService.getByRefId(id);
		if (activityDetail != null) {
			activityDetailBizService.castToHTML(activityDetail);
		}
		List<Counterfoil> counterfoils = counterfoilBizService.getActivityCounterfoils(id);
		if (counterfoils.size() > 0) {
			// 获取票价
			String newPrice = counterfoilBizService.getShowPrice(null, id);
			mv.addObject("newPrice", newPrice);
		} else {
			mv.addObject("newPrice", activity.getPrice().toString());
		}
		mv.addObject("activityDetail", activityDetail);
		return mv;
	}

	@RequestMapping(value = "zcActivityDetail")
	public ModelAndView zcActivityDetail(String actId) {
		ModelAndView mv = new ModelAndView("activity/zcActivityDetail");
		Activity activity = activityService.get(actId);
		mv.addObject("activity", activity);
		ActivityDetail activityDetail = activityDetailService.getByRefId(actId);
		if (activityDetail != null) {
			activityDetailBizService.castToHTML(activityDetail);
		}

		//众筹金额
		float actualAmount = activityBizService.actualAmountForTargetId(actId);
		mv.addObject("actualAmount", actualAmount);
		mv.addObject("activityDetail", activityDetail);
		return mv;
	}

	/**
	 * 活动管理
	 * @param activity
	 * @param page
	 * @param commonInput
	 * @param s_start
	 * @param s_end
	 * @return
	 */
	@RequestMapping(value = "activityList")
	public ModelAndView activityList(Activity activity, Page page, CommonInput commonInput, String s_start, String s_end) {
		ModelAndView mv = new ModelAndView("activity/activityList");
		activity.setIsCrowdfunded(0); // 普通活动
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		// 活动开始时间
		if (StringUtils.isNotEmpty(s_start)) {
			params.put("s_start", s_start);
			mv.addObject("s_start", s_start);
		}
		if (StringUtils.isNotEmpty(s_end)) {
			params.put("s_end", s_end);
			mv.addObject("s_end", s_end);
		}

		Dict dict = new Dict();
		dict.setType("check_status");
		mv.addObject("checkStatus", dictService.list(dict));

		activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Activity> activities = activityService.listPage(activity, params, page);
		List<ActivityOutput> activityOutputs = LangUtils.transform(activities, input -> {
			ActivityOutput output = ActivityOutput.transform(input);
			// 报名人数
				List<Counterfoil> counterfoils = counterfoilBizService.getActivityCounterfoils(input.getId());
				Activity dbActivity = activityService.get(input.getId());
				if (counterfoils.size() > 0) {
					String newPrice = counterfoilBizService.getShowPrice(counterfoils, input.getId());
					output.setNewPrice(newPrice);
				} else {
					dbActivity = orderActivityBizService.updateActivityInfo(input.getId());
					output.setNewPrice(dbActivity.getPrice().toString());
				}
				output.setJoinNum(dbActivity.getJoinNum());
				output.setLimitNum(dbActivity.getLimitNum());

				// 活动二维码
				String path = RealmUtils.getCurrentUser().getId() + "/activity/";
				String content = "hd/hd_detail.html?hdId=" + input.getId();
				String fileEntity = fileBizService.getFileEntity(input.getId(), path, content);
				output.setQrCodeUrl(fileEntity);

				// 签到二维码
				String bmPath = RealmUtils.getCurrentUser().getId() + "/activity/memberAct/";
				String bmContent = "hd/hdbm_sign.html?hdId=" + input.getId();
				fileEntity = fileBizService.getFileEntity(input.getId(), bmPath, bmContent);
				output.setBmQrCodeUrl(fileEntity);

				// 活动二维码
				String disQr = activityBizService.getQrCode(input.getId(), input.getMemberId());
				output.setDisQrCode(disQr);
				return output;
			});

		mv.addObject("activities", activityOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 众筹活动管理
	 * @param activity
	 * @param page
	 * @param commonInput
	 * @param e_start
	 * @param e_end
	 * @return
	 */
	@RequestMapping(value = "zcActivityList")
	public ModelAndView zcActivityList(Activity activity, Page page, CommonInput commonInput, String e_start, String e_end) {
		ModelAndView mv = new ModelAndView("activity/zcActivityList");
		activity.setIsCrowdfunded(1); // 众筹活动
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		// 报名截止时间
		if (StringUtils.isNotEmpty(e_start)) {
			params.put("e_start", e_start);
			mv.addObject("e_start", e_start);
		}
		if (StringUtils.isNotEmpty(e_end)) {
			params.put("e_end", e_end);
			mv.addObject("e_end", e_end);
		}

		Dict dict = new Dict();
		dict.setType("check_status");
		mv.addObject("checkStatus", dictService.list(dict));

		activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Activity> activities = activityService.listPage(activity, params, page);

		List<ActivityOutput> activityOutputs = LangUtils.transform(activities, input -> {
			ActivityOutput output = ActivityOutput.transform(input);
			// 报名人数
				int joinNum = orderFormService.calculateBuyNum(input.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
				output.setJoinNum(joinNum);
				Activity activity1 = activityService.get(input.getId());
				if (!activity1.getJoinNum().equals(joinNum)) {
					activity1.setJoinNum(joinNum);
					activityService.update(activity1);
				}

				// 众筹代言二维码
				String path = RealmUtils.getCurrentUser().getId() + "/zcActivity/";
				String content = "project/target_detail.html?id=" + input.getId();
				String qrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
				output.setQrCodeUrl(qrCodeUrl);

				// 众筹项目详情二维码
				String detail = RealmUtils.getCurrentUser().getId() + "/zcActivity/detail/";
				String detailContent = "project/target_client_detail.html?id=" + input.getId();
				String detailQrCodeUrl = fileBizService.getFileEntity(input.getId(), detail, detailContent);
				output.setBmQrCodeUrl(detailQrCodeUrl);
				return output;
			});

		if (!Strings.isNullOrEmpty(activity.getEventId())){
			mv.addObject("removeEvent", true);
		}
		mv.addObject("activities", activityOutputs);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 发布保存
	 * 
	 * @param startDate
	 * @param endDate
	 * @param activity
	 * @param activityDetail
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public AjaxResult save(String startDate, String endDate, Activity activity, String areaSelect, String areaInput, ActivityDetail activityDetail, BindingResult result, String picList,
			String videoList) throws Exception {
		String memberId = RealmUtils.getCurrentUser().getId();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			return AjaxResult.error(allErros.get(0).getDefaultMessage());
		}

		if (StringUtils.isNotEmpty(startDate)) {
			activity.setStartTime(DateUtils.parse(startDate, "yyyy-MM-dd HH:mm"));
		}
		if (StringUtils.isNotEmpty(endDate)) {
			activity.setEndTime(DateUtils.parse(endDate, "yyyy-MM-dd HH:mm"));
		}
		if (!Strings.isNullOrEmpty(activity.getEventId())){
			if (Strings.isNullOrEmpty(activity.getEventId().trim())){
				activity.setEventId("");
			}
		}
		if (activity.getLimitNum() == null) {
			activity.setLimitNum(0);
		}
		
		if (StringUtils.isNotEmpty(areaInput)) {
			activity.setArea(areaInput);
		}
		if (StringUtils.isNotEmpty(areaSelect)) {
			activity.setArea(areaSelect);
		}

		activity.setThirdPartyId("a287a2a4e8e94fe6bd5fb19cad345da6");// 活动供应商默认是同行者
		activity.setTitle(activity.getTitle().replace("\"", "“"));
		User user = userService.findByLoginName("admin");

		if (StringUtils.isNotEmpty(activity.getId())) {
			// 更新内容
			Activity t = activityService.get(activity.getId());// 从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(activity, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			activityService.update(t);// 保存
			if (t.getIsCrowdfunded() == 1) {
				TargetProject targetProject = new TargetProject();
				targetProject.setTargetId(t.getId());
			}

		} else {// 新增表单保存
			if (activity.getPrice() == null) {
				activity.setPrice(new Float(0.0));
			}
			activity.setIsOpen(1); // 报名开启
			activity.setShareNum(0); // 分享数
			activity.setReadNum(0); // 阅读数
			activity.setJoinNum(0); // 报名数
			activity.setGoodNum(0); // 点赞数

			activity.setCreateBy(user.getId());
			activity.setUpdateBy(user.getId());
			activity.setMember(RealmUtils.getCurrentUser().getId()); // 当前用户
			activity.setCheckStatus(ActStatus.ACT_STATUS_AUDITING.getCode().toString());

			if (activity.getIsCrowdfunded() == 1) {
				activity.setMicWebStatus(1); // 微网站效果
				activity.setInviteOnly(0); // 是否持邀请码参与
				activity.setJoinHidden(1); // 隐藏报名人员
				activity.setIsAllowedOutside(0); // 是否允许在其它城市频道显示
				activity.setShowFront(1); // 显示在前端
			}
			activityService.insert(activity);// 保存
		}
		/************* 详情 ****************/
		activityDetailBizService.saveBiz(activity.getId(), activityDetail);
		crowfundResourceBizService.saveResourceBiz(activity, picList, videoList, memberId);
		counterfoilBizService.saveBiz(activity.getCounterfoils(), activity.getId(), Constant.BUSINESS_TYPE_ACTIVITY, memberId);
		return AjaxResult.success();
	}

	/**
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteLogic")
	public AjaxResult deleteLogic(String id) {
		try {
			if (StringUtils.isEmpty(id)) {
				return new AjaxResult(false);
			}

			MemberAct t = new MemberAct();
			t.setActId(id);
			List<MemberAct> memberActs = memberActService.list(t);
			// 没有人报名可以直接删除
			if (memberActs.size() == 0) {
				Activity activity = activityService.get(id);
				activity.setDelFlag(BaseModel.DEL_FLAG_DELETE);
				activityService.update(activity);
			} else {
				return new AjaxResult(false, "已有人报名不能删除");
			}

			return new AjaxResult(true);
		} catch (Exception e) {
			logger.info("活动删除异常", e);
		}
		return new AjaxResult(false);
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

			Activity activity = activityService.get(id);
			// 没有人报名可以直接删除
			if (activity.getJoinNum() == 0) {
				MemberAct t = new MemberAct();
				t.setActId(id);
				List<MemberAct> memberActs = memberActService.list(t);
				for (MemberAct memberAct : memberActs) {
					if (memberAct.getCheckStatus().equals(ActStatus.ACT_STATUS_CANCEL.getCode())
							|| memberAct.getCheckStatus().equals(ActStatus.ACT_STATUS_AUDIT_REJECT.getCode())
							|| memberAct.getCheckStatus().equals(ActStatus.ACT_STATUS_NO_JOIN.getCode())) {
						counterfoilBusinessService.deleteByBusinessId(memberAct.getId());
						memberActService.delete(memberAct.getId());
					}
				}
				activityDetailService.deleteByRefId(id);
				activityService.delete(id);
				return new AjaxResult(true);
			} else {
				return new AjaxResult(false, "已有人报名不能删除");
			}

		} catch (Exception e) {
			logger.info("活动删除异常", e);
		}
		return new AjaxResult(false);
	}

	/**
	 * 审核活动
	 */
	@ResponseBody
	@RequestMapping(value = "verify")
	public AjaxResult verify(String actId, String checkStatus) {
		try {
			if (StringUtils.isEmpty(actId)) {
				return new AjaxResult(false);
			}
			Activity entity = activityService.get(actId);
			if (entity != null) {
				entity.setCheckStatus(checkStatus);
				activityService.update(entity);
				// 消息推送
				if (StringUtils.isNotEmpty(entity.getMember())) {
					if ("1".equals(checkStatus)) {
						notifySendService.sendActivityPass(entity.getTitle(), entity.getId(), entity.getMember());
					} else {
						notifySendService.sendActivityRefuse(entity.getTitle(), entity.getId(), entity.getMember());
					}
				}
			}
			return new AjaxResult(true);
		} catch (Exception e) {
			logger.info("活动审核异常", e);
		}
		return new AjaxResult(false);
	}
	
	/**
	 * 众筹列表
	 * @param id 活动编号
	 * @param page 分页参数
	 * @param projectWithAuthor 众筹状态
	 * @return 输出视图
	 */
	@RequestMapping(value = "zcCrowdfundList")
	public ModelAndView crowdfundList(String id, ProjectWithAuthor projectWithAuthor, Page page){
		page.setLimit(20);
		ModelAndView modelAndView = new ModelAndView("activity/zcCrowdfund");
		Activity activity = activityService.get(id);
		int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
		activity.setJoinNum(joinNum);

		projectWithAuthor.setTargetId(id);
		List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(projectWithAuthor, page);
		//众筹金额
		float actualAmount = activityBizService.actualAmountForTargetId(id);
		String zipUrl = stringJedis.getValue(Constant.PRE_ZIP_URL + "crowdfund" + id);
		stringJedis.delete(Constant.PRE_ZIP_URL + "crowdfund" + id);
		modelAndView.addObject("activity", activity);
		modelAndView.addObject("actualAmount", actualAmount);
		modelAndView.addObject("crowdfundNum", page.getTotalCount());
		modelAndView.addObject("list", projectForActivityOutputList);
		modelAndView.addObject("zipUrl", zipUrl);
		modelAndView.addObject("page", page);
		return modelAndView;
	}


	/**
	 * 导出众筹列表
	 * @param id 目标编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crowdfundListExport")
	public AjaxResult crowdfundListExport(String id){
		projectAsynExportService.export(id);
		return AjaxResult.success();
	}

	/**
	 * 代言列表
	 * @param id 活动编号
	 * @param page 分页参数
	 * @return 输出视图
	 */
	@RequestMapping(value = "zcRepresentList")
	public ModelAndView representList(String id, WithCount withCount, Page page){
		page.setLimit(20);
		ModelAndView modelAndView = new ModelAndView("activity/zcRepresent");
		Activity activity = activityService.get(id);
		int joinNum = orderFormService.calculateBuyNum(activity.getId(), null, false, OrderType.ORDER_ACTIVITY.getCode());
		activity.setJoinNum(joinNum);
		modelAndView.addObject("activity", activity);

		List<ListForTargetOutput> listForTargetOutputList = representBizService.listForTarget(page, withCount, id);
		Integer crowdfundNum = projectService.sizeForTargetId(id);
		//众筹金额
		float actualAmount = activityBizService.actualAmountForTargetId(id);

		String zipUrl = stringJedis.getValue(Constant.PRE_ZIP_URL + "represent"  + id);
		stringJedis.delete(Constant.PRE_ZIP_URL + "represent"  + id);
		modelAndView.addObject("actualAmount", actualAmount);
		modelAndView.addObject("crowdfundNum", crowdfundNum);
		modelAndView.addObject("list", listForTargetOutputList);
		modelAndView.addObject("zipUrl", zipUrl);
		modelAndView.addObject("page", page);
		return modelAndView;
	}

	/**
	 * 代言列表导出
	 * @param id 活动编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "representListExport")
	public AjaxResult representListExport(String id){
		asynExportService.export(id);
		return AjaxResult.success();
	}
	
	/**
	 * 删除资源图片
	 * @param resourceId 资源ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delZcActivityPic")
	public AjaxResult delZcActivityPic(String resourceId) {
		crowfundResourcesService.delete(resourceId);
		return AjaxResult.success();
	}

	/**
	 * 更新showFront
	 * 
	 * @param showFront
	 * @param actId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateShowFront")
	public AjaxResult updateShowFront(String showFront, String actId) {
		Activity activity = activityService.get(actId);
		if (activity != null) {
			activity.setShowFront(Integer.valueOf(showFront));
			activityService.update(activity);
		}
		return AjaxResult.success();
	}
	
	/**
	 * 创建业务ID
	 * @param actId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createCircle")
	public AjaxResult createCircle(String actId, String type) {
		Activity activity = activityService.get(actId);
		MemberAct memberAct = new MemberAct();
		memberAct.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		memberAct.setActId(actId);
		List<MemberAct> memberActs = new ArrayList<MemberAct>();
		if (type.equals("1")) { // 已成功
			memberAct.setCheckStatus(ActStatus.ACT_STATUS_PAID.getCode());
			memberActs = memberActService.list(memberAct);
		} else if (type.equals("2")) {
			if (activity.getIsCrowdfunded() == 0) {
				memberActs = memberActService.list(memberAct); // 全部
			} else if (activity.getIsCrowdfunded() == 1) {
				Set<Integer> status = new HashSet<Integer>();
				status.add(ActStatus.ACT_STATUS_CANCEL.getCode());
				status.add(ActStatus.ACT_STATUS_NO_JOIN.getCode());
				memberActs = memberActService.getSuccessMemberAct(memberAct, status);
			}
		}

		List<String> memberIds = new ArrayList<String>();
		memberIds.add(activity.getMember());
		
		String remarks = activity.getTitle() + "——";
		if (activity.getIsCrowdfunded() == 0) {
			remarks += (type.equals("1") ? "已报名成功人员圈子" : "全部报名人员圈子");
		} else if (activity.getIsCrowdfunded() == 1) {
			remarks += (type.equals("1") ? "已众筹成功人员圈子" : "全部发起众筹人员圈子");
		}
		remarks = remarks.replace("\"", "“");
		activity.setRemarks(remarks);

		String circleId = circleBizService.createCircleBusiness(actId, activity, type);
		circleMemberBizService.circleMemberManage(memberActs, circleId, type, memberIds,RealmUtils.getCurrentUser().getId());
		return AjaxResult.success();
	}

	/**
	 * 前端城市三级联动
	 * 
	 * @param cityName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAreaByCityName")
	public List<Area> getAreaByCityName(String cityName) {
		List<Area> areas = areaService.getAreaByCityName(cityName);
		return areas;
	}
}
