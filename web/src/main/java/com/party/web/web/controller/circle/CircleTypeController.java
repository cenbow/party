package com.party.web.web.controller.circle;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.model.system.Dict;
import com.party.core.model.user.User;
import com.party.core.service.system.IDictService;
import com.party.core.service.user.IUserService;
import com.party.web.utils.MyBeanUtils;
import com.party.web.web.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 圈子类型 Created by Administrator on 2017/5/9 0009.
 */
@Controller
@RequestMapping(value = "/circle/type")
public class CircleTypeController {

	@Autowired
	private IDictService dictService;
	
	@Autowired
	private IUserService userService;

	protected static Logger logger = LoggerFactory.getLogger(CircleTypeController.class);

	/**
	 * 圈子类型列表页面
	 */
	@RequestMapping(value = { "list", "" })
	public ModelAndView list(Page page, Dict dict) {
		page.setLimit(20);
		dict.setType("circle_type");
		ModelAndView mv = new ModelAndView("circle/typeList");
		List<Dict> list = dictService.listPage(dict, page);
		mv.addObject("list", list);
		mv.addObject("page", page);
		return mv;
	}

	/**
	 * 删除类型
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxResult delete(String id) throws Exception {
		try {
			dictService.delete(id);
			return AjaxResult.success();
		} catch (Exception e) {
			logger.info("删除类型错误", e);
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 添加类型
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxResult save(Dict dict) throws Exception {
		try {
			if (dict.getSort() == null) {
				dict.setSort(10);
			}
			User user = userService.findByLoginName("admin");
			dict.setCreateBy(user.getId());
			dict.setUpdateBy(user.getUpdateBy());
			if (StringUtils.isNotEmpty(dict.getId())) {
				Dict t = dictService.get(dict.getId());
				t.setType("circle_type");
				MyBeanUtils.copyBean2Bean(t, dict);
				dictService.update(t);
			} else {
				dict.setType("circle_type");
				dictService.insert(dict);
			}
		} catch (Exception e) {
			logger.info("添加类型错误", e);
			return AjaxResult.error(e.getMessage());
		}
		return AjaxResult.success();
	}

	/**
	 * 跳转至发布
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "form")
	public ModelAndView form(String id) {
		ModelAndView mv = new ModelAndView("circle/typeForm");
		if (StringUtils.isNotEmpty(id)) {
			Dict dict = dictService.get(id);
			mv.addObject("type", dict);
		}
		return mv;
	}
}
