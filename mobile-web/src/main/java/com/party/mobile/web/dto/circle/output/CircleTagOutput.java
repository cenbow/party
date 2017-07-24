package com.party.mobile.web.dto.circle.output;

import org.springframework.beans.BeanUtils;

import com.party.core.model.circle.CircleTag;

/**
 * 圈子标签输出视图
 *
 * @Author: Juliana
 * @Description:
 * @Date: 2016-12-14
 * @Modified by:
 */
public class CircleTagOutput {
    //标签主键
    private String id;

    //标签名
    private String tagName;

    //圈子id
    private String circleId;

    //标签权限信息
	private Integer isOpen;//是否公开 1 所有人可见 2 所有人不可见 3对本类型成员可见
	public String getId() {
		return id;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
     * 圈子标签输出视图
     * @return 输出视图
     */
    public static CircleTagOutput transform(CircleTag tag){
    	CircleTagOutput circleTagOutput = new CircleTagOutput();
        BeanUtils.copyProperties(tag, circleTagOutput);
        return circleTagOutput;
    }
}
