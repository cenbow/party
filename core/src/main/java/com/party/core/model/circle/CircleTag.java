/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 圈子用户标签Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleTag extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String circle;		// 圈子id
	private String tagName;		// 标签名
	private Integer isOpen; 	//是否公开 1 所有人可见 0 所有人不可见 2对本类型成员可见
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Integer getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	
	
	
}