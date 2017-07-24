package com.party.web.web.controller.gatherForm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.party.common.paging.Page;
import com.party.common.utils.Encodes;
import com.party.common.utils.StringUtils;
import com.party.core.model.gatherForm.GatherForm;
import com.party.core.model.gatherForm.GatherFormInfo;
import com.party.core.model.gatherForm.GatherProject;
import com.party.core.model.member.Member;
import com.party.core.service.gatherForm.IGatherFormInfoService;
import com.party.core.service.gatherForm.IGatherFormOptionService;
import com.party.core.service.gatherForm.IGatherFormService;
import com.party.core.service.gatherForm.IGatherProjectService;
import com.party.core.service.member.IMemberService;
import com.party.web.biz.gatherForm.GatherFormInfoBizService;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.common.CommonInput;
import com.party.web.web.dto.output.gatherForm.GatherFormInfoOutput;

@Controller
@RequestMapping("gatherForm/form")
public class GatherFormController {

	@Autowired
	private IGatherFormService gatherFormService;
	@Autowired
	private IGatherFormInfoService gatherFormInfoService;
	@Autowired
	private IGatherProjectService gatherProjectService;
	@Autowired
	private IGatherFormOptionService gatherFormOptionService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private GatherFormInfoBizService gatherFormInfoBizService;

	/**
	 * 表单收集信息列表
	 * 
	 * @param projectId
	 * @param commonInput
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(String projectId, CommonInput commonInput, Page page) throws Exception {
		page.setLimit(30);
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		params.put("projectId", projectId);
		List<Map<String, Object>> gatherForms = gatherFormInfoService.webListPage(params, page);
		List<GatherFormInfoOutput> outputs = Lists.newArrayList();
		List<GatherForm> fields = gatherFormService.list(new GatherForm(projectId));
		for (Map<String, Object> input : gatherForms) {
			GatherFormInfoOutput output = new GatherFormInfoOutput();
			String maxIndex = input.get("maxIndex") != null ? input.get("maxIndex").toString() : "";
			output.setMaxIndex(maxIndex);
			String fieldValue = input.get("fieldValue") != null ? input.get("fieldValue").toString() : "";
			String [] result = new String[fields.size()];
			for (int i = 0; i < fieldValue.split("maxIndex").length; i++) {
				result[i] = fieldValue.split("maxIndex")[i];
			}
			output.setFieldValues(result);
			if (null != input.get("createBy") && StringUtils.isNotEmpty(input.get("createBy").toString())) {
				Member member = memberService.get(input.get("createBy").toString());
				output.setMember(member);
			}
			output.setCreateDate((Date) input.get("createDate"));
			outputs.add(output);
		}
		ModelAndView mv = new ModelAndView("gatherForm/formInfoList");
		mv.addObject("formInfos", outputs);
		mv.addObject("input", commonInput);
		mv.addObject("page", page);
		
		mv.addObject("fields", fields);

		GatherProject gatherProject = gatherProjectService.get(projectId);
		mv.addObject("project", gatherProject);
		return mv;
	}

	/**
	 * 表单信息详情
	 * 
	 * @param maxIndex
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/formInfo")
	public ModelAndView formInfo(String maxIndex, String projectId) {
		ModelAndView mv = new ModelAndView("gatherForm/formInfo");
		List<GatherForm> fields = gatherFormService.list(new GatherForm(projectId));
		mv.addObject("fields", fields);

		GatherFormInfo t = new GatherFormInfo();
		t.setProjectId(projectId);
		t.setMaxIndex(Integer.valueOf(maxIndex));
		List<GatherFormInfo> infos = gatherFormInfoService.list(t);
		mv.addObject("infos", infos);
		return mv;
	}

	/**
	 * 删除表单字段
	 * 
	 * @param projectId
	 * @param maxIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public AjaxResult delete(String projectId, String maxIndex) {
		if (StringUtils.isEmpty(projectId) || StringUtils.isEmpty(maxIndex)) {
			AjaxResult ajaxResult = new AjaxResult();
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		gatherFormInfoService.deleteByProjectId(projectId, maxIndex);
		return AjaxResult.success();
	}
	
	/**
	 * 删除单个字段
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteField")
	public AjaxResult deleteField(String id){
		gatherFormService.delete(id);
		return AjaxResult.success();
	}
	
	/**
	 * 删除单个选项
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteOption")
	public AjaxResult deleteOption(String id){
		gatherFormOptionService.delete(id);
		return AjaxResult.success();
	}

	/**
	 * 导出表单收集信息
	 * 
	 * @param projectId
	 * @param commonInput
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("exportFormInfo")
	public String exportFormInfo(String projectId, CommonInput commonInput, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "表单收集" + sdf.format(new Date()) + ".xlsx";
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		params.put("projectId", projectId);
		List<Map<String, Object>> gatherForms = gatherFormInfoService.webListPage(params, null);
		List<GatherForm> fields = gatherFormService.list(new GatherForm(projectId));
		Workbook workbook = gatherFormInfoBizService.exportExcel(gatherForms, fields);

		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
		workbook.write(response.getOutputStream());
		return null;
	}
}
