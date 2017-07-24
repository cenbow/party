package com.party.mobile.web.dto.sign.output;

import com.alibaba.fastjson.annotation.JSONField;
import com.party.core.model.sign.GroupAuthor;
import org.springframework.beans.BeanUtils;

/**
 * 签到小组输出视图
 * Created by wei.li
 *
 * @date 2017/6/16 0016
 * @time 10:42
 */
public class SignGroupOutput {

    //编号
    private String id;

    //队伍名
    private String name;

    //创建者
    private String authorName;

    //创建者图像
    private String authorLogo;

    //状态
    private Integer status;

    //是否加入小组
    @JSONField(name = "isGroup")
    private boolean isGroup;

    //是否签到
    @JSONField(name = "isSign")
    private boolean isSign;

    //图片
    private String pic;

    //报名编号
    private String applyId;

    //项目编号
    private String projectId;

    //备注
    private String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLogo() {
        return authorLogo;
    }

    public void setAuthorLogo(String authorLogo) {
        this.authorLogo = authorLogo;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public static SignGroupOutput transform(GroupAuthor groupAuthor){
        SignGroupOutput signGroupOutput = new SignGroupOutput();
        BeanUtils.copyProperties(groupAuthor, signGroupOutput);
        return signGroupOutput;
    }
}
