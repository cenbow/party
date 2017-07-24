package com.party.core.model.gatherForm;

import java.util.List;

import com.party.core.model.BaseModel;

/**
 * 表单
 * 
 * @author Administrator
 *
 */
public class GatherForm extends BaseModel {

	private static final long serialVersionUID = 7939793249708064364L;
	private String title; // 标题
	private String type; // 类型
	private String required; // 必填
	private String description; // 字段描述
	private String sort; // 排序
	private String projectId; // 项目id
	private String category; // 类型
	private List<GatherFormOption> subitems;
	private Integer maxIndex;

	public GatherForm(String projectId) {
		super();
		this.projectId = projectId;
	}

	public GatherForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<GatherFormOption> getSubitems() {
		return subitems;
	}

	public void setSubitems(List<GatherFormOption> subitems) {
		this.subitems = subitems;
	}

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}

}
