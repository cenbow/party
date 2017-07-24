package com.party.core.model.circle;

import com.party.core.model.BaseModel;
import com.party.core.model.dynamic.Dynamic;

/**
 * 话题实体
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public class TopicMap extends Dynamic {

    private String tagName;

    private Integer isTop;

    private String circle;

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

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

}
