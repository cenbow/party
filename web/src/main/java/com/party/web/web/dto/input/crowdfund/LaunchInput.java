package com.party.web.web.dto.input.crowdfund;

import com.party.core.model.crowdfund.Project;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * 发起众筹项目输入视图
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 15:45
 */
public class LaunchInput {

    private String id;

    //项目标题
    private String title;

    //项目内容
    @NotBlank(message = "众筹项目内容不能为空")
    private String content;

    //目标筹集资金
    private Float targetAmount;

    //照片地址
    @NotBlank(message = "照片地址不能为空")
    private String pic;

    //备注
    private String remarks;

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

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 发起众筹输入视图转视图
     * @param launchInput 输入视图
     * @return 众筹项目实体
     */
    public static Project transform(LaunchInput launchInput){
        Project project = new Project();
        BeanUtils.copyProperties(launchInput, project);
        return project;
    }
}
