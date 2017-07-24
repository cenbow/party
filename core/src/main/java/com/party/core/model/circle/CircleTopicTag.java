/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import com.party.core.model.BaseModel;

import java.io.Serializable;

/**
 * 圈子话题标签Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleTopicTag extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String circle;		// 圈子id
	private String name;		// 标签名
	private Integer sort;

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}