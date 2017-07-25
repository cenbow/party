package com.party.core.model.help;

import com.google.common.collect.Lists;
import com.party.core.model.BaseModel;

import java.util.List;

/**
 * 帮助
 */
public class Help extends BaseModel {
    private String serialNumber; // 序号
    private String title; // 标题
    private String parentId; // 父节点
    private String parentIds; // 父节点
    private String content; // 后端内容
    private String frontContent; // 前端内容
    private Integer sort; // 子排序
    private List<Help> childrens = Lists.newArrayList(); // 子节点

    public Help() {
    }

    public Help(String parentId) {
        this.parentId = parentId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Help> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Help> childrens) {
        this.childrens = childrens;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getFrontContent() {
        return frontContent;
    }

    public void setFrontContent(String frontContent) {
        this.frontContent = frontContent;
    }
}
