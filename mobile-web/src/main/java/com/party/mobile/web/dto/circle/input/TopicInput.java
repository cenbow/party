package com.party.mobile.web.dto.circle.input;

import com.party.core.model.dynamic.Dynamic;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 话题输入视图.
 */
public class TopicInput {
    //话题内容
    @NotBlank(message = "话题内容不能为空")
    private String content;

    //话题图
    @Size(min = 1,message = "图片不能小于一张")
    private String pics;

    private String tagId;

    private String circleId;

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * 转换为话题实体
     * @param dynamicInput 话题视图
     * @return 话题实体
     */
    public static Dynamic transform(TopicInput dynamicInput){
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(dynamicInput, dynamic);
        return dynamic;
    }
}
