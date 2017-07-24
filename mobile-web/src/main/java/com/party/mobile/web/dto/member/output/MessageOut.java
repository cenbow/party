package com.party.mobile.web.dto.member.output;

import com.party.core.model.message.Message;
import com.party.mobile.web.dto.dynamic.output.DyMemberOutput;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 会员消息输出视图
 * party
 * Created by Wesley
 * on 2016/11/6
 */
public class MessageOut{

    //消息主键
    private String id;

    //关注者，评论者，点赞者
    private DyMemberOutput member;

    //消息图标
    private String logo;

    //消息创建时间
    private Date createDate;

    //标题
    private String title;

    //关联ID
    private String relId;

    //标签
    private String tag;

    //消息内容
    private String content;

    //图片URL
    private String picUrl;

    //是否新消息（0：否， 1：是）
    private Integer isNew;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public DyMemberOutput getMember() {
        return member;
    }

    public void setMember(DyMemberOutput member) {
        this.member = member;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    /**
     * 消息转消息输出视图
     * @param message 会员
     * @return 交互数据
     */
    public static MessageOut transform(Message message){
        MessageOut messageOut = new MessageOut();
        BeanUtils.copyProperties(message, messageOut);
        return messageOut;
    }
}