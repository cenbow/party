package com.party.mobile.web.dto.dynamic.input;

import com.party.core.model.dynamic.Dynamic;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 动态输入视图.
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 18:49 16/11/15
 * @Modified by:
 */
public class DynamicInput {
    //动态内容
    @NotBlank(message = "动态内容不能为空")
    private String content;

    //动态图
    @NotBlank(message = "图片内容不能为空")
    private String  pics;

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

    /**
     * 转换为动态实体
     * @param dynamicInput 动态视图
     * @return 动态实体
     */
    public static Dynamic transform(DynamicInput dynamicInput){
        Dynamic dynamic = new Dynamic();
        BeanUtils.copyProperties(dynamicInput, dynamic);
        return dynamic;
    }
}
