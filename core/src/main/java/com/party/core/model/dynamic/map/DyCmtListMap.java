package com.party.core.model.dynamic.map;

import java.util.Date;

/**
 * Created by Juliana on 2017/6/27 0027.
 */
public class DyCmtListMap {
    //动态评论作者昵称
    private String authorName;

    //动态评论内容
    private String content;

    //创建时间
    private Date createDate;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
