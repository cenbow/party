package com.party.core.model.dynamic;

import com.party.core.model.BaseModel;
import com.party.core.model.dynamic.map.DyCmtListMap;

import java.util.List;

/**
 * 动态实体
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public class Dynamic extends BaseModel {

    //地区
    private String areaId;

    //城市
    private String cityId;

    //动态图
    private String pics;

    //内容
    private String content;

    //是否热门推荐（0：非热门；1：热门）
    private Integer isHot;

    //点击查看详情数
    private Integer clickNum;

    //点赞数
    private Integer loveNum;

    //评论数
    private Integer commentNum;

    // 动态类型（1：社区动态； 2：圈子动态；）
    private String dynamicType;

    //发送方式（0:自发；1：代发）
    private Integer dynamicWay;

    //排序
    private Integer sort;

    //发布者名称
    private String memberName;

    private List<DyCmtListMap> commentList;

    public List<DyCmtListMap> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<DyCmtListMap> commentList) {
        this.commentList = commentList;
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

    public Integer getDynamicWay() {
        return dynamicWay;
    }

    public void setDynamicWay(Integer dynamicWay) {
        this.dynamicWay = dynamicWay;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dynamic dynamic = (Dynamic) o;

        if (areaId != null ? !areaId.equals(dynamic.areaId) : dynamic.areaId != null) return false;
        if (cityId != null ? !cityId.equals(dynamic.cityId) : dynamic.cityId != null) return false;
        if (pics != null ? !pics.equals(dynamic.pics) : dynamic.pics != null) return false;
        if (content != null ? !content.equals(dynamic.content) : dynamic.content != null) return false;
        if (isHot != null ? !isHot.equals(dynamic.isHot) : dynamic.isHot != null) return false;
        if (clickNum != null ? !clickNum.equals(dynamic.clickNum) : dynamic.clickNum != null) return false;
        if (dynamicType != null ? !dynamicType.equals(dynamic.dynamicType) : dynamic.dynamicType != null) return false;
        return sort != null ? sort.equals(dynamic.sort) : dynamic.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (pics != null ? pics.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (isHot != null ? isHot.hashCode() : 0);
        result = 31 * result + (clickNum != null ? clickNum.hashCode() : 0);
        result = 31 * result + (dynamicType != null ? dynamicType.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "areaId='" + areaId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", pics='" + pics + '\'' +
                ", content='" + content + '\'' +
                ", isHot=" + isHot +
                ", clickNum=" + clickNum +
                ", dynamicType='" + dynamicType + '\'' +
                ", sort=" + sort +
                '}';
    }

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
