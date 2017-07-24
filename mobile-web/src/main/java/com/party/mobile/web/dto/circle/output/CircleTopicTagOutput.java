package com.party.mobile.web.dto.circle.output;

import com.party.core.model.circle.CircleTag;
import com.party.core.model.circle.CircleTopicTag;
import org.springframework.beans.BeanUtils;

/**
 * 圈子话题标签输出视图
 *
 * @Author: Juliana
 * @Description:
 * @Date: 2016-12-14
 * @Modified by:
 */
public class CircleTopicTagOutput {
    //标签主键
    private String id;

    //标签名
    private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 圈子标签输出视图
     * @return 输出视图
     */
    public static CircleTopicTagOutput transform(CircleTopicTag tag){
    	CircleTopicTagOutput circleTagOutput = new CircleTopicTagOutput();
        BeanUtils.copyProperties(tag, circleTagOutput);
        return circleTagOutput;
    }
}
