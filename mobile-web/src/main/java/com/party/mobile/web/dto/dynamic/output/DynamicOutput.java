package com.party.mobile.web.dto.dynamic.output;

import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.dynamic.map.DyCmtListMap;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 动态输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:08 16/11/8
 * @Modified by:
 */
public class DynamicOutput {
    //动态主键id
    private String id;

    //创建时间
    private Date createDate;

    //创建时间
    private Date updateDate;

    //动态作者
    private DyMemberOutput author;

    //动态文字内容
    private String content;

    //动态图列表
    private List<String> picList;

    //动态总点赞数
    private Integer loveNum;

    //动态点击数
    private Integer clickNum;

    //动态总评论数
    private Integer commentNum;

    //动态评论列表
    private List<DyCmtListMap> commentList;

    //当前用户对该动态是否已经点赞(0：否，1：是)
    private Integer isLove;

    //分享链接
    private String shareLink;

    //动态类型
    private String dynamicType;

    //<------以下为圈子话题字段----->
    //标签名称
    private String tagName;
    //是否置顶
    private Integer isTop; 		//是否置顶 1 置顶 0 不置顶
    //当前用户在圈子角色
    private Map<String,Object> role;


    public Map<String, Object> getRole() {
        return role;
    }

    public void setRole(Map<String, Object> role) {
        this.role = role;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public DyMemberOutput getAuthor() {
        return author;
    }

    public void setAuthor(DyMemberOutput author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public List<DyCmtListMap> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<DyCmtListMap> commentList) {
        this.commentList = commentList;
    }

    public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public Integer getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(Integer loveNum) {
        this.loveNum = loveNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getIsLove() {
        return isLove;
    }

    public void setIsLove(Integer isLove) {
        this.isLove = isLove;
    }


    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }


    public static DynamicOutput transform(Dynamic dynamic){
        DynamicOutput dynamicOutput = new DynamicOutput();
        BeanUtils.copyProperties(dynamic, dynamicOutput);
        return dynamicOutput;
    }
}
