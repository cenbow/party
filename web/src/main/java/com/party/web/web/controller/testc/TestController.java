package com.party.web.web.controller.testc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/testc")
public class TestController {

	@Autowired
	@RequestMapping(value = "form")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("test/form_test");
		return mv;
	}
	
	@Autowired
	@RequestMapping(value = "phoneView")
	public ModelAndView phoneView() {
		ModelAndView mv = new ModelAndView("test/phone_view");
		return mv;
	}
	
}
