package com.party.core.model.gatherForm;

import java.util.List;

import com.party.core.model.BaseModel;

/**
 * 表单项目
 * 
 * @author Administrator
 *
 */
public class GatherProject extends BaseModel {
	
	private static final long serialVersionUID = -7840561412805016821L;
	private String title;
	private String picture;
	private String content;
	private String showPicture;
	private String isRemindMe; // 是否需要通知
	
	private List<GatherForm> items;
	private List<GatherFormInfo> infoItems;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<GatherForm> getItems() {
		return items;
	}

	public void setItems(List<GatherForm> items) {
		this.items = items;
	}

	public List<GatherFormInfo> getInfoItems() {
		return infoItems;
	}

	public void setInfoItems(List<GatherFormInfo> infoItems) {
		this.infoItems = infoItems;
	}

	public String getShowPicture() {
		return showPicture;
	}

	public void setShowPicture(String showPicture) {
		this.showPicture = showPicture;
	}

	public String getIsRemindMe() {
		return isRemindMe;
	}

	public void setIsRemindMe(String isRemindMe) {
		this.isRemindMe = isRemindMe;
	}

}
