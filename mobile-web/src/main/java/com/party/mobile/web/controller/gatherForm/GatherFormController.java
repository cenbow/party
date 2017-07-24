package com.party.mobile.web.controller.gatherForm;

import com.party.common.utils.PartyCode;
import com.party.common.utils.PinyinUtil;
import com.party.common.utils.StringUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.gatherForm.*;
import com.party.core.model.member.Member;
import com.party.core.service.gatherForm.IGatherFormInfoService;
import com.party.core.service.gatherForm.IGatherFormOptionService;
import com.party.core.service.gatherForm.IGatherFormService;
import com.party.core.service.gatherForm.IGatherProjectService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.notify.notifyPush.servce.INotifySendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 表单
 */
@Controller
@RequestMapping("gatherForm/form")
public class GatherFormController {

	@Autowired
	private IGatherProjectService gatherProjectService;
	@Autowired
	private IGatherFormService gatherFormService;
	@Autowired
	private CurrentUserBizService currentUserBizService;
	@Autowired
	private IGatherFormInfoService gatherFormInfoService;
	@Autowired
	private IGatherFormOptionService gatherFormOptionService;
    @Autowired
    private INotifySendService notifySendService;
    @Autowired
    private IMemberService memberService;

	@ResponseBody
	@RequestMapping("getDetails")
	public AjaxResult getDetails(String projectId) {
		if (StringUtils.isEmpty(projectId)) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目id不能为空");
		}
		GatherProject gatherProject = gatherProjectService.get(projectId);
		if (StringUtils.isNotEmpty(gatherProject.getContent())) {
			String content = StringUtils.stringtohtml(gatherProject.getContent());
			gatherProject.setContent(content);
		}

		GatherForm t = new GatherForm();
		t.setProjectId(projectId);
		List<GatherForm> gatherForms = gatherFormService.list(t);
		for (GatherForm gatherForm : gatherForms) {
			if (gatherForm.getType().equals(FormType.CHECKBOX.getValue()) || gatherForm.getType().equals(FormType.RADIO.getValue())
					|| gatherForm.getType().equals(FormType.SELECT.getValue())) {
				List<GatherFormOption> subitems = gatherFormOptionService.list(new GatherFormOption(gatherForm.getId()));
				gatherForm.setSubitems(subitems);
			}
		}
		gatherProject.setItems(gatherForms);
		return AjaxResult.success(gatherProject);
	}

	@ResponseBody
	@RequestMapping("save")
	public AjaxResult save(GatherProject project, BindingResult result, HttpServletRequest request) {
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			String description = allErrors.get(0).getDefaultMessage();
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
		}

		Integer maxIndex = gatherFormInfoService.getMaxIndex(project.getId());

		if (StringUtils.isEmpty(project.getId())) {
			return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "项目id不能为空");
		}

		CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
		String currentId = "";
		if (currentUser != null) {
			currentId = currentUser.getId();
		}

		if (null != project.getInfoItems()) {
			for (GatherFormInfo info : project.getInfoItems()) {
				if (StringUtils.isEmpty(info.getFieldValue())) {
					info.setFieldValue(" ");
				} else {
					info.setFieldValue(PinyinUtil.filterEmoji(info.getFieldValue(), ""));
				}
				if (StringUtils.isEmpty(info.getId())) {
					info.setMaxIndex(null == maxIndex ? 1 : maxIndex + 1);
					info.setProjectId(project.getId());
					info.setCreateBy(currentId);
					info.setUpdateBy(currentId);
					gatherFormInfoService.insert(info);
				}
			}
		}

        GatherProject dbProject = gatherProjectService.get(project.getId());

        if (StringUtils.isNotEmpty(dbProject.getIsRemindMe()) && Integer.valueOf(dbProject.getIsRemindMe()).equals(YesNoStatus.YES.getCode())) {
            Member member = memberService.get(dbProject.getCreateBy());
            notifySendService.sendSuccessForm(member.getMobile(), dbProject);
        }

		return AjaxResult.success();
	}
}
