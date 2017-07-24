package com.party.admin.web.controller.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.resource.Resource;
import com.party.core.model.system.Dict;
import com.party.core.service.resource.IResourceService;
import com.party.core.service.system.IDictService;

@Controller
@RequestMapping("/resource/resource")
public class ResourceController {

	@Autowired
	private IDictService dictService;

	@Autowired
	private IResourceService resourceService;

	/**
	 * 资源列表
	 * 
	 * @param resource
	 * @param page
	 * @param commonInput
	 * @return
	 */
	@RequestMapping(value = "resourceList")
	public ModelAndView resourceList(Resource resource, Page page, CommonInput commonInput) {
		ModelAndView mv = new ModelAndView("resource/resourceList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		resource.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Resource> resources = resourceService.webListPage(resource, params, page);
		mv.addObject("resources", resources);
		mv.addObject("page", page);

		Dict t = new Dict();
		t.setType("resource_type");
		List<Dict> dicts = dictService.list(t);
		mv.addObject("resourceTypes", dicts);
		return mv;
	}

	/**
	 * 编辑或新增
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "resourceForm")
	public ModelAndView resourceForm(String id) {
		ModelAndView mv = new ModelAndView("resource/resourceForm");
		if (StringUtils.isNotEmpty(id)) {
			Resource resource = resourceService.get(id);
			mv.addObject("resource", resource);
		}

		Dict t = new Dict();
		t.setType("resource_type");
		List<Dict> dicts = dictService.list(t);
		mv.addObject("resourceTypes", dicts);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Resource resource) throws Exception {
		if (StringUtils.isNotEmpty(resource.getId())) {
			Resource t = resourceService.get(resource.getId());
			MyBeanUtils.copyBeanNotNull2Bean(resource, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
			resourceService.update(t);
		} else {
			String usreId = RealmUtils.getCurrentUser().getId();
			resource.setUpdateBy(usreId);
			resource.setCreateBy(usreId);
			resourceService.insert(resource);
		}
		return new AjaxResult(true);
	}
}
