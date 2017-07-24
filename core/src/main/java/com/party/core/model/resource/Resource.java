package com.party.core.model.resource;

import com.party.core.model.BaseModel;

/**
 * 资源文件
 * 
 * @author Administrator
 *
 */
public class Resource extends BaseModel {

	private static final long serialVersionUID = -3016478227312616600L;

	// 标题
	private String title;

	// 图片
	private String pic;

	// 链接
	private String link;

	// 资源类型
	private String type;

	// 关联id
	private String refId;

	// 排序
	private Integer sort;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
