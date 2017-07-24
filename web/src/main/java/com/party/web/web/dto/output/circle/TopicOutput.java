package com.party.web.web.dto.output.circle;

import com.party.web.web.dto.output.dynamic.DynamicOutput;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 话题输出实体
 * 
 * @author Administrator
 *
 */
public class TopicOutput extends DynamicOutput {
	private String tagName;

	private Integer isTop;

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public static TopicOutput transform(DynamicOutput input) {
		TopicOutput output = new TopicOutput();
		try {
			BeanUtils.copyProperties(output, input);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}
}
