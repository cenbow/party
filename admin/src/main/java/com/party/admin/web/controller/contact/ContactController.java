package com.party.admin.web.controller.contact;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.web.dto.AjaxResult;
import com.party.common.paging.Page;
import com.party.core.model.contact.Contact;
import com.party.core.model.member.Member;
import com.party.core.service.contact.IContactService;
import com.party.core.service.member.IMemberService;

/**
 * 通讯录
 * 
 * @author Juliana
 *
 */
@Controller
@RequestMapping(value = "/contact")
public class ContactController {

	@Autowired
	private IContactService contactService;
	@Autowired
	private IMemberService memberService;
	

	protected static Logger logger = LoggerFactory.getLogger(ContactController.class);

	/**
	 * 通讯录用户列表
	 * 
	 * @param member
	 * @param page
	 * @param timeType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "memberList")
	public ModelAndView memberList(Member member, Page page, Integer timeType, String memberType, String startDate, String endDate, String status) {
		ModelAndView mv = new ModelAndView("contact/memberList");
		try {
			page.setLimit(20);
			Map<String, Object> params = new HashMap<String, Object>();
			if (timeType != null && timeType != 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				Date ed = calendar.getTime(); // 结束时间
				if (timeType == 2) { // 本周内
					int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
					if (day_of_week == 0) {
						day_of_week = 7;
					}
					calendar.add(Calendar.DATE, -day_of_week + 1);
				} else if (timeType == 3) { // 本月内
					calendar.set(Calendar.DAY_OF_MONTH, 1);
				}
				Date sd = calendar.getTime(); // 开始时间
				String std = sdf.format(sd) + " 00:00:00";
				params.put("startDate", std);
				String end = sdf.format(ed) + " 23:59:59";
				params.put("endDate", end);
			}

			Set<String> statuss = new HashSet<String>();
			if (StringUtils.isNotEmpty(memberType)) {
				String[] str = memberType.split(":");
				if (str[0].equals("userStatus")) {
					statuss.add(str[1]);
					params.put(str[0], statuss);
				} else {
					params.put(str[0], str[1]);
				}
			}

			if (StringUtils.isNotEmpty(status)) {
				for (String s : status.split(",")) {
					statuss.add(s);
				}
				params.put("userStatus", statuss);
			}

			if (StringUtils.isNotEmpty(startDate)) {
				params.put("startTime", startDate);
			}
			if (StringUtils.isNotEmpty(endDate)) {
				params.put("endTime", endDate);
			}

			List<Map<String,Object>>groupMember = contactService.memberPage(member, params, page);
			mv.addObject("groupMember", groupMember);
			mv.addObject("page", page);

			mv.addObject("timeType", timeType);
			mv.addObject("memberType", memberType);
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			mv.addObject("status", status);
		} catch (Exception e) {
			logger.info("通讯录用户列表异常", e);
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 改变通讯录状态
	 * @param contact
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeStatus")
	public AjaxResult changeStatus(Contact contact){
		AjaxResult ajaxResult = new AjaxResult();
		Contact dbContact = contactService.get(contact.getId());
		dbContact.setStatus(contact.getStatus());
		contactService.update(dbContact);
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
	/**
	 * 通讯录列表
	 * 
	 * @param member
	 * @param page
	 * @param timeType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "contactList")
	public ModelAndView contactList(Contact contact,Page page) {
		ModelAndView mv = new ModelAndView("contact/contactList");
		try {
			page.setLimit(50);
			
			List<Contact>contacts = contactService.listPage(contact, page);
			Member member = memberService.get(contact.getMemberId());
			mv.addObject("contacts", contacts);
			mv.addObject("page", page);
			mv.addObject("contact",contact);
			mv.addObject("member",member);
		} catch (Exception e) {
			logger.info("通讯录用户列表异常", e);
			e.printStackTrace();
		}
		return mv;
	}
}
