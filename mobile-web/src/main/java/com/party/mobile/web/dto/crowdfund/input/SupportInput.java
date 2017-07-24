package com.party.mobile.web.dto.crowdfund.input;

import com.party.core.model.crowdfund.Support;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * 众筹支持输入视图
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 11:40
 */
public class SupportInput {

    //支持评论
    private String comment;


    //众筹项目编号
    @NotBlank(message = "众筹项目编号不能为空")
    private String projectId;

    @NotNull(message = "金额不能为空")
    private Float money;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    /**
     * 转众筹支持
     * @param supportInput 众筹支持输入视图
     * @return 支持实体
     */
    public static Support transform(SupportInput supportInput){
        Support support = new Support();
        BeanUtils.copyProperties(supportInput, support);
        return support;
    }
}
