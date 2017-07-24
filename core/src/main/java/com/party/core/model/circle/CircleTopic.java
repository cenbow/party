/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.party.core.model.circle;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

import java.io.Serializable;

/**
 * 圈子话题（动态）关系Entity
 * @author Juliana
 * @version 2016-12-14
 */
public class CircleTopic extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String circle;		// 圈子id
	private String dynamic;		// 动态id
	private String topicTagId;	//圈子话题标签id
	private Integer isTop; 		//是否置顶 1 置顶 0 不置顶
	private Integer sort;

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDynamic() {
		return dynamic;
	}

	public String getTopicTagId() {
		return topicTagId;
	}

	public void setTopicTagId(String topicTagId) {
		this.topicTagId = topicTagId;
	}

	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}