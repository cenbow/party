package com.party.admin.web.controller.circle;

import java.util.List;
import java.util.Map;

import com.party.core.model.circle.CircleCode;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.circle.CircleInput;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.common.paging.Page;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleTag;
import com.party.core.model.city.Area;
import com.party.core.model.system.Dict;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.ICircleTagService;
import com.party.core.service.circle.biz.CircleBizService;
import com.party.core.service.city.IAreaService;
import com.party.core.service.system.IDictService;

/**
 * 圈子
 */
@Controller
@RequestMapping(value = "/circle")
public class CircleController {

	@Autowired
	private ICircleService circleService;
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IDictService dictService;
	@Autowired
	private CircleBizService circleBizService;
	@Autowired
	private FileBizService fileBizService;
	@Autowired
	private ICircleTagService circleTagService;

	/**
	 * 圈子列表页面
	 */
	@RequestMapping(value = { "list", "" })
	public ModelAndView list(Page page, Circle circle, CommonInput commonInput) {
		page.setLimit(20);
		ModelAndView mv = new ModelAndView("circle/circleList");
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		List<Map<String, Object>> list = circleService.webListPage(circle, params, page);
		for (Map<String, Object> item : list) {
			String id = (String) item.get("id");
			String content = "circle/circle_detail.html?id=" + id;
			String path = RealmUtils.getCurrentUser().getId() + "/circle/";
			String fileEntity = fileBizService.getFileEntity(id, path, content);
			item.put("qrCodeUrl", fileEntity);
		}
		mv.addObject("list", list);
		mv.addObject("page", page);
		mv.addObject("circle", circle);
		return mv;
	}

	/**
	 * 查看，增加，编辑圈子表单页面
	 */
	@RequestMapping(value = "form")
	public ModelAndView form(Circle circle) {
		ModelAndView mv = new ModelAndView("circle/circleForm");
		if (StringUtils.isNotBlank(circle.getId())) {
			circle = circleService.get(circle.getId());

			CircleTag t = new CircleTag();
			t.setCircle(circle.getId());
			List<CircleTag> circleTags = circleTagService.list(t);
			mv.addObject("circleTags", circleTags);
		}
		if (StringUtils.isNotEmpty(circle.getArea())) {
			Area area = areaService.get(circle.getArea());
			mv.addObject("city", area.getId());
			mv.addObject("arParent", area.getParentId());
		}

		Dict dict = new Dict();
        dict.setType("circle_type");
        mv.addObject("types", dictService.list(dict));
		mv.addObject("circle", circle);
		Area area = new Area();
		area.setParentId("1");
		List<Area> areas = areaService.list(area);
		mv.addObject("areas", areas);
		return mv;
	}

	/**
	 * 保存圈子
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(CircleInput circleInput, BindingResult result) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
			return ajaxResult;
		}
		// html编码
		String content = null == circleInput.getMemo() ? null : StringEscapeUtils.escapeHtml4(circleInput.getMemo().trim());
		circleInput.setMemo(circleInput.getMemo().trim());
		// 以下内容暂时写在controller 因为core里没有myBeanUtils类
		if (StringUtils.isNotEmpty(circleInput.getId())) {// 编辑表单保存
			Circle circleDb = circleService.get(circleInput.getId());
			MyBeanUtils.copyBeanNotNull2Bean(circleInput, circleDb);// 将编辑表单中的非\NULL值覆盖数据库记录中的值
			circleInput.setUpdateBy(RealmUtils.getCurrentUser().getId());

			if (circleInput.getCircleTags() != null) {
				if (circleInput.getIsOpen() == CircleCode.CIRCLE_AUTH_TYPE_AUTH.getCode()) {
					for (CircleTag circleTag : circleInput.getCircleTags()) {
						if (circleTag.getTagName().equals("其他")) {
							circleDb.setNoTypeIsOpen(circleTag.getIsOpen());
						} else {
							CircleTag t = circleTagService.get(circleTag.getId());
							t.setIsOpen(circleTag.getIsOpen());
							circleTagService.update(t);
						}
					}
				} else {
					for (CircleTag circleTag : circleInput.getCircleTags()) {
						if (circleTag.getTagName().equals("其他")) {
							circleDb.setNoTypeIsOpen(null);
						} else {
							CircleTag t = circleTagService.get(circleTag.getId());
							t.setIsOpen(null);
							circleTagService.update(t);
						}
					}
				}
			}
			circleService.update(circleDb);
		} else {
			circleInput.setCreateBy(RealmUtils.getCurrentUser().getId());
			Circle circle = new Circle();
			MyBeanUtils.copyBeanNotNull2Bean(circleInput, circle);// 将编辑表单中的非\NULL值覆盖数据库记录中的值
			circleService.insert(circle);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	/**
	 * 删除圈子
	 */
	@ResponseBody
	@RequestMapping(value = "del")
	public AjaxResult delete(String id) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			circleBizService.dissolveCircle(id);
			return new AjaxResult(true);
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return new AjaxResult(false);
	}

}
