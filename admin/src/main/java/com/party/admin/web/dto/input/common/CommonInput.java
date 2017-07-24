package com.party.admin.web.dto.input.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CommonInput {

	// 时间类型
	private Integer timeType;

	// 创建时间——开始
	private String createStart;

	// 创建时间——结束
	private String createEnd;

	// 发布者
	private String memberName;

	private Integer limit; // 每页显示
	private String orderBy; // 排序

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getCreateStart() {
		return createStart;
	}

	public void setCreateStart(String createStart) {
		this.createStart = createStart;
	}

	public String getCreateEnd() {
		return createEnd;
	}

	public void setCreateEnd(String createEnd) {
		this.createEnd = createEnd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * 拼接查询参数
	 * 
	 * @param input
	 * @return
	 */
	public static Map<String, Object> appendParams(CommonInput input) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (input == null) {
			input = new CommonInput();
		}

		/******** 时间块处理 ********/
		if (input.getTimeType() != null && input.getTimeType() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date ed = calendar.getTime(); // 结束时间
			if (input.getTimeType() == 2) { // 本周内
				int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (day_of_week == 0) {
					day_of_week = 7;
				}
				calendar.add(Calendar.DATE, -day_of_week + 1);
			} else if (input.getTimeType() == 3) { // 本月内
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			} else if (input.getTimeType() == 4) { // 昨天
				calendar.add(Calendar.DATE, -1);
				ed = calendar.getTime();
			}
			Date sd = calendar.getTime(); // 开始时间
			String std = sdf.format(sd) + " 00:00:00";
			params.put("startDate", std);
			String end = sdf.format(ed) + " 23:59:59";
			params.put("endDate", end);
		}
		/******** 时间块处理 ********/

		/******** 时间段处理 ********/
		if (StringUtils.isNotEmpty(input.getCreateStart())) {
			params.put("c_start", input.getCreateStart());
		}
		if (StringUtils.isNotEmpty(input.getCreateEnd())) {
			params.put("c_end", input.getCreateEnd());
		}
		/******** 时间段处理 ********/

		/******** 发布者处理 ********/
		if (StringUtils.isNotEmpty(input.getMemberName())) {
			params.put("memberName", input.getMemberName());
		}
		/******** 发布者处理 ********/

		return params;
	}
}
