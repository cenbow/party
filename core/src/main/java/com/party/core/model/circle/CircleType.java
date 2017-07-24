package com.party.core.model.circle;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 圈子类型
 * 
 * @author Administrator
 *
 */
public class CircleType extends BaseModel implements Serializable {

	private static final long serialVersionUID = 5141194937440202235L;
	private String typeName;
	private Integer sort;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
