package com.party.core.model.charge;

import java.util.List;

import com.party.core.model.BaseModel;

/**
 * 产品权限类型
 *
 * @author Administrator
 * @date 2017/7/27 0027 10:28
 */
public class ProductPrivilege extends BaseModel {
	private String name;
	private String code;
	private Integer sort;
	private String parentId;
	private String parentIds;
	private List<ProductPrivilege> childrens;

	public ProductPrivilege() {

	}

	public ProductPrivilege(String parentId) {

		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<ProductPrivilege> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<ProductPrivilege> childrens) {
		this.childrens = childrens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
}
