package com.party.mobile.web.dto.dynamic.output;

import java.util.Date;

/**
 * 动态评论输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:07 16/11/8
 * @Modified by:
 */
public class DyCommentOutput {
    //动态评论作者
    private DyMemberOutput author;

    //动态评论内容
    private String content;

    //创建时间
    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
